package cn.edu.cqu.ngtl.controller.tamanagement;

import cn.edu.cqu.ngtl.dataobject.enums.TA_STATUS;
import cn.edu.cqu.ngtl.form.tamanagement.TaInfoForm;
import cn.edu.cqu.ngtl.service.riceservice.ITAConverter;
import cn.edu.cqu.ngtl.service.taservice.ITAService;
import cn.edu.cqu.ngtl.viewobject.tainfo.MyTaViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.TaInfoViewObject;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krad.web.service.impl.CollectionControllerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangjing on 16-10-19.
 * 助教信息查看的相关view及function
 */
@Controller
@RequestMapping("/ta")
public class TaController extends UifControllerBase {

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
        TaInfoForm taInfoForm = (TaInfoForm) form;

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
        TaInfoForm taInfoForm = (TaInfoForm) form;

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
        TaInfoForm taInfoForm = (TaInfoForm) form;

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

    //我的助教（教师用户看到的）界面
    /**
     * 获取助教管理页面(包含我的助教列表+申请助教列表)
     * 127.0.0.1:8080/tams/portal/ta?methodToCall=getTaManagementPage&viewId=TaView
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getTaManagementPage")
    public ModelAndView getTaManagementPage(@ModelAttribute("KualiForm") UifFormBase form,
                                            HttpServletRequest request) {
        TaInfoForm taInfoForm = (TaInfoForm) form;

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
     */

    @RequestMapping(params = "methodToCall=employ")
    public ModelAndView employ(@ModelAttribute("KualiForm") UifFormBase form,
                                            HttpServletRequest request) {
        TaInfoForm taInfoForm = (TaInfoForm) form;

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

        if(result)
            return this.getTaListPage(form, request);
        else
            return this.getTaListPage(form, request); //应该返回错误信息
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
        TaInfoForm taInfoForm = (TaInfoForm) form;

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
        TaInfoForm taInfoForm = (TaInfoForm) form;

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
        TaInfoForm taInfoForm = (TaInfoForm) form;
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
        TaInfoForm taInfoForm = (TaInfoForm) form;
        // TODO: 2016/11/24 要获取前端输入的姓名、学号，所以需要在form中添加对应属性并修改TaManagementPage.xml中375行左右参数名。(现在前端用的是inputField8/9)


        // TODO: 2016/11/24 下面为测试用代码，需要添加一个新的存储符合条件ta列表的属性 ，同时修改TaManagementPage.xml中约326行的propertyName
        // TODO: 2016/11/24 注意：需要在TaInfoForm中为新添加的属性赋初始值(List<xx> xxlist=new arraylist<>();) 否则页面加载时会出错
        List<MyTaViewObject> list=taInfoForm.getAllApplication();
        MyTaViewObject newobj=new MyTaViewObject();
        newobj.setTaName("Zsf");
        newobj.setTaIdNumber("20135040");
        list.add(newobj);

        taInfoForm.setAllApplication(list);

        return this.getModelAndView(taInfoForm, "pageTaManagement");
    }

    /**
     * 助教管理页面，输入姓名或学号查询得到助教列表后，点击助教列表中某一行查看该助教的具体信息
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getTaInfo")
    public ModelAndView getTaInfo(@ModelAttribute("KualiForm") UifFormBase form,
                                            HttpServletRequest request) {
        TaInfoForm taInfoForm = (TaInfoForm) form;

        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(taInfoForm, true);
        int index = params.getSelectedLineIndex();

        taInfoForm.setSelectedTa(taInfoForm.getAllApplication().get(index));

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
        MyTaViewObject curTa=taInfoForm.getSelectedTa();


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
        TaInfoForm taInfoForm = (TaInfoForm) form;

        // TODO: 2016/11/9 在allIssues属性中填入原型中要求的任务类型如作业批改、签到等

        return this.getModelAndView(taInfoForm, "pageAppraisalForTeacher");
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
        TaInfoForm taInfoForm = (TaInfoForm) form;
        return this.getModelAndView(taInfoForm, "pageAddNewTask");
    }

    @Override
    protected UifFormBase createInitialForm() {

        return new TaInfoForm();

    }
}
