package cn.edu.cqu.ngtl.controller.classmanagement;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.controller.BaseController;
import cn.edu.cqu.ngtl.dao.tams.TAMSClassTaApplicationDao;
import cn.edu.cqu.ngtl.dao.tams.impl.TAMSWorkflowStatusDaoJpa;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dataobject.enums.TA_STATUS;
import cn.edu.cqu.ngtl.dataobject.tams.*;
import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.form.classmanagement.ClassInfoForm;
import cn.edu.cqu.ngtl.service.classservice.IClassInfoService;
import cn.edu.cqu.ngtl.service.common.ExcelService;
import cn.edu.cqu.ngtl.service.common.WorkFlowService;
import cn.edu.cqu.ngtl.service.common.impl.IdstarIdentityManagerServiceImpl;
import cn.edu.cqu.ngtl.service.common.impl.TamsFileControllerServiceImpl;
import cn.edu.cqu.ngtl.service.exportservice.IPDFService;
import cn.edu.cqu.ngtl.service.riceservice.IClassConverter;
import cn.edu.cqu.ngtl.service.riceservice.ITAConverter;
import cn.edu.cqu.ngtl.service.taservice.ITAService;
import cn.edu.cqu.ngtl.service.userservice.IUserInfoService;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassDetailInfoViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassTeacherViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.MyTaViewObject;
import cn.edu.cqu.ngtl.viewobject.common.FileViewObject;
import com.itextpdf.text.DocumentException;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krad.web.service.impl.CollectionControllerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.kuali.rice.krad.util.GlobalVariables.getUserSession;

/**searchClassByCondition
 * Created by tangjing on 16-10-20.
 * 课程管理相关的view及function
 */
@Controller
@RequestMapping("/class")
public class ClassController extends BaseController {

    static Logger logger=Logger.getLogger(ClassController.class);

    @Autowired
    private IClassInfoService classInfoService;

    @Autowired
    private ITAConverter taConverter;

    @Autowired
    private IClassConverter classConverter;

    @Autowired
    private ITAService taService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private UTSessionDao utSessionDao;

    @Autowired
    private IPDFService PDFService;

    @Autowired
    private WorkFlowService workFlowService;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private TAMSClassTaApplicationDao tamsClassTaApplicationDao;

    private static int MAX_CALENDAR_HOUR = 10;

    @RequestMapping(params = "methodToCall=logout")
    public ModelAndView logout(@ModelAttribute("KualiForm") UifFormBase form,HttpServletRequest request) throws Exception {
        UserSession userSession = GlobalVariables.getUserSession();
        if (userSession.isBackdoorInUse()) {
            userSession.clearBackdoorUser();
        }

        request.getSession().invalidate();
        String ifUseIdstar = ConfigContext.getCurrentContextConfig().getProperty("filter.login.class");
        if (ifUseIdstar.contains("IdstarLoginFilter")) {
            // 将用户从重庆大学统一认证平台注销
            return this.performRedirect(form, new IdstarIdentityManagerServiceImpl().getLogoutUrl());
        } else {
            return this.returnToHub(form);
        }
    }

    /**
     * http://127.0.0.1:8080/tams/portal/class?methodToCall=getClassListPage&viewId=ClassView
     **/
    @RequestMapping(params = "methodToCall=getClassListPage")
    public ModelAndView getClassListPage(@ModelAttribute("KualiForm") UifFormBase form) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);
//        try {
        User user = (User) getUserSession().retrieveObject("user");

        if(userInfoService.isSysAdmin(user.getCode())||userInfoService.isAcademicAffairsStaff(user.getCode())||userInfoService.isCollegeStaff(user.getCode())||userInfoService.isCourseManager(user.getCode())){
            infoForm.setCanApprove(true);
        }else{
            infoForm.setCanApprove(false);
        }
        infoForm.setUser(user);
        infoForm.setClassList(
                taConverter.classInfoToViewObject(
                        classInfoService.getAllClassesFilterByUid(user.getCode())
                )
        );
        infoForm.setCheckedClassListAll(false);//刷新页面，全选框不选。

            return this.getModelAndView(infoForm, "pageClassList");
//        } catch (Exception e) {
//            BaseForm baseForm=(BaseForm)form;
//            baseForm.setErrMsg("哈哈哈哈，出错了！！！！！");
//            e.printStackTrace();
//            return this.showDialog("refreshPageViewDialog", true, infoForm);
//
//        }
    }
    /**
      * 课程页面checkbox全选
    */
    @RequestMapping(params = "methodToCall=checkClassListAllButton")
    public ModelAndView checkClassListAllButton(@ModelAttribute("KualiForm") UifFormBase form
                                        ) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);
        for(ClassTeacherViewObject classTeacherViewObject:infoForm.getClassList()){
            classTeacherViewObject.setChecked(infoForm.getCheckedClassListAll());
        }
        return this.getModelAndView(infoForm, "pageClassList");
    }


    @RequestMapping(params = "methodToCall=showApproveDialog")
    public ModelAndView showApproveDialog(@ModelAttribute("KualiForm") UifFormBase form,
                                HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        List<ClassTeacherViewObject> classList = infoForm.getClassList();
        //遍历所有list，找到选中的行
        List<ClassTeacherViewObject> checkedList = new ArrayList<>();
        for(ClassTeacherViewObject per : classList) {
            if(per.isChecked())
                checkedList.add(per);
        }
        if(checkedList.size()==0){
            infoForm.setErrMsg("请选择需要审批的课程！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }
        return this.showDialog("approveDialog",true,infoForm);
    }

    /**
     * 审批的方法
     * @param form
     * @param request
     * @return
     */
    @RequestMapping(params = "methodToCall=approveCourseApplicant")
    public ModelAndView approveCourseApplicant(@ModelAttribute("KualiForm") UifFormBase form,
                                         HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        List<ClassTeacherViewObject> classList = infoForm.getClassList();
        List<Integer> checkListIndex = new ArrayList<>();

        //遍历所有list，找到选中的行
        List<ClassTeacherViewObject> checkedList = new ArrayList<>();

        for(int i = 0 ;i<classList.size() ; i++) {
            if(classList.get(i).isChecked()) {
                checkedList.add(classList.get(i));
                checkListIndex.add(i);
            }
        }

        String uid = GlobalVariables.getUserSession().getPrincipalId();
        if(infoForm.getApproveReasonOptionFinder()==null){
            infoForm.setErrMsg("请选择审批的状态！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }

        boolean isSecMax = workFlowService.isSecMaxOrderOfThisStatue(infoForm.getApproveReasonOptionFinder(),"1");
        boolean result = false;
        String feedBackReason = infoForm.getApproveReason();
        for(ClassTeacherViewObject classTeacherViewObject:checkedList) {
            if(isSecMax){ //如果是该条工作流的倒数第二个状态，那么初始化课程经费
                classInfoService.validClassFunds(classTeacherViewObject.getId());

            }
            result = classInfoService.classStatusToCertainStatus(
                    uid,
                    classTeacherViewObject.getId(),
                    infoForm.getApproveReasonOptionFinder()
            );
            classInfoService.insertFeedBack(classTeacherViewObject.getId(),uid,feedBackReason,classTeacherViewObject.getStatus(),infoForm.getApproveReasonOptionFinder());
        }

        for(Integer i:checkListIndex){
            infoForm.getClassList().get(i).setStatus(workFlowService.getWorkFlowStatusName(infoForm.getApproveReasonOptionFinder()));
        }

        if(result){
//
//            MDC.put("remoteHost",request.getRemoteAddr());
//            logger.info("进行了审批操作,状态改为："+workFlowService.getWorkFlowStatusName(infoForm.getApproveReasonOptionFinder()));
            return this.getClassListPage(infoForm);
        }
        else  //应当返回错误信息
            infoForm.setErrMsg("审核失败！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
    }

    /**
     * 驳回的方法
     * @param form
     * @param request
     * @return
     */
    @RequestMapping(params = "methodToCall=reject")
    public ModelAndView reject(@ModelAttribute("KualiForm") UifFormBase form,
                                HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        List<ClassTeacherViewObject> classList = infoForm.getClassList();
        //遍历所有list，找到选中的行
        List<ClassTeacherViewObject> checkedList = new ArrayList<>();
        for(ClassTeacherViewObject per : classList) {
            if(per.isChecked())
                checkedList.add(per);
        }
        boolean result = false;
        String feedBackReason = infoForm.getReturnReason();
        String newStatus = new TAMSWorkflowStatusDaoJpa().getOneById(infoForm.getApproveReasonOptionFinder()).getWorkflowStatus();
        String uid = getUserSession().getPrincipalId();
        for(ClassTeacherViewObject classTeacherViewObject:checkedList) {    //依次将选择列表中的班次调整到设置的状态
             result = classInfoService.classStatusToCertainStatus(
                     uid,
                     classTeacherViewObject.getId(),
                     infoForm.getReturnReasonOptionFinder()
            );
            classInfoService.insertFeedBack(classTeacherViewObject.getId(),uid,feedBackReason,classTeacherViewObject.getStatus(),newStatus);
//            MDC.put("remoteHost",request.getRemoteAddr());
//            logger.info("进行了驳回操作,将："+classTeacherViewObject.getStatus()+"变为："+newStatus);
        }

        if(result){



            return this.getClassListPage(infoForm);
        }

        else  //应当返回错误信息
            return this.getClassListPage(infoForm);
    }

    /**
     * pageId限定了只接受来自pageClassList的请求
     * 从classlist跳转到某个class对应的classInfoPage
     *
     * @param form
     * @return
     */
    @RequestMapping(params = {"methodToCall=getClassInfoPage"})
    public ModelAndView getClassInfoPage(@ModelAttribute("KualiForm") UifFormBase form) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);
        try {
            /**
             * param in
             */
//            CollectionControllerServiceImpl.CollectionActionParameters params =
//                    new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
//            int index = params.getSelectedLineIndex();
            String classId = infoForm.getCurrClassId();
            UTClass utClass = classInfoService.getClassInfoById(classId);

            ClassDetailInfoViewObject detailInfoViewObject = taConverter.classInfoToViewObject(
                    utClass
            );

            infoForm.setDetailInfoViewObject(detailInfoViewObject);

        } catch (Exception e) {
           e.printStackTrace();
        }
        return this.getModelAndView(infoForm, "pageClassInfo");
    }


    /**
     *
     */
    @RequestMapping(params = {"methodToCall=getClassInfoPageFromInside"})
    public ModelAndView getClassInfoPageFromInside(@ModelAttribute("KualiForm") UifFormBase form) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);
        UTClass utClass = classInfoService.getClassInfoById(infoForm.getCurrClassId());

        ClassDetailInfoViewObject detailInfoViewObject = taConverter.classInfoToViewObject(
                utClass
        );
        infoForm.setDetailInfoViewObject(detailInfoViewObject);
        return this.getModelAndView(infoForm, "pageClassInfo");
    }




    /**
     * 获取学生申请当助教页面(填表)
     * http://127.0.0.1:8080/tams/portal/class?methodToCall=getApplyTAPage&viewId=ClassView
     **/
    @RequestMapping(params = "methodToCall=getApplyTAPage")
    public ModelAndView getApplyTAPage(@ModelAttribute("KualiForm") UifFormBase form,
                                       HttpServletRequest request) {
        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        String stuId = userSession.getLoggedInUserPrincipalId();

        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        String classId = infoForm.getCurrClassId();

        TAMSTaApplication tamsTaApplication = taService.getApplicationByStuIdAndClassId(stuId,classId);

        if(tamsTaApplication!=null) {
            infoForm.setTaApplicationSubmitted(true);
            infoForm.setApplicationPhoneNbr(tamsTaApplication.getPhoneNbr()); //设置申请人电话号码
            infoForm.setApplyReason(tamsTaApplication.getNote());   //设置申请人理由
            infoForm.setBankName(tamsTaApplication.getBankName()); //银行名称
            infoForm.setBankNbr(tamsTaApplication.getPhoneNbr());  //银行卡号
        }else{
            infoForm.setTaApplicationSubmitted(false);
        }


        infoForm.setApplyAssistantViewObject(
                taConverter.applyAssistantToTableViewObject(
                        classInfoService.getStudentInfoById(stuId),
                        classInfoService.getClassInfoById(classId)
                )
        );

        return this.getModelAndView(infoForm, "pageApplyForTaForm");
    }

    /**
     * 学生提交助教申请
     * @param form
     * @return
     */
    @RequestMapping(params = {"pageId=pageApplyForTaForm", "methodToCall=submitTaForm"})
    public ModelAndView submitTaForm(@ModelAttribute("KualiForm") UifFormBase form,
                                     HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        if(infoForm.getApplyAssistantViewObject().getStudentId()==null||infoForm.getApplyAssistantViewObject().getUsername()==null){
            infoForm.setErrMsg("身份信息有误，请联系教务处！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }
        if(infoForm.getApplicationPhoneNbr()==null){
            infoForm.setErrMsg("请申请人填写本人联系电话！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }
        if(infoForm.getBankName()==null){
            infoForm.setErrMsg("请选择银行名称！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }
        if(infoForm.getBankNbr()==null){
            infoForm.setErrMsg("请填写银行卡号！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }
        if(infoForm.getApplyReason()==null){
            infoForm.setErrMsg("请申请人填写申请理由！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }

        if(!(infoForm.getIsAgree().equals("1") || infoForm.getIsAgree().equals("2"))){
            infoForm.setErrMsg("请申请人选择导师意见！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }
        if(infoForm.getIsAgree().equals("2")){
            infoForm.setErrMsg("导师意见为不同意，无法申请助教！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }

        if(classInfoService.isInBlackList(infoForm.getApplyAssistantViewObject().getStudentId())){
            infoForm.setErrMsg("您曾经被解聘，无法申请助教！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }

        short code = taService.submitApplicationAssistant(taConverter.submitInfoToTaApplication(infoForm));

        MDC.put("remoteHost",request.getRemoteAddr());
        logger.info("学生提交申请助教操作");

        if(code == 10){
            infoForm.setErrMsg("管理员未设置相应的添加时间！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }else if(code == 1) {
            infoForm.setErrMsg("非助教申请时间！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        else if(code == 2) {
            infoForm.setErrMsg("您的提交已申请，请勿重复提交");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        else if(code == 3) {
            infoForm.setErrMsg("您已经被此课程聘用，请勿重复提交！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        else if(code == 4) {
            infoForm.setTaApplicationSubmitted(true);
            return this.getModelAndView(infoForm, "pageApplyForTaForm");
        }
        else if(code == 6){
            infoForm.setErrMsg("您最多可以申请和担任两门课程的助教！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        else {
            infoForm.setErrMsg("未知错误");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }


    }

    /**
     * 学生取消助教申请
     * @param form
     * @param request
     * @return
     */
    @RequestMapping(params = "methodToCall=cancelSubmitTaForm")
    public ModelAndView cancelSubmitTaForm(@ModelAttribute("KualiForm") UifFormBase form,
                                            HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);
        String classId = infoForm.getCurrClassId();
        String stuId = infoForm.getApplyAssistantViewObject().getStudentId();

        if(classId==null||stuId==null){
            infoForm.setErrMsg("个人信息有误，无法删除助教申请。请联系管理员！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        classInfoService.deleteTaApplicationByStuIdAndClassId(stuId,classId);
        infoForm.setTaApplicationSubmitted(false);

        MDC.put("remoteHost",request.getRemoteAddr());
        logger.info("取消了学生ID为："+stuId+"的助教申请操作");

        return this.getModelAndView(infoForm, "pageApplyForTaForm");
    }

    /**
     * 获取教学日历页面
     * http://127.0.0.1:8080/tams/portal/class?methodToCall=getTeachingCalendar&viewId=ClassView
     **/
    @RequestMapping(params = "methodToCall=getTeachingCalendar")
    public ModelAndView getTeachingCalendar(@ModelAttribute("KualiForm") UifFormBase form,
                                            HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        int index=Integer.parseInt(infoForm.getIndexClassListPage());
        ClassTeacherViewObject classObject = infoForm.getClassList().get(index);
        /**
         * param in end
         */
        String classId = classObject.getId();
        infoForm.setCurrClassId(classId);

        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        String uId = userSession.getLoggedInUserPrincipalId();

        /*
         * 是否已经是助教
         */
        TAMSTa tamsta = taService.getTaByTaId(uId,classId);
        if(tamsta!=null){
            infoForm.setBeenEmployed(true);
        }else{
            infoForm.setBeenEmployed(false);
        }

        if (classId == null) {
            infoForm.setErrMsg("访问出错！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }

        infoForm.setAllCalendar(
                taConverter.TeachCalendarToViewObject(
                        classInfoService.getAllTaTeachCalendarFilterByUidAndClassId(
                                uId,
                                classId),
                        false
                )
        );

        infoForm.setTotalElapsedTime(
                taConverter.countCalendarTotalElapsedTime(
                        infoForm.getAllCalendar()
                )
        );
        return this.getModelAndView(infoForm, "pageTeachingCalendar");
    }


    /**
     * 获取新建教学日历页面
     **/
    @RequestMapping(params = "methodToCall=getAddTeachCalendarPage")
    public ModelAndView getAddTeachCalendarPage(@ModelAttribute("KualiForm") UifFormBase form,
                                                HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        if(classInfoService.getClassApplicationByClassId(infoForm.getCurrClassId())!=null){
            if(!classInfoService.getAllClassesFilterByCLassId(infoForm.getCurrClassId()).getStatus().equals("1")) {
                infoForm.setErrMsg("您已提交申请，无法添加教学日历！");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            }
        }

        infoForm.setTeachCalendar(new TAMSTeachCalendar());
        infoForm.setAddTeachCTime(null);
        infoForm.setFileList(new ArrayList<FileViewObject>());
        return this.getModelAndView(infoForm, "pageAddTeachCalendar");
    }

    /**
     * 获取教学日历详情页面
     **/
    @RequestMapping(params = "methodToCall=getViewTeachingCalendarPage")
    public ModelAndView getViewTeachingCalendarPage(@ModelAttribute("KualiForm") UifFormBase form,
                                                HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        CollectionControllerServiceImpl.CollectionActionParameters params = new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
        int index = params.getSelectedLineIndex();

        try {
            infoForm.setCurrentCalendarInfo(
                    infoForm.getAllCalendar().get(index)
            );

            //code 当做 id 用
            List<TAMSAttachments> attachments = new TamsFileControllerServiceImpl().getAllCalendarAttachments(infoForm.getAllCalendar().get(index).getCode());
            infoForm.setCalendarFiles(
                    taConverter.attachmentsToFileViewObject(attachments)
            );
            infoForm.setAllCalendar(null);  //节省内存

            String classId = infoForm.getCurrClassId();
            String uId = getUserSession().getPrincipalId();
           /* infoForm.setAllActivities(
                    taConverter.activitiesToViewObject(
                            classInfoService.getAllTaTeachActivityAsCalendarFilterByUidAndClassId(
                                    uId, classId)
                    )
            );*/
            return this.getModelAndView(infoForm, "pageViewTeachingCalendar");
        }
        catch (IndexOutOfBoundsException e) {
            // 应该返回错误页面，选择数据在内存中不存在，可能存在脏数据
            return this.getModelAndView(infoForm, "pageViewTeachingCalendar");
        }
    }

    /**
     * 编辑日历详情页面
     **/
    @RequestMapping(params = "methodToCall=getEditTeachCalendarPage")
    public ModelAndView getEditTeachCalendarPage(@ModelAttribute("KualiForm") UifFormBase form,
                                                    HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        CollectionControllerServiceImpl.CollectionActionParameters params = new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
        int index = params.getSelectedLineIndex();

        try {
            infoForm.setCurrentCalendarInfo(
                    infoForm.getAllCalendar().get(index)
            );

            //code 当做 id 用
            List<TAMSAttachments> attachments = new TamsFileControllerServiceImpl().getAllCalendarAttachments(infoForm.getAllCalendar().get(index).getCode());
            infoForm.setCalendarFiles(
                    taConverter.attachmentsToFileViewObject(attachments)
            );
            infoForm.setAllCalendar(null);  //节省内存

            String classId = infoForm.getCurrClassId();
            String uId = getUserSession().getPrincipalId();
           /* infoForm.setAllActivities(
                    taConverter.activitiesToViewObject(
                            classInfoService.getAllTaTeachActivityAsCalendarFilterByUidAndClassId(
                                    uId, classId)
                    )
            );*/
            return this.getModelAndView(infoForm, "pageEditTeachingCalendar");
        }
        catch (IndexOutOfBoundsException e) {
            // 应该返回错误页面，选择数据在内存中不存在，可能存在脏数据
            return this.getModelAndView(infoForm, "pageEditTeachingCalendar");
        }
    }


    /**
     * 下载教学日历的附件
     **/
    @RequestMapping(params = "methodToCall=downloadCalendarFile")
    public ModelAndView downloadCalendarFile(@ModelAttribute("KualiForm") UifFormBase form,
                                                HttpServletResponse response) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        String classId = infoForm.getCurrClassId();
        String calendarId = infoForm.getCurrentCalendarInfo() != null ? infoForm.getCurrentCalendarInfo().getCode() : null;
        if (classId == null || calendarId == null) {
            infoForm.setErrMsg("无法进入,请联系管理员！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }

        CollectionControllerServiceImpl.CollectionActionParameters params = new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
        int index = params.getSelectedLineIndex();

        try {
            String attachmentId = infoForm.getCalendarFiles().get(index).getId();

            new TamsFileControllerServiceImpl().downloadCalendarFile(classId, calendarId, attachmentId, response);


            return this.getModelAndView(infoForm, "pageViewTeachingCalendar");
        }
        catch (IndexOutOfBoundsException e) {
            return this.getModelAndView(infoForm, "pageViewTeachingCalendar");
        }
    }

    /**
     * 删除教学日历中附件
     */
    @RequestMapping(params = "methodToCall=removeCalendarFile")
    public ModelAndView removeCalendarFile(@ModelAttribute("KualiForm") UifFormBase form,
                                           HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        String classId = infoForm.getCurrClassId();
        if (classId == null) {
            infoForm.setErrMsg("无法进入,请联系管理员！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }

        CollectionControllerServiceImpl.CollectionActionParameters params = new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
        int index = params.getSelectedLineIndex();

        try {
            String attachmentId = infoForm.getCalendarFiles().get(index).getId();

            classInfoService.removeCalendarFileById(classId, attachmentId);
            infoForm.getCalendarFiles().remove(index);

            MDC.put("remoteHost",request.getRemoteAddr());
            logger.info("删除ID为："+attachmentId+"的教学日历中附件");

            return this.getModelAndView(infoForm, "pageViewTeachingCalendar");
        }
        catch (IndexOutOfBoundsException e) {
            return this.getModelAndView(infoForm, "pageViewTeachingCalendar");
        }
    }

    /**
     * 提交新建教学日历页面
     **/
    @RequestMapping(params = "methodToCall=submitTeachCalendarPage")
    public ModelAndView submitTeachCalendarPage(@ModelAttribute("KualiForm") UifFormBase form,
                                                HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        UserSession session = getUserSession();
        String uId = session.getPrincipalId();

        String classId = infoForm.getCurrClassId();
        if(infoForm.getAddTeachCTime()==null){
            infoForm.setErrMsg("请申请人填写时间范围！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }
        String arr[] = infoForm.getAddTeachCTime().split("~");

        TAMSTeachCalendar added = infoForm.getTeachCalendar();
        /*
            控制判断 start
         */
        if(added.getElapsedTime()==null){
            infoForm.setErrMsg("请申请人填写总耗时！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }

        if(added.getTheme()==null){
            infoForm.setErrMsg("请申请人填写教学主题！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }

        if(added.getDescription()==null){
            infoForm.setErrMsg("请申请人填写教学描述！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }

        if(added.getTaTask()==null){
            infoForm.setErrMsg("请申请人填写助教任务！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }

        if(Integer.parseInt(added.getElapsedTime()) > MAX_CALENDAR_HOUR) {
            infoForm.setErrMsg("单次教学日历耗时不能超过10个小时！请重新输入");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//infoForm.getTeachCalendar().getStartTime()  infoForm.getTeachCalendar().getEndTime()
        try {
            added.setStartTime(
                    outputFormat.format(
                            inputFormat.parse(
                                    arr[0]
                            )
                    )
            );
            added.setEndTime(
                    outputFormat.format(
                            inputFormat.parse(
                                    arr[1]
                            )
                    )
            );
        } catch (Exception e) {
            //do nothing
        }
        if(infoForm.getFileList() != null && infoForm.getFileList().size() != 0)
            added.setHasAttachment(true);

        //添加日历信息到数据库
        added = classInfoService.instructorAddTeachCalendar(uId, classId, added);

        if(added == null){
            infoForm.setErrMsg("你不是该门课的主管教师，无法添加");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        if (added.getId() != null) { //添加数据库成功

            MDC.put("remoteHost",request.getRemoteAddr());
            logger.info("添加了一条ID为："+added.getId()+"的教学日历信息到数据库");

            //添加附件
            if(added.isHasAttachment()) {
                new TamsFileControllerServiceImpl().saveCalendarAttachments(uId, classId, added.getId(), infoForm.getFileList());

                logger.info("上传新建教学日历的附件");
            }

            return this.getTeachingCalendar(infoForm, request);
        }
        return this.getModelAndView(infoForm, "pageTeachingCalendar");
    }


    /**
     * 创建并继续
     * @param form
     * @param request
     * @return
     */
    @RequestMapping(params = "methodToCall=submitThenContinue")
    public ModelAndView submitThenContinue(@ModelAttribute("KualiForm") UifFormBase form,
                                            HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        UserSession session = getUserSession();
        String uId = session.getPrincipalId();

        String classId = infoForm.getCurrClassId();
        if(infoForm.getAddTeachCTime()==null){
            infoForm.setErrMsg("请申请人填写时间范围！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }
        String arr[] = infoForm.getAddTeachCTime().split("~");

        TAMSTeachCalendar added = infoForm.getTeachCalendar();

        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(added.getElapsedTime());
        if(!isNum.matches()){
            infoForm.setErrMsg("请输入整数！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }
        /*
        else if(added.getElapsedTime().contains(".")){
            infoForm.setErrMsg("教学日历耗时不能为小数！请重新输入");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }
        */
        else if(Integer.parseInt(added.getElapsedTime()) > MAX_CALENDAR_HOUR) {
            infoForm.setErrMsg("单次教学日历耗时不能超过10个小时！请重新输入");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }

        /*
            控制判断 start
         */
        if(added.getElapsedTime()==null){
            infoForm.setErrMsg("请申请人填写总耗时！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }

        if(added.getTheme()==null){
            infoForm.setErrMsg("请申请人填写教学主题！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }

        if(added.getDescription()==null){
            infoForm.setErrMsg("请申请人填写教学描述！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }

        if(added.getTaTask()==null){
            infoForm.setErrMsg("请申请人填写助教任务！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//infoForm.getTeachCalendar().getStartTime()  infoForm.getTeachCalendar().getEndTime()
        try {
            added.setStartTime(
                    outputFormat.format(
                            inputFormat.parse(
                                    arr[0]
                            )
                    )
            );
            added.setEndTime(
                    outputFormat.format(
                            inputFormat.parse(
                                    arr[1]
                            )
                    )
            );
        } catch (Exception e) {
            //do nothing
        }
        if(infoForm.getFileList() != null && infoForm.getFileList().size() != 0)
            added.setHasAttachment(true);

        //添加日历信息到数据库
        added = classInfoService.instructorAddTeachCalendar(uId, classId, added);
        if(added == null){
            infoForm.setErrMsg("你不是该门课的主管教师，无法添加教学日历！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        if (added.getId() != null) { //添加数据库成功

            MDC.put("remoteHost",request.getRemoteAddr());
            logger.info("添加了一条ID为："+added.getId()+"的教学日历信息到数据库");

            //添加附件
            if(added.isHasAttachment()) {
                new TamsFileControllerServiceImpl().saveCalendarAttachments(uId, classId, added.getId(), infoForm.getFileList());

                logger.info("上传新建教学日历的附件");
            }

            return this.getAddTeachCalendarPage(infoForm,request);
        }
        return this.getAddTeachCalendarPage(infoForm,request);
    }

    /**
     * 删除教学日历
     */
    @RequestMapping(params = "methodToCall=deleteTeachCalendar")
    public ModelAndView deleteTeachCalendar(@ModelAttribute("KualiForm") UifFormBase form,
                                            HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        /** uid **/
        UserSession session = getUserSession();
        String uId = session.getPrincipalId();

        /** classid **/
        String classId = infoForm.getCurrClassId();

        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
        int index = params.getSelectedLineIndex();

        /** calendarid **/
        String teachCalendarId = infoForm.getAllCalendar().get(index).getCode();

        if(classInfoService.getClassApplicationByClassId(infoForm.getCurrClassId())!=null){
            if(!classInfoService.getAllClassesFilterByCLassId(infoForm.getCurrClassId()).getStatus().equals("1")) {
                infoForm.setErrMsg("您已提交申请，无法删除教学日历！");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            }
        }

        if (classInfoService.removeTeachCalenderById(uId, classId, teachCalendarId)) {

            MDC.put("remoteHost",request.getRemoteAddr());
            logger.info("删除ID为："+teachCalendarId+"的教学日历信息");

            //删除教学日历的附件信息
            boolean result = classInfoService.removeAllCalendarFilesByClassIdAndCalendarId(
                    classId, teachCalendarId);

            logger.info("删除ID为："+teachCalendarId+"的教学日历的附件");

            return this.getTeachingCalendar(infoForm, request);
        }
        else {
            infoForm.setErrMsg("删除失败！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
    }

    /**
     * 获取教学活动页面
     * http://127.0.0.1:8080/tams/portal/class?methodToCall=getTeachActivities&viewId=ClassView
     **/
    @RequestMapping(params = "methodToCall=getTeachActivities")
    public ModelAndView getTeachActivities(@ModelAttribute("KualiForm") UifFormBase form,
                                           HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        String uId = userSession.getLoggedInUserPrincipalId();

        String classId = infoForm.getCurrClassId();
        if (classId == null) {
            infoForm.setErrMsg("无法进入,请联系管理员！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }

        infoForm.setAllActivities(
                taConverter.activitiesToViewObject(
                        classInfoService.getAllTaTeachActivityAsCalendarFilterByUidAndClassId(
                                uId, classId)
                )
        );

//        infoForm.getAllActivities();

        return this.getModelAndView(infoForm, "pageTeachActivities");
    }

    /**
     * 获取新建教学活动的页面
     * http://127.0.0.1:8080/tams/portal/class?methodToCall=getAddActivityPage&viewId=ClassView
     **/
    @RequestMapping(params = "methodToCall=getAddActivityPage")
    public ModelAndView getAddActivityPage(@ModelAttribute("KualiForm") UifFormBase form,
                                           HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);
        return this.getModelAndView(infoForm, "pageAddActivity");
    }

    /**
     * 根据条件查询班级列表
     *
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=searchClassByCondition")
    public ModelAndView searchClassByCondition(@ModelAttribute("KualiForm") UifFormBase form,
                                               HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        String uId = userSession.getLoggedInUserPrincipalId();
        Map<String, String> conditions = new HashMap<>();

        //put conditions
        conditions.put("ClassNumber", infoForm.getCondClassNumber());
        conditions.put("DepartmentId", infoForm.getCondDepartmentName());
        conditions.put("DepartmentId", infoForm.getCondDepartmentName());
        conditions.put("InstructorName", infoForm.getCondInstructorName());
        conditions.put("CourseName", infoForm.getCondCourseName());
        conditions.put("CourseCode", infoForm.getCondCourseCode());
        conditions.put("ClassType", infoForm.getCondClassType());
        conditions.put("StatusId", infoForm.getCourseStatus());

        infoForm.setClassList(
                taConverter.classInfoToViewObject(
                        classInfoService.getAllClassesFilterByUidAndCondition(uId, conditions)
                )
        );

        //清除table页面信息缓存
        Map map = new HashMap();
        map.putAll(infoForm.getViewPostMetadata().getComponentPostMetadataMap().get("ClassListPageTable").getData());
        map.put("displayStart",0);
        infoForm.getViewPostMetadata().getComponentPostMetadataMap().get("ClassListPageTable").setData(map);

        return this.getModelAndView(infoForm, "pageClassList");
    }


    /**
     * 获取教师申请助教的页面
     * http://127.0.0.1:8080/tams/portal/class?methodToCall=getRequestTaPage&viewId=ClassView
     **/
    @RequestMapping(params = "methodToCall=getRequestTaPage")
    public ModelAndView getRequestTaPage(@ModelAttribute("KualiForm") UifFormBase form) {

        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        String uId = getUserSession().getPrincipalId();
        String classId = infoForm.getCurrClassId();

        infoForm.setApplyViewObject(
                taConverter.instructorAndClassInfoToViewObject(classInfoService.getClassInfoById(classId))
        );
        infoForm.setAllCalendar(
                taConverter.TeachCalendarToViewObject(
                        classInfoService.getAllTaTeachCalendarFilterByUidAndClassId(
                                uId,
                                classId),
                        true
                )
        );
        infoForm.setTotalElapsedTime(
                taConverter.countCalendarTotalElapsedTime(
                        infoForm.getAllCalendar()
                )
        );
        infoForm.setTotalBudget(
                taConverter.countCalendarTotalBudget(
                        infoForm.getAllCalendar()
                )
        );

        //设置助教人数
        TAMSClassTaApplication tamsClassTaApplication =classInfoService.getClassApplicationByClassId(classId);
        if(tamsClassTaApplication!=null){
            infoForm.getApplyViewObject().setAssistantNumber(tamsClassTaApplication.getTaNumber().toString());
            if(!classInfoService.getAllClassesFilterByCLassId(infoForm.getCurrClassId()).getStatus().equals("1")) {
                infoForm.setSubmitted(true);
            }else {
                infoForm.setSubmitted(false);
            }
        }
        //设置成绩评定方式
        List<TAMSClassEvaluation> tamsClassEvaluation = classInfoService.getClassEvaluationByClassId(classId);
        if(tamsClassEvaluation!=null){
            infoForm.setClassEvaluations(tamsClassEvaluation);
        }else{
            infoForm.setClassEvaluations(new ArrayList<TAMSClassEvaluation>());
        }

        infoForm.setFeedbacks(taConverter.feedBackToViewObject(classInfoService.getFeedBackByClassId(classId)));


        return this.getModelAndView(infoForm, "pageRequestTa");
    }

    /**
     * 成绩评定删除辅助方法
     *
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=deleteEvaluationLine")
    public ModelAndView deleteEvaluationLine(@ModelAttribute("KualiForm") UifFormBase form,HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
        int index = params.getSelectedLineIndex();

        if(classInfoService.getClassApplicationByClassId(infoForm.getCurrClassId())!=null){
            if(!classInfoService.getAllClassesFilterByCLassId(infoForm.getCurrClassId()).getStatus().equals("1")) {
                infoForm.setErrMsg("您已提交申请，无法删除教学日历！");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            }
        }

        infoForm.getClassEvaluations().remove(index);

        MDC.put("remoteHost",request.getRemoteAddr());
        logger.info("删除了ID为："+index+"的成绩评定辅助方法");

        return this.getModelAndView(infoForm, "pageRequestTa");
    }

    /**
     * 教师提交申请助教的请求
     */
    @RequestMapping(params = "methodToCall=submitRequestTaPage")
    public ModelAndView submitRequestTaPage(@ModelAttribute("KualiForm") UifFormBase form,
                                            HttpServletRequest request) {

        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        String assistantNumber = infoForm.getApplyViewObject().getAssistantNumber();
        if(assistantNumber==null){
            infoForm.setErrMsg("请填写本课程所需助教的数量！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        List<TAMSClassEvaluation> classEvaluations = infoForm.getClassEvaluations();
        if(classEvaluations == null || classEvaluations.isEmpty()) {
            infoForm.setErrMsg("请填写至少一种成绩评定方式！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        else {
            Integer percent = 0;
            for(TAMSClassEvaluation classEvaluation : classEvaluations) {
                percent += Integer.valueOf(classEvaluation.getEvaluationPercent());
            }
            if(percent != 100) {
                infoForm.setErrMsg("成绩评定方式成绩占比之和必须等于100！");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            }
        }
        String classId = infoForm.getCurrClassId();
        String instructorId = GlobalVariables.getUserSession().getPrincipalId();
        String totalTime = infoForm.getTotalElapsedTime();
        String totalBudget = infoForm.getTotalBudget().replace(",",""); //去掉钱里面的逗号
        short result = classInfoService.instructorAddClassTaApply(instructorId, classId, assistantNumber, classEvaluations,totalTime,totalBudget);

        MDC.put("remoteHost",request.getRemoteAddr());
        logger.info("教师"+GlobalVariables.getUserSession().getPrincipalName()+"提交申请助教的请求");

        if(result == 1) {
            infoForm.setErrMsg("不在教师申请助教期间!");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        else if(result == 2) {
            infoForm.setErrMsg("已处于审核状态中！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        else if(result == 3) {
            infoForm.setErrMsg("写入申请信息失败！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        else if(result == 4) {
            infoForm.setErrMsg("写入课程考核信息失败！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        else if(result == 5) {
            infoForm.setErrMsg("未找到用户的角色！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        else if(result == 6) {
            infoForm.setErrMsg("未找到\"审核方法\"！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        else if(result == 7) {
            infoForm.setSubmitted(true);
            return this.getModelAndView(infoForm, "pageRequestTa");
        }
        else if(result == 8) {
            infoForm.setErrMsg("您已经提交过申请，请等待审批结果！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        else if(result == 10) {
            infoForm.setErrMsg("管理员未设置提交申请时间，无法提交！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        else {
            infoForm.setErrMsg("未知错误！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
    }


    /**
     * 将表格打印为excel，整体可用，各列具体参数还需修改
     *
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = {"pageId=pageClassList", "methodToCall=exportClassListExcel"})
    public ModelAndView exportClassListExcel(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                                             HttpServletResponse response) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);


        if (infoForm.getClassList() == null || infoForm.getClassList().size() == 1) { //size=1是因为会设置至少一个空object让表格不会消失
            if(infoForm.getClassList().get(0).getId() == null) {
                infoForm.setErrMsg("列表为空！");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            }
        }

        List<ClassTeacherViewObject> classList = infoForm.getClassList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = "教学班列表" + "-" + getUserSession().getLoggedInUserPrincipalId() + "-" + sdf.format(new Date()) + ".xls";

        try {
            String filePath = excelService.printClassListExcel(classList, "exportfolder", fileName, "2003");
            String baseUrl = CoreApiServiceLocator.getKualiConfigurationService()
                    .getPropertyValueAsString(KRADConstants.ConfigParameters.APPLICATION_URL);

            return this.performRedirect(infoForm, baseUrl + "/" + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            infoForm.setErrMsg("系统导出EXCEL文件错误！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
    }

    /**
     * 将表格打印为PDF，整体可用，各列具体参数还需修改
     *
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = {"pageId=pageClassList", "methodToCall=exportClassListPDF"})
    public ModelAndView exportClassListPDF(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                                             HttpServletResponse response) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        if (infoForm.getClassList() == null || infoForm.getClassList().size() == 1) { //size=1是因为会设置至少一个空object让表格不会消失
            if(infoForm.getClassList().get(0).getId() == null) {
                infoForm.setErrMsg("列表为空！");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            }
        }

        List<ClassTeacherViewObject> classList = infoForm.getClassList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = "教学班列表" + "-" + getUserSession().getLoggedInUserPrincipalId() + "-" + sdf.format(new Date());
        String filePath = "";
        try {
            String[] header = {"课程名称", "课程编号", "教学班", "教师", "耗费工时", "学院"};
            List<String[]> Content = new ArrayList(classList.size());
            for(ClassTeacherViewObject clazz : classList) {
                String courseName = clazz.getCourseName() == null ? "" : clazz.getCourseName();
                String courseCode = clazz.getCourseCode() == null ? "" : clazz.getCourseCode();
                String clazzCode = clazz.getClassNumber() == null ? "" : clazz.getClassNumber();
                String instructor = clazz.getInstructorName() == null ? "" : clazz.getInstructorName();
                String time = clazz.getWorkTime() == null ? "" : clazz.getWorkTime();
                String department = clazz.getDepartmentName() == null ? "" : clazz.getDepartmentName();
                String[] content = {
                        courseName, courseCode, clazzCode, instructor, time, department
                };
                Content.add(content);
            }

            filePath = PDFService.printNormalTable("课程信息列表", header, Content, fileName);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            infoForm.setErrMsg("系统导出PDF文件错误！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        String baseUrl = CoreApiServiceLocator.getKualiConfigurationService()
                .getPropertyValueAsString(KRADConstants.ConfigParameters.APPLICATION_URL);

        return this.performRedirect(infoForm, baseUrl + "/" + filePath);
    }

    /**
     * 上传文件方法
     */
    @Override
    public ModelAndView addFileUploadLine(UifFormBase form) {
        return super.addFileUploadLine(form);
    }

    /**
     * 文件上传之后，点击文件名(href)就会调用此方法
     */
    @Override
    public void getFileFromLine(UifFormBase form, HttpServletResponse response) {
        new TamsFileControllerServiceImpl().getFileFromLine(form, response);
    }

    @Override
    protected UifFormBase createInitialForm() {
        // 开发用的setService,不设置这个那么上传时会调用默认的fileService(有中文乱码等问题)
//        setFileControllerService(new TamsFileControllerServiceImpl());
        return new ClassInfoForm();
    }

    @RequestMapping(params = "methodToCall=getTAInfoPage")
    public ModelAndView getTAInfoPage(@ModelAttribute("KualiForm") UifFormBase form) {
        return null;
    }

    //我的助教（教师用户看到的）(管理助教)界面
    /**
     * 获取助教管理页面(包含我的助教列表+申请助教列表)
     * 127.0.0.1:8080/tams/portal/ta?methodToCall=getTaManagementPage&viewId=TaView
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getTaManagementPage")
    public ModelAndView getTaManagementPage(@ModelAttribute("KualiForm") UifFormBase form,
                                            HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        String classId = infoForm.getCurrClassId();

        infoForm.setCanEmploy(classInfoService.canEmployByClassId(classId));

        infoForm.setAllMyTa(taConverter.myTaCombinePayDayClass(
                classInfoService.getAllTaFilteredByClassid(classId)
        ));


        infoForm.setAllApplication(taConverter.applicationToViewObjectClass(
                taService.getAllApplicationFilterByClassid(classId)
        ));
        return this.getModelAndView(infoForm, "pageTaManagement");
    }


    /**
     * 聘请弹dialog
     */
    @RequestMapping(params = "methodToCall=showEmployDialog")
    public ModelAndView showEmployDialog(@ModelAttribute("KualiForm") UifFormBase form,
                               HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        List<MyTaViewObject> applicationList = infoForm.getAllApplication();

        //遍历所有list，找到选中的行
        List<MyTaViewObject> checkedList = new ArrayList<>();
        for(MyTaViewObject per : applicationList) {
            if(per.isCheckBox())
                checkedList.add(per);
        }

        if(checkedList.size()==0){
            return this.getModelAndView(infoForm, "pageTaManagement");

        }else{
            return this.showDialog("confirmEmployDialog", true, infoForm);
        }

    }

    /**
     * 聘请助教
     */

    @RequestMapping(params = "methodToCall=employ")
    public ModelAndView employ(@ModelAttribute("KualiForm") UifFormBase form,
                               HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        List<MyTaViewObject> applicationList = infoForm.getAllApplication();
        String classId = infoForm.getCurrClassId();
        if(classId==null){
            infoForm.setErrMsg("系统异常，请联系管理员！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }

        TAMSClassTaApplication tamsClassTaApplication = tamsClassTaApplicationDao.selectByClassId(infoForm.getCurrClassId());
        List<TAMSTa> taList = classInfoService.getAllTaFilteredByClassid(classId);
        int appTaNumber = tamsClassTaApplication.getTaNumber();
        //遍历所有list，找到选中的行
        List<MyTaViewObject> checkedList = new ArrayList<>();
        for(MyTaViewObject per : applicationList) {
            if(per.isCheckBox())
                checkedList.add(per);
        }

        if(((taList==null?0:taList.size())+checkedList.size()) > appTaNumber){
            infoForm.setErrMsg("您的聘用人数将超过限额！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }

        if(tamsClassTaApplication==null){
            infoForm.setErrMsg("您没有申请助教无法聘用！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }

        if(tamsClassTaApplication.getTaNumber() == null){
            infoForm.setErrMsg("您没有申请助教无法聘用！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }




        int addSize = taService.employBatchByStuIdsWithClassId(
                classConverter.extractIdsFromApplication(checkedList)
        );


        for(MyTaViewObject needToAdd : checkedList){
            needToAdd.setCheckBox(false);
            needToAdd.setPayDay("暂未设置");
            needToAdd.setStatus("1");
            //若助教列表为空，直接添加
            if(infoForm.getAllMyTa().size() == 0)
                infoForm.getAllMyTa().add(needToAdd);
            //若助教列表为一个空对象，则先删除空对象
            else if(infoForm.getAllMyTa().get(0).getTaName() == null) {
                infoForm.getAllMyTa().remove(0);
                infoForm.getAllMyTa().add(needToAdd);
            }
            //否则直接添加
            else
                infoForm.getAllMyTa().add(needToAdd);

            MDC.put("remoteHost",request.getRemoteAddr());
            logger.info("教师"+GlobalVariables.getUserSession().getPrincipalName()+"聘用了助教："+needToAdd.getTaName());

            infoForm.getAllApplication().remove(needToAdd);

            //聘用之后申请列表为空，往申请列表中添加一个空对象
            if(infoForm.getAllApplication().size() == 0){
                List<MyTaViewObject> nullObject = new ArrayList<>();
                nullObject.add(new MyTaViewObject());
                infoForm.getAllApplication().addAll(nullObject);
            }
        }

        //开始释放剩下的tapplication
        if((addSize+(taList==null?0:taList.size()))>=appTaNumber){
            classInfoService.releaseTaApplication(infoForm.getAllApplication());
            //聘请满了之后将状态切换到最终工作状态
            classInfoService.changeToSpecificStatus(classId,classInfoService.getMaxOrderStatusIdOfSpecificFunction("1"));

            infoForm.getAllApplication().clear();
            infoForm.getAllApplication().add(new MyTaViewObject());
            infoForm.setAllApplication(infoForm.getAllApplication());
        }

        if(addSize>=0){
            return this.getModelAndView(infoForm, "pageTaManagement");
        }

        else {
            infoForm.setErrMsg("聘用出错！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
    }


    /**
     * 恢复助教
     * 属于TaManagementPage
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=setTaToLiving")
    public ModelAndView setTaToLiving(@ModelAttribute("KualiForm") UifFormBase form,
                                      HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        List<MyTaViewObject> objects = infoForm.getAllMyTa();

        List<MyTaViewObject> isOkToChange = new ArrayList<>();

        for (MyTaViewObject per : objects) {
            if (per.isCheckBox() && per.getStatus().equals(TA_STATUS.PAUSED))
                isOkToChange.add(per);
        }

        if (isOkToChange.isEmpty()) {
            return this.getTaManagementPage(form, request);
        }

        boolean result = taService.changeStatusBatchByTaIds(
                classConverter.extractIdsFromMyTaInfo(isOkToChange),
                TA_STATUS.LIVING
        );

        if (result){

            MDC.put("remoteHost",request.getRemoteAddr());
            logger.info("教师"+GlobalVariables.getUserSession().getPrincipalName()+"恢复了助教");

            return this.getTaManagementPage(form, request);
        }

        else {
                infoForm.setErrMsg("恢复出错！");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
    }


    /**
     * 暂停助教
     * 属于TaManagementPage
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=setTaToPause")
    public ModelAndView setTaToPause(@ModelAttribute("KualiForm") UifFormBase form,
                                     HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        List<MyTaViewObject> objects = infoForm.getAllMyTa();

        List<MyTaViewObject> isOkToChange = new ArrayList<>();

        for (MyTaViewObject per : objects) {
            if (per.isCheckBox() && per.getStatus().equals(TA_STATUS.LIVING))
                isOkToChange.add(per);
        }

        int n = isOkToChange.size();

        if (isOkToChange.isEmpty()) {
            return this.getTaManagementPage(form, request);
        }

        n = classConverter.extractIdsFromMyTaInfo(isOkToChange).size();

        boolean result = taService.changeStatusBatchByTaIds(
                classConverter.extractIdsFromMyTaInfo(isOkToChange),
                TA_STATUS.PAUSED
        );

        if (result){

            MDC.put("remoteHost",request.getRemoteAddr());
            logger.info("教师"+GlobalVariables.getUserSession().getPrincipalName()+"暂停了助教");

            return this.getTaManagementPage(form, request);
        }

        else {
                infoForm.setErrMsg("暂停助教出错！");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
    }

    /**
     * 解聘助教
     * 属于TaManagementPage
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=setTaToDismiss")
    public ModelAndView setTaToDismiss(@ModelAttribute("KualiForm") UifFormBase form,
                                       HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);
        List<MyTaViewObject> objects = infoForm.getAllMyTa();

        List<MyTaViewObject> needToFire = new ArrayList<>();

        for (MyTaViewObject per : objects) {
            if (per.isCheckBox() )
                needToFire.add(per);
        }

        if (needToFire.isEmpty()) {
            return this.getModelAndView(infoForm, "pageTaManagement");
        }

        Boolean result = taService.dismissTa(classConverter.extractIdsAndClassIdsFromMyTaInfo(needToFire),GlobalVariables.getUserSession().getPrincipalId());

        for(MyTaViewObject needTodelete : needToFire){
            infoForm.getAllMyTa().remove(needTodelete);
        }

        if(infoForm.getAllMyTa().size()==0){
            infoForm.getAllMyTa().add(new MyTaViewObject());
        }


        if(result){

            MDC.put("remoteHost",request.getRemoteAddr());
            logger.info("教师"+GlobalVariables.getUserSession().getPrincipalName()+"暂停了助教");
            //return this.getTaManagementPage(form, request);
            return this.getModelAndView(infoForm, "pageTaManagement");

        }
        else{
            infoForm.setErrMsg("解聘助教出错！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }

    }

    /**
     * 解聘助教弹框
     * 属于TaManagementPage
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=showDismissDialog")
    public ModelAndView showDismissDialog(@ModelAttribute("KualiForm") UifFormBase form,
                                       HttpServletRequest request) {

        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);
        List<MyTaViewObject> objects = infoForm.getAllMyTa();

        List<MyTaViewObject> needToFire = new ArrayList<>();

        for (MyTaViewObject per : objects) {
            if (per.isCheckBox())
                needToFire.add(per);
        }
        if(needToFire.size()==0){
            return this.getModelAndView(infoForm, "pageTaManagement");

        }else{
            return this.showDialog("confirmDismissDialog", true, infoForm);
        }
    }


    /**
     * 助教管理页面，输入姓名或学号，查询助教
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=searchTaByCondition")
    public ModelAndView searchTaByCondition(@ModelAttribute("KualiForm") UifFormBase form,
                                            HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);
        // TODO: 2016/11/24 要获取前端输入的姓名、学号，所以需要在form中添加对应属性并修改TaManagementPage.xml中375行左右参数名。(现在前端用的是inputField8/9)


        // TODO: 2016/11/24 下面为测试用代码，需要添加一个新的存储符合条件ta列表的属性 ，同时修改TaManagementPage.xml中约326行的propertyName
        // TODO: 2016/11/24 注意：需要在TaInfoForm中为新添加的属性赋初始值(List<xx> xxlist=new arraylist<>();) 否则页面加载时会出错
        /*
        List<MyTaViewObject> list= taInfoForm.getConditionTAList();
        MyTaViewObject newobj=new MyTaViewObject();
        newobj.setTaName("Zsf");
        newobj.setTaIdNumber("20135040");
        list.add(newobj);
        */
//        taInfoForm.setConditionTAList(list);
        Map<String, String> conditions = new HashMap<>();
        //put conditions
        conditions.put("StudentName", infoForm.getStudentName());
        conditions.put("StudentId", infoForm.getStudentNumber());
        infoForm.setConditionTAList(
                classConverter.studentInfoToMyTaViewObject(
                        taService.getConditionTaByNameAndId(conditions)
                )
        );

        //清除table页面信息缓存
        Map map = new HashMap();
        map.putAll(infoForm.getViewPostMetadata().getComponentPostMetadataMap().get("searchTaApplicantList").getData());
        map.put("displayStart",0);
        infoForm.getViewPostMetadata().getComponentPostMetadataMap().get("searchTaApplicantList").setData(map);

        return this.getModelAndView(infoForm, "pageTaManagement");
    }

    /**
     * 助教管理页面，输入姓名或学号查询得到助教列表后，点击助教列表中某一行查看该助教的具体信息
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getSelectedTaInfo")
    public ModelAndView getSelectedTaInfo(@ModelAttribute("KualiForm") UifFormBase form,
                                          HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
        int index = params.getSelectedLineIndex();

        infoForm.setSelectedTa(infoForm.getConditionTAList().get(index));

        return this.getModelAndView(infoForm, "pageTaManagement");
    }

    /**
     * 在dialog中'确定'对应的后台方法
     * 首先需要判断当前是否有选中有效目标
     * 如果没选中，则弹出errDialog
     * 已选中则将目标ta加入到候选人列表(allApplication),并刷新整个页面
     * @param form
     * @param request
     * @return
     */
    @RequestMapping(params = "methodToCall=addSelectedTaApplicant")
    public ModelAndView addSelectedTaApplicant(@ModelAttribute("KualiForm") UifFormBase form,
                                               HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);
        String classId = infoForm.getCurrClassId();

        MyTaViewObject curTa = infoForm.getSelectedTa();
        curTa.setApplicationClassId(classId);
        List<MyTaViewObject> needToBeAddToApplication = new ArrayList<>();
        //如果没有选择任何人
        if (curTa.getTaIdNumber() == null) {
            infoForm.setErrMsg("请选择某位学生！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        } else if(infoForm.getCandidatePhoneNbr()==null) {
            infoForm.setErrMsg("请填写助教申请人的联系电话！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        } else if(infoForm.getCandidateBankNbr()==null) {
            infoForm.setErrMsg("请填写助教申请人的银行卡号！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }else if(infoForm.getCandidateBankName()==null) {
            infoForm.setErrMsg("请填写助教申请人的发卡行名称！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }else {
            curTa.setApplicationClassId(classId);
            needToBeAddToApplication.add(curTa);
            String phoneNbr = infoForm.getCandidatePhoneNbr();
            String bankName = infoForm.getCandidateBankName();
            String bankNbr = infoForm.getCandidateBankNbr();
            short code = taService.submitApplicationAssistant(
                    classConverter.TaViewObjectToTaApplication(curTa,classId,phoneNbr,bankName,bankNbr)
            );
            curTa.setContactPhone(phoneNbr);
            if (code == 10) {
                infoForm.setErrMsg("管理员未设置申请时间！");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            }else if (code == 1) {
                infoForm.setErrMsg("非助教申请时间！");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            } else if (code == 2) {
                infoForm.setErrMsg("您的申请已提交，请勿重复提交");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            } else if (code == 3) {
                infoForm.setErrMsg("已经被此课程聘用！");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            } else if (code == 7) {
                infoForm.setErrMsg("该学生最多可申请两门课程的助教！");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            } else if (code == 8) {
                infoForm.setErrMsg("该学生最多可担任两门课程的助教！");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            } else if (code == 4) {
                //避免延迟刷新
                //if (infoForm.getAllApplication() == null)
                //    infoForm.setAllApplication(needToBeAddToApplication);
                //如果为空，即没有对象
                if(infoForm.getAllApplication().size() == 0)
                    infoForm.getAllApplication().addAll(needToBeAddToApplication);
                //如果为一个空对象
                if(infoForm.getAllApplication().get(0).getTaName() == null) {
                    infoForm.getAllApplication().remove(0);
                    infoForm.getAllApplication().addAll(needToBeAddToApplication);
                }
                //否则直接添加
                else
                    infoForm.getAllApplication().addAll(needToBeAddToApplication);

                MDC.put("remoteHost",request.getRemoteAddr());
                logger.info("教师"+GlobalVariables.getUserSession().getPrincipalName()+"将"+curTa.getTaName()+"加入了候选人列表");

                infoForm.getConditionTAList().remove(curTa);
                return this.getModelAndView(infoForm, "pageTaManagement");
            } else {
                infoForm.setErrMsg("未知错误");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            }
        }
    }

    @RequestMapping(params =  {"methodToCall=selectCurSession"})
    public ModelAndView selectCurSession(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);
        utSessionDao.setCurrentSession(utSessionDao.getUTSessionById(Integer.parseInt(infoForm.getSessionTermFinder())));
        return this.getClassListPage(form);
    }

    /**
     * 申请优秀助教页面
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getApplyOutStandingClassPage")
    public ModelAndView getApplyOutStandingClassPage(@ModelAttribute("KualiForm") UifFormBase form,
                                          HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        return this.getModelAndView(infoForm, "pageApplyOutStandingClass");
    }

    @RequestMapping(params = "methodToCall=ApplyOutStanding")
    public ModelAndView ApplyOutStanding(@ModelAttribute("KualiForm") UifFormBase form,
                                                     HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        String applyOSReason = infoForm.getApplyOutStandingReason();
        String classId = infoForm.getCurrClassId();
        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        String stuId = userSession.getLoggedInUserPrincipalId();

        if(applyOSReason.isEmpty()) {
            infoForm.setErrMsg("请填写申请优秀助教理由！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        else {
            if(classInfoService.applyOutStanding(applyOSReason, stuId, classId) == 2){
                infoForm.setErrMsg("您的申请已提交！请勿重复申请");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            }
            else {
                infoForm.setErrMsg("申请成功！");
                    //return this.showDialog("refreshPageViewDialog", true, infoForm);
            }

        }
        return this.getModelAndView(infoForm, "pageApplyOutStandingClass");
    }

}
