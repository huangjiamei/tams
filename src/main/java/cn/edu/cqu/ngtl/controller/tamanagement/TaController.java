package cn.edu.cqu.ngtl.controller.tamanagement;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.controller.BaseController;
import cn.edu.cqu.ngtl.dataobject.enums.TA_STATUS;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaTravelSubsidy;
import cn.edu.cqu.ngtl.form.tamanagement.TaInfoForm;
import cn.edu.cqu.ngtl.service.classservice.IClassInfoService;
import cn.edu.cqu.ngtl.service.riceservice.ITAConverter;
import cn.edu.cqu.ngtl.service.taservice.ITAService;
import cn.edu.cqu.ngtl.viewobject.tainfo.IssueViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.MyTaViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.TaInfoViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.WorkBenchViewObject;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by tangjing on 16-10-19.
 * 助教信息查看的相关view及function
 */
@Controller
@RequestMapping("/ta")
public class TaController extends BaseController {

    @Autowired
    private IClassInfoService classInfoService;

    @Autowired
    private ITAConverter taConverter;

    @Autowired
    private ITAService taService;

    @RequestMapping(params = "methodToCall=logout")
    public ModelAndView logout(@ModelAttribute("KualiForm") UifFormBase form) throws Exception {
        String redirctURL = ConfigContext.getCurrentContextConfig().getProperty(KRADConstants.APPLICATION_URL_KEY) + "/portal/home?methodToCall=logout&viewId=PortalView";
        return this.performRedirect(form, redirctURL);
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
        taInfoForm.setAllTaInfo(taConverter.taCombineDetailInfo(
                taService.getAllTaFilteredByUid(uId)
        ));

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

    /**
     * 助教撤销优秀
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=revocationOutstanding")
    public ModelAndView revocationOutstanding(@ModelAttribute("KualiForm") UifFormBase form,
                                            HttpServletRequest request) {
        TaInfoForm taInfoForm = (TaInfoForm) form;
        super.baseStart(taInfoForm);

        List<TaInfoViewObject> taList = taInfoForm.getAllTaInfo();

        //遍历所有list，找到选中的行
        List<TaInfoViewObject> checkedList = new ArrayList<>();
        for(TaInfoViewObject per : taList) {
            if(per.isCheckBox())
                checkedList.add(per);
        }

        String uid = GlobalVariables.getUserSession().getPrincipalId();
        boolean result = taService.revocationOutstanding(
                taConverter.extractIdsFromTaInfo(checkedList),
                uid
        );
        if(result)
            return this.getTaListPage(form, request);
        else
            return this.getTaListPage(form, request); //应该返回错误信息
    }

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

        //遍历所有list，找到选中的行
        List<TaInfoViewObject> checkedList = new ArrayList<>();
        for(TaInfoViewObject per : taList) {
            if(per.isCheckBox())
                checkedList.add(per);
        }

        String uid = GlobalVariables.getUserSession().getPrincipalId();
        boolean result = taService.appraiseOutstanding(
                taConverter.extractIdsFromTaInfo(checkedList),
                uid
        );
        if(result)
            return this.getTaListPage(form, request);
        else
            return this.getTaListPage(form, request); //应该返回错误信息
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
        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);

        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        String uId = userSession.getLoggedInUserPrincipalId();
        taInfoForm.setAllMyTa(taConverter.myTaCombinePayDay(
                taService.getAllTaFilteredByUid(uId)
        ));


        taInfoForm.setAllApplication(taConverter.applicationToViewObject(
                taService.getAllApplicationFilterByUid(uId)
        ));

        return this.getModelAndView(taInfoForm, "pageTaManagement");
    }

    /**
     * 聘请助教
     * @param form
     * @param request
     * @return
     */
    @RequestMapping(params = "methodToCall=employ")
    public ModelAndView employ(@ModelAttribute("KualiForm") UifFormBase form,
                                            HttpServletRequest request) {
        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);

        List<MyTaViewObject> applicationList = taInfoForm.getAllApplication();

        //遍历所有list，找到选中的行
        List<MyTaViewObject> checkedList = new ArrayList<>();
        for(MyTaViewObject per : applicationList) {
            if(per.isCheckBox())
                checkedList.add(per);
        }

        boolean result = taService.employBatchByStuIdsWithClassId(
                taConverter.extractIdsFromApplication(checkedList)
        );
        for(MyTaViewObject needToAdd : checkedList){
            needToAdd.setCheckBox(false);
            taInfoForm.getAllMyTa().add(needToAdd);
            taInfoForm.getAllApplication().remove(needToAdd);
        }


        if(result)
            return this.getModelAndView(taInfoForm, "pageTaManagement");
        else
            return this.getModelAndView(taInfoForm, "pageTaManagement"); //应该返回错误信息
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
        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);

        List<MyTaViewObject> objects = taInfoForm.getAllMyTa();

        List<MyTaViewObject> isOkToChange = new ArrayList<>();

        for (MyTaViewObject per : objects) {
            if (per.isCheckBox() && per.getStatus().equals(TA_STATUS.PAUSED))
                isOkToChange.add(per);
        }

        if (isOkToChange.isEmpty()) {
            return this.getTaManagementPage(form, request);
        }

        boolean result = taService.changeStatusBatchByTaIds(
                taConverter.extractIdsFromMyTaInfo(isOkToChange),
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
        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);

        List<MyTaViewObject> objects = taInfoForm.getAllMyTa();

        List<MyTaViewObject> isOkToChange = new ArrayList<>();

        for (MyTaViewObject per : objects) {
            if (per.isCheckBox() && per.getStatus().equals(TA_STATUS.LIVING))
                isOkToChange.add(per);
        }

        int n = isOkToChange.size();

        if (isOkToChange.isEmpty()) {
            return this.getTaManagementPage(form, request);
        }

        n = taConverter.extractIdsFromMyTaInfo(isOkToChange).size();

        boolean result = taService.changeStatusBatchByTaIds(
                taConverter.extractIdsFromMyTaInfo(isOkToChange),
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


        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);
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
        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);
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
        conditions.put("StudentName", taInfoForm.getStudentName());
        conditions.put("StudentId", taInfoForm.getStudentNumber());
        taInfoForm.setConditionTAList(
                taConverter.studentInfoToMyTaViewObject(
                        taService.getConditionTaByNameAndId(conditions)
                )
        );

        return this.getModelAndView(taInfoForm, "pageTaManagement");
    }

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
        TaInfoForm taInfoForm = (TaInfoForm) form;
        super.baseStart(taInfoForm);

        MyTaViewObject curTa=taInfoForm.getSelectedTa();

        String classid = "301369";

        boolean result = taService.submitApplicationAssistant(
                taConverter.TaViewObjectToTaApplication(curTa, classid)
        );

        if(result){
            //避免延迟刷新
            taInfoForm.getAllApplication().add(curTa);
            taInfoForm.getConditionTAList().remove(curTa);
            return this.getTaManagementPage(form, request);
        }
        else
            return this.getTaManagementPage(form, request);
    }

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
    public ModelAndView getWorkbenchPage(@ModelAttribute("KualiForm") UifFormBase form) {
        TaInfoForm taInfoForm = (TaInfoForm) form;
        super.baseStart(taInfoForm);
        //我担任助教的课程
        taInfoForm.setWorkbench(
                taConverter.taCombineWorkbench(
                        taService.getClassInfoByIds(
                                taService.getClassIdsByUid()
                        )
                )
        );
        return this.getModelAndView(taInfoForm, "pageWorkbench");
    }

    /**
     * 工作台页面点击'申请交通补贴'，通过此方法跳转到对应某学生的交通补贴页面
     * 127.0.0.1:8080/tams/portal/ta?methodToCall=getTransAllowancePage&viewId=TaView
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getTransAllowancePage")
    public ModelAndView getTransAllowancePage(@ModelAttribute("KualiForm") UifFormBase form) {
        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);

        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(taInfoForm, true);
        int index = params.getSelectedLineIndex();

        WorkBenchViewObject selectedWorkBenchViewObject = taInfoForm.getWorkbench().get(index);
        String classId = selectedWorkBenchViewObject.getClassId();
        String taId = ((User)GlobalVariables.getUserSession().retrieveObject("user")).getCode();
        List<TAMSTaTravelSubsidy> tamsTaTravelSubsidies = taService.getTaTravelByStuIdAndClassId(taId,classId);
        taInfoForm.setCurClassId(classId);
        taInfoForm.setTravelSubsidies(tamsTaTravelSubsidies);
        taInfoForm.setTaUniqueId(tamsTaTravelSubsidies.get(0).getTamsTaId());
        // TODO: 2016/11/27 (首先判断权限) 老师是不是不可进入此页面？

        // TODO: 2016/11/27 根据user信息，找到相关的交通补贴历史记录，将记录并放置在某个list中，同时修改TransAllowancePage.xml对应位置的objClass和collection

        return this.getModelAndView(taInfoForm, "pageTransAllowance");
    }



    @RequestMapping(params = "methodToCall=submitTravelRecord")
    public ModelAndView submitTravelRecord(@ModelAttribute("KualiForm") UifFormBase form) {
        TaInfoForm taInfoForm = (TaInfoForm) form; super.baseStart(taInfoForm);
        String travelTime = taInfoForm.getTravelTime();
        String travelNote = taInfoForm.getTravelNote();
        TAMSTaTravelSubsidy tamsTaTravelSubsidy = new TAMSTaTravelSubsidy();
        tamsTaTravelSubsidy.setTravelTime(travelTime);
        tamsTaTravelSubsidy.setDescription(travelNote);
        tamsTaTravelSubsidy.setTamsTaId(taInfoForm.getTaUniqueId());
        taService.saveTravelSubsidy(tamsTaTravelSubsidy);
        String taId = ((User)GlobalVariables.getUserSession().retrieveObject("user")).getCode();
        List<TAMSTaTravelSubsidy> tamsTaTravelSubsidies = taService.getTaTravelByStuIdAndClassId(taId,taInfoForm.getCurClassId());
        taInfoForm.setTravelSubsidies(tamsTaTravelSubsidies);
        return this.getModelAndView(taInfoForm, "pageTransAllowance");
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
            CollectionControllerServiceImpl.CollectionActionParameters params =
                    new CollectionControllerServiceImpl.CollectionActionParameters(taInfoForm, true);
            int index = params.getSelectedLineIndex();
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

    @Override
    protected UifFormBase createInitialForm() {

        return new TaInfoForm();

    }
}
