package cn.edu.cqu.ngtl.controller.tamanagement;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.controller.BaseController;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dataobject.enums.TA_STATUS;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaTravelSubsidy;
import cn.edu.cqu.ngtl.form.tamanagement.TaInfoForm;
import cn.edu.cqu.ngtl.service.common.ExcelService;
import cn.edu.cqu.ngtl.service.common.WorkFlowService;
import cn.edu.cqu.ngtl.service.common.impl.IdstarIdentityManagerServiceImpl;
import cn.edu.cqu.ngtl.service.exportservice.IPDFService;
import cn.edu.cqu.ngtl.service.riceservice.ITAConverter;
import cn.edu.cqu.ngtl.service.taservice.ITAService;
import cn.edu.cqu.ngtl.service.userservice.IUserInfoService;
import cn.edu.cqu.ngtl.viewobject.tainfo.IssueViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.TaInfoViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.WorkBenchViewObject;
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

/**
 * Created by tangjing on 16-10-19.
 * 助教信息查看的相关view及function
 */
@Controller
@RequestMapping("/ta")
public class TaController extends BaseController {

    private static final Integer ONE_TRAVEL_SUBSIDY = 10;

    @Autowired
    private ITAConverter taConverter;

    @Autowired
    private ITAService taService;

    @Autowired
    private UTSessionDao utSessionDao;

    @Autowired
    private IPDFService PDFService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private WorkFlowService workFlowService;

    @Autowired
    private IUserInfoService userInfoService;

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
     * 获取助教列表页面(教师看到自己的助教，管理员看到所有人的助教)
     * 127.0.0.1:8080/tams/portal/ta?methodToCall=getTaListPage&viewId=TaView
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getTaListPage")
    public ModelAndView getTaListPage(@ModelAttribute("KualiForm") UifFormBase form,
                                      HttpServletRequest request) {
        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);

        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        String uId = userSession.getLoggedInUserPrincipalId();

        if(userInfoService.isSysAdmin(uId)||userInfoService.isAcademicAffairsStaff(uId)||userInfoService.isCollegeStaff(uId)||userInfoService.isCourseManager(uId)){
            taInfoForm.setCanApprise(true);
        }else{
            taInfoForm.setCanApprise(false);
        }

        taInfoForm.setUser((User)GlobalVariables.getUserSession().retrieveObject("user"));
        taInfoForm.setAllTaInfo(taConverter.taCombineDetailInfo(
                taService.getAllTaFilteredByUid(uId)
        ));
        taInfoForm.setCheckedTaListAll(false);//刷新页面，全选框不选。

        return this.getModelAndView(taInfoForm, "pageTaList");
    }

    /**
     * 助教页面checkbox全选
     */
    @RequestMapping(params = "methodToCall=checkTaListAllButton")
    public ModelAndView checkTaListAllButton(@ModelAttribute("KualiForm") UifFormBase form,
                                             HttpServletRequest request) {
        TaInfoForm taInfoForm = (TaInfoForm) form;
        super.baseStart(taInfoForm);

        for(TaInfoViewObject taInfoViewObject:taInfoForm.getAllTaInfo()){
            taInfoViewObject.setCheckBox(taInfoForm.getCheckedTaListAll());
        }
        return this.getModelAndView(taInfoForm, "pageTaList");
    }

    /**
     * 助教列表过滤器
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=searchTaList")
    public ModelAndView searchTaList(@ModelAttribute("KualiForm") UifFormBase form,HttpServletRequest request){
        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);
        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        String uId = userSession.getLoggedInUserPrincipalId();
        Map<String, String> conditions = new HashMap<>();
        conditions.put("taName", taInfoForm.getTaAssitantName());
        conditions.put("taId", taInfoForm.getTaAssitantIDNumber());
        conditions.put("taDegree", taInfoForm.getTaCategoryName());
        conditions.put("taCourseName", taInfoForm.getTaCourseName());
        conditions.put("taCourseCode", taInfoForm.getTaCourseCode() == null ? null : taInfoForm.getTaCourseCode().toUpperCase());
        conditions.put("taClassNumber", taInfoForm.getTaClassNumber());
        conditions.put("taTeacherName", taInfoForm.getTaTeacherName());
        conditions.put("taTeacherAppraise", taInfoForm.getTaTeacherAppraise());
        conditions.put("taStuAppraise", taInfoForm.getTaStuAppraise());
        conditions.put("taTaScore", taInfoForm.getTaScore());
        conditions.put("taStatus", taInfoForm.getTaStatus());

        taInfoForm.setAllTaInfo(taConverter.getTaInfoListByConditions(conditions,uId));

        System.out.println(taConverter.getTaInfoListByConditions(conditions,uId));

        //清除table页面信息缓存
        Map map = new HashMap();
        map.putAll(taInfoForm.getViewPostMetadata().getComponentPostMetadataMap().get("taListTable").getData());
        map.put("displayStart",0);
        taInfoForm.getViewPostMetadata().getComponentPostMetadataMap().get("taListTable").setData(map);

        return this.getModelAndView(taInfoForm, "pageTaList");
    }

    /**
     * 恢复助教状态
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=recover")
    public ModelAndView recover(@ModelAttribute("KualiForm") UifFormBase form,
                                HttpServletRequest request) {
        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);

        List<TaInfoViewObject> taList = taInfoForm.getAllTaInfo();


        //遍历所有list，找到选中的行
        List<TaInfoViewObject> checkedList = new ArrayList<>();
        for(TaInfoViewObject per : taList) {
            if(per.isCheckBox() && per.getStatus().equals(TA_STATUS.PAUSED))
                checkedList.add(per);
        }

        boolean result = taService.changeStatusBatchByIds(
                taConverter.extractIdsFromTaInfo(checkedList),
                TA_STATUS.LIVING
        );

        if(result)
            return this.getTaListPage(form, request);
        else
            return this.getTaListPage(form, request); //应该返回错误信息
    }

    /**
     * 暂停助教状态
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=pause")
    public ModelAndView pause(@ModelAttribute("KualiForm") UifFormBase form,
                              HttpServletRequest request) {
        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);

        List<TaInfoViewObject> taList = taInfoForm.getAllTaInfo();

        //遍历所有list，找到选中的行
        List<TaInfoViewObject> checkedList = new ArrayList<>();
        for(TaInfoViewObject per : taList) {
            if(per.isCheckBox() && per.getStatus().equals(TA_STATUS.LIVING))
                checkedList.add(per);
        }

        boolean result = taService.changeStatusBatchByIds(
                taConverter.extractIdsFromTaInfo(checkedList),
                TA_STATUS.PAUSED
        );

        if(result)
            return this.getTaListPage(form, request);
        else
            return this.getTaListPage(form, request); //应该返回错误信息
    }

/*
    */
/**
     * 助教撤销优秀
     * @param form
     * @return
     *//*

    @RequestMapping(params = "methodToCall=revocationOutstanding")
    public ModelAndView revocationOutstanding(@ModelAttribute("KualiForm") UifFormBase form,
                                            HttpServletRequest request) {
        TaInfoForm taInfoForm = (TaInfoForm) form;
        super.baseStart(taInfoForm);

        List<TaInfoViewObject> taList = taInfoForm.getAllTaInfo();
        List<Integer> checkListIndex = new ArrayList<>();

        //遍历所有list，找到选中的行
        List<TaInfoViewObject> checkedList = new ArrayList<>();
        for(int i = 0 ;i<taList.size() ; i++) {
            if(taList.get(i).isCheckBox()) {
                checkedList.add(taList.get(i));
                checkListIndex.add(i);
            }
        }

        String uid = GlobalVariables.getUserSession().getPrincipalId();
        boolean result = taService.appraiseOutstandingToSpecifiedStatus(
                taConverter.extractIdsFromTaInfo(checkedList),
                uid,taInfoForm.getRevocationReasonOptionFinder()
        );


        for(Integer i:checkListIndex){
            taInfoForm.getAllTaInfo().get(i).setStatusId(taInfoForm.getRevocationReasonOptionFinder());
        }

        if(result)
            return this.getModelAndView(taInfoForm, "pageTaList")
        else {
            taInfoForm.setErrMsg("撤销助教出现错误");
            return this.showDialog("refreshPageViewDialog", true, taInfoForm);
        }
    }
*/

    /**
     * 助教评优
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=appraiseOutstanding")
    public ModelAndView appraiseOutstanding(@ModelAttribute("KualiForm") UifFormBase form,
                              HttpServletRequest request) {
        TaInfoForm taInfoForm = (TaInfoForm) form;
        super.baseStart(taInfoForm);

        List<TaInfoViewObject> taList = taInfoForm.getAllTaInfo();

        if(taInfoForm.getAppraiseReasonOptionFinder()==null){
            taInfoForm.setErrMsg("请选择助教需要转到的状态！");
            return this.showDialog("refreshPageViewDialog",true,taInfoForm);
        }


        List<Integer> checkListIndex = new ArrayList<>();

        //遍历所有list，找到选中的行
        List<TaInfoViewObject> checkedList = new ArrayList<>();
        for(int i = 0 ;i<taList.size() ; i++) {
            if(taList.get(i).isCheckBox()) {
                checkedList.add(taList.get(i));
                checkListIndex.add(i);
            }
        }

        String uid = GlobalVariables.getUserSession().getPrincipalId();
        boolean result = taService.appraiseOutstandingToSpecifiedStatus(
                taConverter.extractIdsFromTaInfo(
                        checkedList),
                        uid,
                        taInfoForm.getAppraiseReasonOptionFinder()
        );

        for(Integer i:checkListIndex){
            taInfoForm.getAllTaInfo().get(i).setStatus(workFlowService.getWorkFlowStatusName(taInfoForm.getAppraiseReasonOptionFinder()));
        }

        if(result)
            return this.getModelAndView(taInfoForm, "pageTaList");
        else {
            taInfoForm.setErrMsg("评优失败！");
            return this.showDialog("refreshPageViewDialog", true, taInfoForm);
        }
    }

    //我的助教（教师用户看到的）(管理助教)界面
//    /**
//     * 获取助教管理页面(包含我的助教列表+申请助教列表)
//     * 127.0.0.1:8080/tams/portal/ta?methodToCall=getTaManagementPage&viewId=TaView
//     * @param form
//     * @return
//     */
//    @RequestMapping(params = "methodToCall=getTaManagementPage")
//    public ModelAndView getTaManagementPage(@ModelAttribute("KualiForm") UifFormBase form,
//                                            HttpServletRequest request) {
//        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);
//
//        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
//        String uId = userSession.getLoggedInUserPrincipalId();
//        taInfoForm.setAllMyTa(taConverter.myTaCombinePayDay(
//                taService.getAllTaFilteredByUid(uId)
//        ));
//
//
//        taInfoForm.setAllApplication(taConverter.applicationToViewObject(
//                taService.getAllApplicationFilterByUid(uId)
//        ));
//
//        return this.getModelAndView(taInfoForm, "pageTaManagement");
//    }

//    /**
//     * 聘请助教
//     * @param form
//     * @param request
//     * @return
//     */
//    @RequestMapping(params = "methodToCall=employ")
//    public ModelAndView employ(@ModelAttribute("KualiForm") UifFormBase form,
//                                            HttpServletRequest request) {
//        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);
//
//        List<MyTaViewObject> applicationList = taInfoForm.getAllApplication();
//
//        //遍历所有list，找到选中的行
//        List<MyTaViewObject> checkedList = new ArrayList<>();
//        for(MyTaViewObject per : applicationList) {
//            if(per.isCheckBox())
//                checkedList.add(per);
//        }
//
//        boolean result = taService.employBatchByStuIdsWithClassId(
//                taConverter.extractIdsFromApplication(checkedList)
//        );
//        for(MyTaViewObject needToAdd : checkedList){
//            needToAdd.setCheckBox(false);
//            taInfoForm.getAllMyTa().add(needToAdd);
//            taInfoForm.getAllApplication().remove(needToAdd);
//        }
//
//
//        if(result)
//            return this.getModelAndView(taInfoForm, "pageTaManagement");
//        else
//            return this.getModelAndView(taInfoForm, "pageTaManagement"); //应该返回错误信息
//    }


//    /**
//     * 恢复助教
//     * 属于TaManagementPage
//     * @param form
//     * @return
//     */
//    @RequestMapping(params = "methodToCall=setTaToLiving")
//    public ModelAndView setTaToLiving(@ModelAttribute("KualiForm") UifFormBase form,
//                                      HttpServletRequest request) {
//        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);
//
//        List<MyTaViewObject> objects = taInfoForm.getAllMyTa();
//
//        List<MyTaViewObject> isOkToChange = new ArrayList<>();
//
//        for (MyTaViewObject per : objects) {
//            if (per.isCheckBox() && per.getStatus().equals(TA_STATUS.PAUSED))
//                isOkToChange.add(per);
//        }
//
//        if (isOkToChange.isEmpty()) {
//            return this.getTaManagementPage(form, request);
//        }
//
//        boolean result = taService.changeStatusBatchByTaIds(
//                taConverter.extractIdsFromMyTaInfo(isOkToChange),
//                TA_STATUS.LIVING
//        );
//
//        if (result)
//            return this.getTaManagementPage(form, request);
//        else {
//            // TODO: 2016/11/12 等待错误信息设计
//            return this.getTaManagementPage(form, request);
//        }
//    }

//    /**
//     * 暂停助教
//     * 属于TaManagementPage
//     * @param form
//     * @return
//     */
//    @RequestMapping(params = "methodToCall=setTaToPause")
//    public ModelAndView setTaToPause(@ModelAttribute("KualiForm") UifFormBase form,
//                                     HttpServletRequest request) {
//        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);
//
//        List<MyTaViewObject> objects = taInfoForm.getAllMyTa();
//
//        List<MyTaViewObject> isOkToChange = new ArrayList<>();
//
//        for (MyTaViewObject per : objects) {
//            if (per.isCheckBox() && per.getStatus().equals(TA_STATUS.LIVING))
//                isOkToChange.add(per);
//        }
//
//        int n = isOkToChange.size();
//
//        if (isOkToChange.isEmpty()) {
//            return this.getTaManagementPage(form, request);
//        }
//
//        n = taConverter.extractIdsFromMyTaInfo(isOkToChange).size();
//
//        boolean result = taService.changeStatusBatchByTaIds(
//                taConverter.extractIdsFromMyTaInfo(isOkToChange),
//                TA_STATUS.PAUSED
//        );
//
//        if (result)
//            return this.getTaManagementPage(form, request);
//        else {
//            // TODO: 2016/11/12 等待错误信息设计
//            return this.getTaManagementPage(form, request);
//        }
//    }

//    /**
//     * 解聘助教
//     * 属于TaManagementPage
//     * @param form
//     * @return
//     */
//    @RequestMapping(params = "methodToCall=setTaToDismiss")
//    public ModelAndView setTaToDismiss(@ModelAttribute("KualiForm") UifFormBase form,
//                                       HttpServletRequest request) {
//
//
//        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);
//        // TODO: 2016/11/12 等待需求
//        return this.getTaManagementPage(form, request);
//    }

//    /**
//     * 测试用方法：
//     * 助教管理页面，输入姓名或学号，查询助教
//     * @param form
//     * @return
//     */
//    @RequestMapping(params = "methodToCall=searchTaByCondition")
//    public ModelAndView searchTaByCondition(@ModelAttribute("KualiForm") UifFormBase form,
//                                       HttpServletRequest request) {
//        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);
//        // TODO: 2016/11/24 要获取前端输入的姓名、学号，所以需要在form中添加对应属性并修改TaManagementPage.xml中375行左右参数名。(现在前端用的是inputField8/9)
//
//
//        // TODO: 2016/11/24 下面为测试用代码，需要添加一个新的存储符合条件ta列表的属性 ，同时修改TaManagementPage.xml中约326行的propertyName
//        // TODO: 2016/11/24 注意：需要在TaInfoForm中为新添加的属性赋初始值(List<xx> xxlist=new arraylist<>();) 否则页面加载时会出错
//        /*
//        List<MyTaViewObject> list= taInfoForm.getConditionTAList();
//        MyTaViewObject newobj=new MyTaViewObject();
//        newobj.setTaName("Zsf");
//        newobj.setTaIdNumber("20135040");
//        list.add(newobj);
//        */
////        taInfoForm.setConditionTAList(list);
//        Map<String, String> conditions = new HashMap<>();
//        //put conditions
//        conditions.put("StudentName", taInfoForm.getStudentName());
//        conditions.put("StudentId", taInfoForm.getStudentNumber());
//        taInfoForm.setConditionTAList(
//                taConverter.studentInfoToMyTaViewObject(
//                        taService.getConditionTaByNameAndId(conditions)
//                )
//        );
//
//        return this.getModelAndView(taInfoForm, "pageTaManagement");
//    }

    /**
     * 助教管理页面，输入姓名或学号查询得到助教列表后，点击助教列表中某一行查看该助教的具体信息
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getSelectedTaInfo")
    public ModelAndView getSelectedTaInfo(@ModelAttribute("KualiForm") UifFormBase form,
                                          HttpServletRequest request) {
        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);

        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(taInfoForm, true);
        int index = params.getSelectedLineIndex();

        taInfoForm.setSelectedTa(taInfoForm.getConditionTAList().get(index));

        return this.getModelAndView(taInfoForm, "pageTaManagement");
    }

//    /**
//     * 在dialog中'确定'对应的后台方法
//     * 首先需要判断当前是否有选中有效目标
//     * 如果没选中，则弹出errDialog
//     * 已选中则将目标ta加入到候选人列表(allApplication),并刷新整个页面
//     * @param form
//     * @param request
//     * @return
//     */
//    @RequestMapping(params = "methodToCall=addSelectedTaApplicant")
//    public ModelAndView addSelectedTaApplicant(@ModelAttribute("KualiForm") UifFormBase form,
//                                  HttpServletRequest request) {
//        TaInfoForm taInfoForm = (TaInfoForm) form;
//        super.baseStart(taInfoForm);
//
//        MyTaViewObject curTa=taInfoForm.getSelectedTa();
//
//        String classid = taInfoForm.getCurClassId();
//
//        boolean result = taService.submitApplicationAssistant(
//                taConverter.TaViewObjectToTaApplication(curTa, classid)
//        );
//
//        if(result){
//            //避免延迟刷新
//            taInfoForm.getAllApplication().add(curTa);
//            taInfoForm.getConditionTAList().remove(curTa);
//            return this.getTaManagementPage(form, request);
//        }
//        else
//            return this.getTaManagementPage(form, request);
//    }

    /**
     * 获取助教考核表(教师给助教评分)
     * 127.0.0.1:8080/tams/portal/ta?methodToCall=getTaAppraisalPage&viewId=TaView
     * @param form
     * @return
     */
    @RequestMapping(params = {"methodToCall=getTaAppraisalPage"}) /*"pageId=pageTaList",*/
    public ModelAndView getTaAppraisalPage(@ModelAttribute("KualiForm") UifFormBase form) {
        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);
        String classId = taInfoForm.getClassIdForDetailPage();
        String taId = taInfoForm.getTaIdForDetailpage();
        if(taInfoForm.getAppraisalDetail()==null) {
            taInfoForm.setAppraisalDetail(taConverter.teachCalendarToAppraisalViewObject(
                    taService.getTeachCalendarByClassId(
                            classId)));
        }

        taInfoForm.setEvaluateDetail(taService.getTaByTaId(taId,classId).getEvaluationDetail());

        return this.getModelAndView(taInfoForm, "pageAppraisalForTeacher");
    }

    /**
     * 获取助教考核表(学生给助教评分)
     * 127.0.0.1:8080/tams/portal/ta?methodToCall=getTaAppraisalForStu&viewId=TaView
     * @param form
     * @return
     */
    @RequestMapping(params = {"methodToCall=getTaAppraisalForStu"}) /*"pageId=pageTaList",*/
    public ModelAndView getTaAppraisalForStu(@ModelAttribute("KualiForm") UifFormBase form) {
        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);
        String classId = taInfoForm.getClassIdForDetailPage();

        if(taInfoForm.getAppraisalDetail()==null) {
            taInfoForm.setAppraisalDetail(taConverter.teachCalendarToAppraisalViewObject(
                    taService.getTeachCalendarByClassId(
                            classId)));
        }

        return this.getModelAndView(taInfoForm, "pageAppraisalForStu");
    }

    /**
     * 助教申请成为优秀助教
     * OS=outstanding
     * 127.0.0.1:8080/tams/portal/ta?methodToCall=getApplyOSTaPage&viewId=TaView
     * @param form
     * @return
     */
    /*
    @RequestMapping(params = "methodToCall=getApplyOSTaPage")
    public ModelAndView getApplyOSTaPage(@ModelAttribute("KualiForm") UifFormBase form) {
        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);

        // TODO: 2016/11/27 原型中不要求教学日历，zsf认为在申请页面加入教学日历的信息方便助教了解自己的工作情况和编写申请理由，如果不需要请删除.xml中的TableCollectionSection


        // TODO: 2016/11/9 在allIssues属性中填入原型中要求的任务类型如作业批改、签到等
        List<IssueViewObject> testIssueList= new ArrayList<IssueViewObject>();
        IssueViewObject issueViewObject=new IssueViewObject();
        issueViewObject.setIssueType("type1");
        issueViewObject.setLikeRate("80%");
        testIssueList.add(issueViewObject);
        taInfoForm.setAllIssues(testIssueList);

        // TODO: 2016/11/27 新的原型要求这里填入教学日历？？所以viewObject和对应list都需要改

        return this.getModelAndView(taInfoForm, "pageApplyOStA");
    }
    */


    /**
     * 页面和form还未对应, 无法显示页面内容，页面的控件无法加载，但页面无错
     * 获取添加任务页面
     * 127.0.0.1:8080/tams/portal/ta?methodToCall=getAddTaskPage&viewId=TaView
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getAddTaskPage")
    public ModelAndView getAddTaskPage(@ModelAttribute("KualiForm") UifFormBase form) {
        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);
        return this.getModelAndView(taInfoForm, "pageAddNewTask");
    }

    /**
     * 获取工作台页面(包含正在进行的活动、我担任助教的课程、我的课程三项)
     * 127.0.0.1:8080/tams/portal/ta?methodToCall=getWorkbenchPage&viewId=TaView
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getWorkbenchPage")
    public ModelAndView getWorkbenchPage(@ModelAttribute("KualiForm") UifFormBase form,HttpServletRequest request) {
        TaInfoForm taInfoForm = (TaInfoForm) form;
        super.baseStart(taInfoForm);
        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        String uId = userSession.getLoggedInUserPrincipalId();

        //是否是系统管理员和学生
        if(userInfoService.isSysAdmin(uId) || userInfoService.isSysAdmin(uId))
            taInfoForm.setBeenStuOrSys(true);
        else
            taInfoForm.setBeenStuOrSys(false);
        //我担任助教的课程
        taInfoForm.setWorkbench(
                taConverter.taCombineWorkbench(
                        taService.getClassInfoByIds(
                                taService.getClassIdsByUid()
                        )
                )
        );

        //我的课程
        taInfoForm.setMyClassViewObjects(taConverter.MyClassViewObject(
                taService.getMycourseByUid(uId))
        );

        //我申请的助教的课程
        taInfoForm.setMyApplicationClass(
                taConverter.taCombineMyApplicationClass(
                        taService.getTaApplicationByClassIds(
                                taService.getApplicationClassIdsByUid(uId)
                        )
                )
        );

        return this.getModelAndView(taInfoForm, "pageWorkbench");
    }

    /**
     * 工作台页面点击'申请交通补贴'，通过此方法跳转到对应某学生的交通补贴页面
     * 127.0.0.1:8080/tams/portal/ta?methodToCall=getGowancePage&viewId=TaView
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getTransAllowancePage")
    public ModelAndView getTransAllowancePage(@ModelAttribute("KualiForm") UifFormBase form) {
        TaInfoForm taInfoForm = (TaInfoForm) form;
        super.baseStart(taInfoForm);
        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(taInfoForm, true);
        int index = params.getSelectedLineIndex();

        WorkBenchViewObject selectedWorkBenchViewObject = taInfoForm.getWorkbench().get(index);
        String classId = selectedWorkBenchViewObject.getClassId();
        String taId = ((User)GlobalVariables.getUserSession().retrieveObject("user")).getCode();
        List<TAMSTaTravelSubsidy> tamsTaTravelSubsidies = taService.getTaTravelByStuIdAndClassId(taId,classId);
        taInfoForm.setCurClassId(classId);
        //显示总的交通补贴
        if(tamsTaTravelSubsidies == null || tamsTaTravelSubsidies.size() == 0) {
            List<TAMSTaTravelSubsidy> nullObject = new ArrayList<>();
            nullObject.add(new TAMSTaTravelSubsidy());
            taInfoForm.setTravelSubsidies(nullObject);
            taInfoForm.setTotalTravelSubsidy("0");
        }
        else {
            taInfoForm.setTravelSubsidies(tamsTaTravelSubsidies);
            taInfoForm.setTotalTravelSubsidy(String.valueOf(tamsTaTravelSubsidies.size() * ONE_TRAVEL_SUBSIDY));
        }
        String tamstaId = taService.getTamsTaIdByStuIdAndClassId(taId,classId);
        if(tamstaId!=null)
            taInfoForm.setTaUniqueId(tamstaId);
        else{
            taInfoForm.setErrMsg("您的助教信息有误，请联系管理员！");
            return this.showDialog("refreshPageViewDialog", true, taInfoForm);
        }
        // TODO: 2016/11/27 (首先判断权限) 老师是不是不可进入此页面？

        // TODO: 2016/11/27 根据user信息，找到相关的交通补贴历史记录，将记录并放置在某个list中，同时修改TransAllowancePage.xml对应位置的objClass和collection

        return this.getModelAndView(taInfoForm, "pageTransAllowance");
    }


    /**
     * 添加往返记录
     * @param form
     * @return
     */

    @RequestMapping(params = "methodToCall=submitTravelRecord")
    public ModelAndView submitTravelRecord(@ModelAttribute("KualiForm") UifFormBase form) {
        TaInfoForm taInfoForm = (TaInfoForm) form;
        super.baseStart(taInfoForm);
        String travelTime = taInfoForm.getTravelTimeD();
        String travelNote = taInfoForm.getTravelNote();
        TAMSTaTravelSubsidy tamsTaTravelSubsidy = new TAMSTaTravelSubsidy();
        tamsTaTravelSubsidy.setTravelTime(travelTime);
        tamsTaTravelSubsidy.setDescription(travelNote);
        tamsTaTravelSubsidy.setTamsTaId(taInfoForm.getTaUniqueId());

        String taId = ((User)GlobalVariables.getUserSession().retrieveObject("user")).getCode();

        String totalTravelSubsidy = taInfoForm.getTotalTravelSubsidy();
        if(taService.isTravelSubsidy(taId, taInfoForm.getCurClassId())) {
            taService.saveTravelSubsidy(tamsTaTravelSubsidy);
            Integer totalTS = Integer.parseInt(totalTravelSubsidy) + ONE_TRAVEL_SUBSIDY;
            taInfoForm.setTotalTravelSubsidy(totalTS.toString());
        }
        else{
            taInfoForm.setErrMsg("课程和助教位于同一个校区！不能申请交通补贴！");
            return this.showDialog("refreshPageViewDialog", true, taInfoForm);
        }
        /*
        if(taService.saveTravelSubsidy(tamsTaTravelSubsidy)){
            if(taService.isTravelSubsidy(taId, taInfoForm.getCurClassId()))
                taService.countTravelSubsidy(taId, taInfoForm.getCurClassId(), "add");
            else {
                taInfoForm.setErrMsg("课程和助教位于同一个校区！不能申请交通补贴！");
                return this.showDialog("refreshPageViewDialog", true, taInfoForm);
            }
        }
        */

        List<TAMSTaTravelSubsidy> tamsTaTravelSubsidies = taService.getTaTravelByStuIdAndClassId(taId,taInfoForm.getCurClassId());
        taInfoForm.setTravelSubsidies(tamsTaTravelSubsidies);
        return this.getModelAndView(taInfoForm, "pageTransAllowance");
    }


    /**
     * 提交最终往返记录
     */
    @RequestMapping(params = "methodToCall=submitTotalTravelRecord")
    public ModelAndView submitTotalTravelRecord(@ModelAttribute("KualiForm") UifFormBase form) {
        TaInfoForm taInfoForm = (TaInfoForm) form;
        super.baseStart(taInfoForm);

        //直接全部提交
        List<TAMSTaTravelSubsidy> tamsTaTravelSubsidies = taInfoForm.getTravelSubsidies();

        String taId = ((User)GlobalVariables.getUserSession().retrieveObject("user")).getCode();
        if(tamsTaTravelSubsidies == null || tamsTaTravelSubsidies.size() == 0){
            taInfoForm.setErrMsg("请先添加往返记录再提交交通补助申请！");
            return this.showDialog("refreshPageViewDialog", true, taInfoForm);
        }
        else {
            Integer totalTravelSubsidy = tamsTaTravelSubsidies.size() * ONE_TRAVEL_SUBSIDY;
            taService.countTravelSubsidy(taId, taInfoForm.getCurClassId(), totalTravelSubsidy.toString());
        }

        return this.getModelAndView(taInfoForm, "pageTransAllowance");
    }

    /**
     * 删除往返记录
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=deleteTransAllowance")
    public ModelAndView deleteTransAllowance(@ModelAttribute("KualiForm") UifFormBase form) {
        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);
        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(taInfoForm, true);
        int index = params.getSelectedLineIndex();
        TAMSTaTravelSubsidy tamsTaTravelSubsidy = taInfoForm.getTravelSubsidies().get(index);

        String totalTravelSubsidy = taInfoForm.getTotalTravelSubsidy();
        taService.deleteTravelSubsidyByEntity(tamsTaTravelSubsidy);
        Integer totalTS = Integer.parseInt(totalTravelSubsidy) - ONE_TRAVEL_SUBSIDY;
        taInfoForm.setTotalTravelSubsidy(totalTS.toString());

        taInfoForm.getTravelSubsidies().remove(index);
        return this.getModelAndView(taInfoForm, "pageTransAllowance");

    }

    /**
     * 弹出添加交通补贴的dialog
     * @param form
     * @return
     */

    @RequestMapping(params = "methodToCall=showAddTransAllowanceDialog")
    public ModelAndView showAddTransAllowanceDialog(@ModelAttribute("KualiForm") UifFormBase form) {
        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);
        taInfoForm.setTravelTimeD(null);
        taInfoForm.setTravelNote(null);
        return this.showDialog("AddTransAllowanceDialog",true,taInfoForm);
    }


    /**
     * 进入助教详情
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getTaDetailPage")
    public ModelAndView getTaDetailPage(@ModelAttribute("KualiForm") UifFormBase form) {
        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);
        try {
//            CollectionControllerServiceImpl.CollectionActionParameters params =
//                    new CollectionControllerServiceImpl.CollectionActionParameters(taInfoForm, true);
//            int index = params.getSelectedLineIndex();
            int index=Integer.parseInt(taInfoForm.getIndexTaListPage());
            String classid = taInfoForm.getAllTaInfo().get(index).getClassid();
            String taid = taInfoForm.getAllTaInfo().get(index).getTaId();
            taInfoForm.setSelectedTaInfo(taInfoForm.getAllTaInfo().get(index));
            taInfoForm.setClassIdForDetailPage(classid);
            taInfoForm.setTaIdForDetailpage(taid);
            taInfoForm.setTaDeApplyReason(taInfoForm.getAllTaInfo().get(index).getApplicationReason());
        }catch (Exception e){

        }
        return this.getModelAndView(taInfoForm, "pageTaDetail");
    }


    @RequestMapping(params =  {"methodToCall=selectCurSession"})
    public ModelAndView selectCurSession(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);
        super.baseStart(taInfoForm);
        utSessionDao.setCurrentSession(utSessionDao.getUTSessionById(Integer.parseInt(taInfoForm.getSessionTermFinder())));
        request.getParameterMap().get("pageId");
        return this.getTaListPage(form,request);
    }

    @RequestMapping(params =  {"methodToCall=showAppriseDialog"})
    public ModelAndView showRevocationDialog(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);
        super.baseStart(taInfoForm);
        List<TaInfoViewObject> taList = taInfoForm.getAllTaInfo();

        List<TaInfoViewObject> checkedList = new ArrayList<>();
        for(TaInfoViewObject per : taList) {
            if(per.isCheckBox())
                checkedList.add(per);
        }

        if(checkedList.size()==0){
            taInfoForm.setErrMsg("请选择需要评优的助教！");
            return this.showDialog("refreshPageViewDialog",true,taInfoForm);
        }

        return this.showDialog("confirmAppraiseDialog" ,true,taInfoForm);
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
    @RequestMapping(params = {"methodToCall=exportTaListExcel"})
    public ModelAndView exportTaListExcel(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                                             HttpServletResponse response) {
        TaInfoForm infoForm = (TaInfoForm) form;
        super.baseStart(infoForm);

        if (infoForm.getAllTaInfo() == null || infoForm.getAllTaInfo().size() == 1) { //size=1是因为会设置至少一个空object让表格不会消失
            if(infoForm.getAllTaInfo().get(0).getId() == null) {
                infoForm.setErrMsg("列表为空！");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            }
        }

        List<TaInfoViewObject> taList = infoForm.getAllTaInfo();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = "助教列表" + "-" + GlobalVariables.getUserSession().getLoggedInUserPrincipalId() + "-" + sdf.format(new Date()) + ".xls";

        try {
            String filePath = excelService.printTaListExcel(taList, "exportfolder", fileName, "2003");
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
    @RequestMapping(params = {"methodToCall=exportTaListPDF"})
    public ModelAndView exportClassListPDF(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                                           HttpServletResponse response) {
        TaInfoForm infoForm = (TaInfoForm) form;
        super.baseStart(infoForm);

        if (infoForm.getAllTaInfo() == null || infoForm.getAllTaInfo().size() == 1) { //size=1是因为会设置至少一个空object让表格不会消失
            if(infoForm.getAllTaInfo().get(0).getId() == null) {
                infoForm.setErrMsg("列表为空！");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            }
        }

        List<TaInfoViewObject> taList = infoForm.getAllTaInfo();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = "助教列表" + "-" + GlobalVariables.getUserSession().getLoggedInUserPrincipalId() + "-" + sdf.format(new Date());
        String filePath = "";
        try {
            String[] header = {"姓名", "学号", "学历", "课程名称", "课程编号", "教学班", "教师", "教师考核", "学生考核", "成绩"};
            List<String[]> Content = new ArrayList(taList.size());
            for(TaInfoViewObject ta : taList) {
                String name = ta.getTaName() == null ? "" : ta.getTaName();
                String stuNumber = ta.getTaIDNumber() == null ? "" : ta.getTaIDNumber();
                String masterMajor = ta.getTaMajorName() == null ? "" : ta.getTaMajorName();
                String courseName = ta.getCourseName() == null ? "" : ta.getCourseName();
                String courseCode = ta.getCourseCode() == null ? "" : ta.getCourseCode();
                String clazzCode = ta.getClassNumber() == null ? "" : ta.getClassNumber();
                String instructor = ta.getInstructorName() == null ? "" : ta.getInstructorName();
                String instructorAppraise = ta.getTeacherAppraise() == null ? "" : ta.getTeacherAppraise();
                String stuAppraise = ta.getStuAppraise() == null ? "" : ta.getStuAppraise();
                String score = ta.getScore() == null ? "" : ta.getScore();
                String[] content = {
                        name, stuNumber, masterMajor,
                        courseName, courseCode, clazzCode,
                        instructor, instructorAppraise, stuAppraise,
                        score
                };
                Content.add(content);
            }

            filePath = PDFService.printNormalTable("助教信息列表", header, Content, fileName);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            infoForm.setErrMsg("系统导出PDF文件错误！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        String baseUrl = CoreApiServiceLocator.getKualiConfigurationService()
                .getPropertyValueAsString(KRADConstants.ConfigParameters.APPLICATION_URL);

        return this.performRedirect(infoForm, baseUrl + "/" + filePath);
    }

    @Override
    protected UifFormBase createInitialForm() {

        return new TaInfoForm();

    }

    //显示优秀助教申请表
    @RequestMapping(params =  {"methodToCall=getApplyOSTaPage"})
    public ModelAndView getApplyOSTaPage(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        TaInfoForm taInfoForm = (TaInfoForm) form;
        super.baseStart(taInfoForm);
        String classId = taInfoForm.getSelectedTaInfo().getClassid();
        String stuId = taInfoForm.getSelectedTaInfo().getTaId();
        taInfoForm.setApplyOSReason(taService.getOSReason(stuId, classId));
        return this.getModelAndView(taInfoForm, "pageApplyOStA");
    }
}
