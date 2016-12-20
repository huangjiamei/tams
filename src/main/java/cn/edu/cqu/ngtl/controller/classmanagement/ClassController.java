package cn.edu.cqu.ngtl.controller.classmanagement;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.controller.BaseController;
import cn.edu.cqu.ngtl.dao.tams.impl.TAMSWorkflowStatusDaoJpa;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dataobject.enums.TA_STATUS;
import cn.edu.cqu.ngtl.dataobject.tams.*;
import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.form.classmanagement.ClassInfoForm;
import cn.edu.cqu.ngtl.service.classservice.IClassInfoService;
import cn.edu.cqu.ngtl.service.common.ExcelService;
import cn.edu.cqu.ngtl.service.common.impl.TamsFileControllerServiceImpl;
import cn.edu.cqu.ngtl.service.exportservice.IPDFService;
import cn.edu.cqu.ngtl.service.riceservice.IClassConverter;
import cn.edu.cqu.ngtl.service.riceservice.ITAConverter;
import cn.edu.cqu.ngtl.service.taservice.ITAService;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassDetailInfoViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassTeacherViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.MyTaViewObject;
import com.itextpdf.text.DocumentException;
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

import static org.kuali.rice.krad.util.GlobalVariables.getUserSession;

/**
 * Created by tangjing on 16-10-20.
 * 课程管理相关的view及function
 */
@Controller
@RequestMapping("/class")
public class ClassController extends BaseController {

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

    @RequestMapping(params = "methodToCall=logout")
    public ModelAndView logout(@ModelAttribute("KualiForm") UifFormBase form) throws Exception {
        String redirctURL = ConfigContext.getCurrentContextConfig().getProperty(KRADConstants.APPLICATION_URL_KEY) + "/portal/home?methodToCall=logout&viewId=PortalView";
        return this.performRedirect(form, redirctURL);
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
            infoForm.setUser(user);
                infoForm.setClassList(
                        taConverter.classInfoToViewObject(
                                classInfoService.getAllClassesFilterByUid(user.getCode())
                        )
                );

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
        //遍历所有list，找到选中的行
        List<ClassTeacherViewObject> checkedList = new ArrayList<>();
        for(ClassTeacherViewObject per : classList) {
            if(per.isChecked())
                checkedList.add(per);
        }

        String uid = GlobalVariables.getUserSession().getPrincipalId();

        TAMSWorkflowStatus newStatus = new TAMSWorkflowStatusDaoJpa().getOneById(infoForm.getApproveReasonOptionFinder());
        String newStatusName = newStatus.getWorkflowStatus();
        Integer newStatusOrder = newStatus.getOrder();
        Integer maxOrder = new TAMSWorkflowStatusDaoJpa().getMaxOrderByFunctionId("1");

        boolean result = false;
        String feedBackReason = infoForm.getApproveReason();
        for(ClassTeacherViewObject classTeacherViewObject:checkedList) {
            if(newStatusOrder==maxOrder){
                classInfoService.validClassFunds(classTeacherViewObject.getId());
            }
            result = classInfoService.classStatusToCertainStatus(
                    uid,
                    classTeacherViewObject.getId(),
                    infoForm.getApproveReasonOptionFinder()
            );
            classInfoService.insertFeedBack(classTeacherViewObject.getId(),uid,feedBackReason,classTeacherViewObject.getStatus(),newStatusName);
        }
        if(result)
            return this.getClassListPage(infoForm);
        else  //应当返回错误信息
            return this.getClassListPage(infoForm);
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
        }

        if(result)
            return this.getClassListPage(infoForm);
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
            CollectionControllerServiceImpl.CollectionActionParameters params =
                    new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
            int index = params.getSelectedLineIndex();

            ClassTeacherViewObject classObject = infoForm.getClassList().get(index);
            /**
             * param in end
             */
            String classId = classObject.getId();
            infoForm.setCurrClassId(classId);

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

        infoForm.setApplicationPhoneNbr(taService.getApplicationPhoneNbr(stuId,classId)); //设置申请人电话号码
        infoForm.setApplyReason(taService.getApplicationReason(stuId,classId));   //设置申请人理由

        infoForm.setApplyAssistantViewObject(
                taConverter.applyAssistantToTableViewObject(
                        classInfoService.getStudentInfoById(stuId),
                        classInfoService.getClassInfoById(classId)
                )
        );

        return this.getModelAndView(infoForm, "pageApplyForTaForm");
    }

    @RequestMapping(params = {"pageId=pageApplyForTaForm", "methodToCall=submitTaForm"})
    public ModelAndView submitTaForm(@ModelAttribute("KualiForm") UifFormBase form) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);

        if(infoForm.getApplicationPhoneNbr()==null){
            infoForm.setErrMsg("请申请人填写本人联系电话！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }
        if(infoForm.getApplyReason()==null){
            infoForm.setErrMsg("请申请人填写申请理由！");
            return this.showDialog("refreshPageViewDialog",true,infoForm);
        }

        taService.submitApplicationAssistant(taConverter.submitInfoToTaApplication(infoForm));

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

        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        String uId = userSession.getLoggedInUserPrincipalId();

        String classId = infoForm.getCurrClassId();
        if (classId == null) //// FIXME: 16-11-18 不是跳转过来应该跳转到报错页面
            return this.getModelAndView(infoForm, "pageTeachingCalendar");

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
            infoForm.setAllActivities(
                    taConverter.activitiesToViewObject(
                            classInfoService.getAllTaTeachActivityAsCalendarFilterByUidAndClassId(
                                    uId, classId)
                    )
            );
            return this.getModelAndView(infoForm, "pageViewTeachingCalendar");
        }
        catch (IndexOutOfBoundsException e) {
            // 应该返回错误页面，选择数据在内存中不存在，可能存在脏数据
            return this.getModelAndView(infoForm, "pageViewTeachingCalendar");
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
        if (classId == null || calendarId == null) //// FIXME: 16-11-18 不是跳转过来应该跳转到报错页面
            return this.getModelAndView(infoForm, "pageViewTeachingCalendar");

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
        if (classId == null) //// FIXME: 16-11-18 不是跳转过来应该跳转到报错页面
            return this.getModelAndView(infoForm, "pageViewTeachingCalendar");

        CollectionControllerServiceImpl.CollectionActionParameters params = new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
        int index = params.getSelectedLineIndex();

        try {
            String attachmentId = infoForm.getCalendarFiles().get(index).getId();

            classInfoService.removeCalendarFileById(classId, attachmentId);
            infoForm.getCalendarFiles().remove(index);
            return this.getModelAndView(infoForm, "pageViewTeachingCalendar");        }
        catch (IndexOutOfBoundsException e) {
            return this.getModelAndView(infoForm, "pageViewTeachingCalendar");        }
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
            //添加附件
            if(added.isHasAttachment()) {
                new TamsFileControllerServiceImpl().saveCalendarAttachments(uId, classId, added.getId(), infoForm.getFileList());
            }

            return this.getTeachingCalendar(infoForm, request);
        }
        return this.getModelAndView(infoForm, "pageTeachingCalendar");
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

        if (classInfoService.removeTeachCalenderById(uId, classId, teachCalendarId)) {
            //删除教学日历的附件信息
            boolean result = classInfoService.removeAllCalendarFilesByClassIdAndCalendarId(
                    classId, teachCalendarId);

            return this.getTeachingCalendar(infoForm, request);
        }
        else //// FIXME: 16-11-18 应当返回错误页面
            infoForm.setErrMsg("删除失败！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
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
        if (classId == null) //// FIXME: 16-11-18 不是跳转过来应该跳转到报错页面
            return this.getModelAndView(infoForm, "pageTeachingCalendar");

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
        conditions.put("InstructorName", infoForm.getCondInstructorName());
        conditions.put("CourseName", infoForm.getCondCourseName());
        conditions.put("CourseCode", infoForm.getCondCourseCode());
        conditions.put("StatusId", infoForm.getCourseStatus());

        infoForm.setClassList(
                taConverter.classInfoToViewObject(
                        classInfoService.getAllClassesFilterByUidAndCondition(uId, conditions)
                )
        );

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

        infoForm.setFeedbacks(taConverter.feedBackToViewObject(classInfoService.getFeedBackByClassId(classId)));

        infoForm.setClassEvaluations(new ArrayList<TAMSClassEvaluation>());

        return this.getModelAndView(infoForm, "pageRequestTa");
    }

    /**
     * 成绩评定删除辅助方法
     *
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=deleteEvaluationLine")
    public ModelAndView deleteEvaluationLine(@ModelAttribute("KualiForm") UifFormBase form) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);
        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
        int index = params.getSelectedLineIndex();

        infoForm.getClassEvaluations().remove(index);

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
        String instructorId = getUserSession().getPrincipalId();
        boolean result = classInfoService.instructorAddClassTaApply(instructorId, classId, assistantNumber, classEvaluations);
        if(result)
            return this.getModelAndView(infoForm, "pageRequestTa");
        else
            infoForm.setErrMsg("您已经提交过申请，请等待审批结果！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
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

        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        String uId = userSession.getLoggedInUserPrincipalId();

        String classId = infoForm.getCurrClassId();

        infoForm.setAllMyTa(taConverter.myTaCombinePayDayClass(
                classInfoService.getAllTaFilteredByClassid(classId)
        ));


        infoForm.setAllApplication(taConverter.applicationToViewObjectClass(
                taService.getAllApplicationFilterByClassid(classId)
        ));
        return this.getModelAndView(infoForm, "pageTaManagement");
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

        //遍历所有list，找到选中的行
        List<MyTaViewObject> checkedList = new ArrayList<>();
        for(MyTaViewObject per : applicationList) {
            if(per.isCheckBox())
                checkedList.add(per);
        }

        boolean result = taService.employBatchByStuIdsWithClassId(
                classConverter.extractIdsFromApplication(checkedList)
        );
        for(MyTaViewObject needToAdd : checkedList){
            needToAdd.setCheckBox(false);
            needToAdd.setPayDay("暂未设置");
            needToAdd.setStatus("1");
            infoForm.getAllMyTa().add(needToAdd);
            infoForm.getAllApplication().remove(needToAdd);
        }
        if(result)
            return this.getModelAndView(infoForm, "pageTaManagement");
        else
            return this.getModelAndView(infoForm, "pageTaManagement"); //应该返回错误信息
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

        if (result)
            return this.getTaManagementPage(form, request);
        else {
            // TODO: 2016/11/12 等待错误信息设计
            return this.getTaManagementPage(form, request);
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

        if (result)
            return this.getTaManagementPage(form, request);
        else {
            // TODO: 2016/11/12 等待错误信息设计
            return this.getTaManagementPage(form, request);
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
        // TODO: 2016/11/12 等待需求
        return this.getTaManagementPage(form, request);
    }



    /**
     * 测试用方法：
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
        String classid = infoForm.getCurrClassId();

        MyTaViewObject curTa=infoForm.getSelectedTa();
        curTa.setApplicationClassId(classid);
        List<MyTaViewObject> needToBeAddToApplication = new ArrayList<>();
        needToBeAddToApplication.add(curTa);

        boolean result = taService.submitApplicationAssistant(
                classConverter.TaViewObjectToTaApplication(curTa, classid)
        );

        if(result){
            //避免延迟刷新
            if(infoForm.getAllApplication()==null)
                infoForm.setAllApplication(needToBeAddToApplication);
            else
                infoForm.getAllApplication().addAll(needToBeAddToApplication);

            infoForm.getConditionTAList().remove(curTa);
            return this.getModelAndView(infoForm, "pageTaManagement");
        }
        else
            return this.getModelAndView(infoForm, "pageTaManagement");
    }

    @RequestMapping(params =  {"methodToCall=selectCurSession"})
    public ModelAndView selectCurSession(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        super.baseStart(infoForm);
        utSessionDao.setCurrentSession(utSessionDao.getUTSessionById(Integer.parseInt(infoForm.getSessionTermFinder())));
        return this.getClassListPage(form);
    }

}
