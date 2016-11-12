package cn.edu.cqu.ngtl.controller.tamanagement;

import cn.edu.cqu.ngtl.dataobject.enums.TA_STATUS;
import cn.edu.cqu.ngtl.form.tamanagement.TaInfoForm;
import cn.edu.cqu.ngtl.service.riceservice.ITAConverter;
import cn.edu.cqu.ngtl.service.taservice.ITAService;
import cn.edu.cqu.ngtl.viewobject.tainfo.TaInfoViewObject;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
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

        boolean result = this.changeStatus(checkedList, TA_STATUS.LIVING);

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

        boolean result = this.changeStatus(taList, TA_STATUS.PAUSED);

        if(result)
            return this.getTaListPage(form, request);
        else
            return this.getTaListPage(form, request); //应该返回错误信息
    }

    /**
     * 修改状态的通用函数
     * @param status
     * @return
     */
    public boolean changeStatus(List<TaInfoViewObject> checkedlist, String status) {

        boolean result;

        result = taService.recoverBatchByIds(
                taConverter.extractIdsFromTaInfo(checkedlist),
                status
        );

        return result;
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
