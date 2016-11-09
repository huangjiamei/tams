package cn.edu.cqu.ngtl.controller.tamanagement;

import cn.edu.cqu.ngtl.form.tamanagement.TaInfoForm;
import cn.edu.cqu.ngtl.service.riceservice.ITAConverter;
import cn.edu.cqu.ngtl.service.taservice.ITAService;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

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
