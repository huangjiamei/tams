package cn.edu.cqu.ngtl.controller.adminmanagement;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.controller.BaseController;
import cn.edu.cqu.ngtl.dao.krim.KRIM_ROLE_T_Dao;
import cn.edu.cqu.ngtl.dao.krim.impl.*;
import cn.edu.cqu.ngtl.dao.tams.TAMSCourseManagerDao;
import cn.edu.cqu.ngtl.dao.tams.TAMSTaTravelSubsidyDao;
import cn.edu.cqu.ngtl.dao.tams.impl.TAMSTaCategoryDaoJpa;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dao.ut.impl.UTInstructorDaoJpa;
import cn.edu.cqu.ngtl.dataobject.cm.CMCourseClassification;
import cn.edu.cqu.ngtl.dataobject.krim.*;
import cn.edu.cqu.ngtl.dataobject.tams.*;
import cn.edu.cqu.ngtl.dataobject.ut.UTInstructor;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.form.adminmanagement.AdminInfoForm;
import cn.edu.cqu.ngtl.service.adminservice.IAdminService;
import cn.edu.cqu.ngtl.service.common.ExcelService;
import cn.edu.cqu.ngtl.service.common.SyncInfoService;
import cn.edu.cqu.ngtl.service.common.impl.IdstarIdentityManagerServiceImpl;
import cn.edu.cqu.ngtl.service.riceservice.IAdminConverter;
import cn.edu.cqu.ngtl.service.riceservice.ITAConverter;
import cn.edu.cqu.ngtl.service.taservice.ITAService;
import cn.edu.cqu.ngtl.service.userservice.IUserInfoService;
import cn.edu.cqu.ngtl.service.userservice.impl.UserInfoServiceImpl;
import cn.edu.cqu.ngtl.viewobject.adminInfo.*;
import com.google.gson.Gson;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krad.web.service.impl.CollectionControllerServiceImpl;
import org.kuali.rice.krad.web.service.impl.CollectionControllerServiceImpl.CollectionActionParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.kuali.rice.krad.util.GlobalVariables.getUserSession;

/**
 * Created by awake on 2016-10-21.
 */
@Controller
@RequestMapping("/admin")
public class adminController extends BaseController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private ExcelService excelService;
    @Autowired
    private TAMSCourseManagerDao tamsCourseManagerDao;

    @Autowired
    private TAMSTaTravelSubsidyDao tamsTaTravelSubsidyDao;

    @Autowired
    private ITAConverter taConverter;

    @Autowired
    private IAdminConverter adminConverter;

    @Autowired
    private SyncInfoService syncInfoService;

    @Autowired
    private ITAService taService;

    @Autowired
    private UTSessionDao utSessionDao;

    @Autowired
    private UserInfoServiceImpl userInfoService;
    @Autowired
    private IUserInfoService iUserInfoService;


    @RequestMapping(params = "methodToCall=logout")
    public ModelAndView logout(@ModelAttribute("KualiForm") UifFormBase form,HttpServletRequest request) throws Exception {
        UserSession userSession = getUserSession();
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
     * 127.0.0.1:8080/tams/portal/admin?methodToCall=getConsolePage&viewId=AdminView
     *
     * @param form
     * @return
     */
    /**
     * 权限控制Start
     **/
    @RequestMapping(params = "methodToCall=getConsolePage")
    public ModelAndView getConsolePage(@ModelAttribute("KualiForm") UifFormBase form) {
//        if(new cn.edu.cqu.ngtl.service.userservice.impl.UserInfoServiceImpl().hasPermission((User) GlobalVariables.getUserSession().retrieveObject("user"),"ViewConsolePage")) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
//            return this.getModelAndView(infoForm, "pageConsole");
//        }
//        StringBuilder redirectUrl = new StringBuilder(ConfigContext.getCurrentContextConfig().getProperty(KRADConstants.APPLICATION_URL_KEY));
//        return this.performRedirect(form, redirectUrl.toString());

        return this.getModelAndView(infoForm, "pageConsole");
    }
    /**权限控制End**/

    /**
     * http://127.0.0.1:8080/tams/portal/admin?methodToCall=getRoleManagerPage&viewId=AdminView
     *
     * @param form
     * @param request
     * @param response
     * @return 角色管理页面
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=getRoleManagerPage")
    public ModelAndView getRoleManagerPage(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        infoForm.setRMPkrimRoleTs(new KRIM_ROLE_T_DaoJpa().getAllKrimRoleTs());

        return this.getModelAndView(infoForm, "pageRoleManager");
    }


    /**
     * http://127.0.0.1:8080/tams/portal/admin?methodToCall=getUserRoleManagerPage&viewId=AdminView
     *
     * @param form
     * @param request
     * @param response
     * @return 用户管理页面
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=getUserRoleManagerPage")
    public ModelAndView getUserRoleManagerPage(@ModelAttribute("KualiForm") UifFormBase form,
                                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        infoForm.setURMutInstructors(new UTInstructorDaoJpa().getAllInstructors());

        return this.getModelAndView(infoForm, "pageUserRoleManager");
    }

    /**
     * http://127.0.0.1:8080/tams/portal/admin?methodToCall=getPermissionManagementPage&viewId=AdminView
     *
     * @param form
     * @param request
     * @param response
     * @return 权限页面
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=getPermissionManagementPage")
    public ModelAndView getPermissionManagementPage(@ModelAttribute("KualiForm") UifFormBase form,
                                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        List<KRIM_PERM_T> krimPermTs = new ArrayList<KRIM_PERM_T>(new KRIM_PERM_T_DaoJpa().getAllPermissions());
        infoForm.setRMPkrimPermTs(krimPermTs);

        return this.getModelAndView(infoForm, "pagePermissionManagement");
    }

    /**
     * http://127.0.0.1:8080/tams/portal/admin?methodToCall=getTimeSetPage&viewId=AdminView
     * 设置时间页面
     *
     * @param form
     * @return 用户管理页面
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=getTimeSetPage")
    public ModelAndView getTimeSetPage(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        infoForm.setTimeSettingsList(
                adminService.getallTimeSettings()
        );

        return this.getModelAndView(infoForm, "pageTimeSet");
    }

    /**
     * http://127.0.0.1:8080/tams/portal/admin?methodToCall=getWorkFlowCategoryPage&viewId=AdminView
     *
     * @param form
     * @return 工作流类型管理页面
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=getWorkFlowCategoryPage")
    public ModelAndView getWorkFlowCategoryPage(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);
        adminInfoForm.setGetWorkFlowStatus("1");
        adminInfoForm.setTamsWorkflowStatuses(
                adminService.getWorkFlowCategory()
        );
        return this.getModelAndView(adminInfoForm, "pageWorkFlowCategory");
    }

    /**
     * http://127.0.0.1:8080/tams/portal/admin?methodToCall=getSyncInfoPage&viewId=AdminView
     *
     * @param form
     * @return 同步信息页面
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=getSyncInfoPage")
    public ModelAndView getSyncInfoPage(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);
        adminInfoForm.setSystemDbName("jwdb");
        adminInfoForm.setSystemHostIP("202.202.0.123");
        adminInfoForm.setSystemHostPort("1521");
        adminInfoForm.setSystemDbUserName("cetman");
        adminInfoForm.setSystemDbPassword("cet_manager");

        return this.getModelAndView(adminInfoForm, "pageSyncInfo");
    }

    /**
     * http://127.0.0.1:8080/tams/portal/admin?methodToCall=getTimeCategoryPage&viewId=AdminView
     *
     * @param form
     * @return 时间类别管理页面
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=getTimeCategoryPage")
    public ModelAndView getTimeCategoryPage(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);

        adminInfoForm.setAllTimeTypes(adminService.getAllTimeCategory());
        return this.getModelAndView(adminInfoForm, "pageTimeCategory");
    }

    /**
     * 修改时间类别，需要获取当前所要修改的对象
     */
    @RequestMapping(params = "methodToCall=modifyTimeCategory")
    public ModelAndView modifyTimeCategory(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        CollectionControllerServiceImpl.CollectionActionParameters parameters =
                new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
        int index = parameters.getSelectedLineIndex();
        infoForm.setTimeSettingType(infoForm.getAllTimeTypes().get(index));
        //用于判断是修改还是添加
        infoForm.setIndex(index);
        return this.showDialog("editTimeCategoryDialog", true, infoForm);
    }

    /**
     * 保存时间类别
     */
    @RequestMapping(params = "methodToCall=saveTimeCategoryPage")
    public ModelAndView saveTimeCategoryPage(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        if(infoForm.getTimeSettingType().getTypeName()==null){
            infoForm.setErrMsg("请设置时间类别名称！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        TAMSTimeSettingType timeSettingType = infoForm.getTimeSettingType();
        adminService.saveTimeCategory(timeSettingType);
        return this.getTimeCategoryPage(infoForm);
    }

    /**
     * 删除时间类别
     *
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=deleteTimeCategoryPage")
    public ModelAndView deleteTimeCategoryPage(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        //获取某一行
        CollectionControllerServiceImpl.CollectionActionParameters parameters =
                new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
        int index = parameters.getSelectedLineIndex();
        TAMSTimeSettingType timeSettingType = infoForm.getAllTimeTypes().get(index);
        adminService.deleteTimeCategory(timeSettingType);
        return this.getTimeCategoryPage(infoForm);
    }

    /**
     * 工作流类型过滤
     */
    @RequestMapping(params = "methodToCall=searchWorkFlowCategory")
    public ModelAndView searchWorkFlowCategory(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        String workFlowFunction = infoForm.getGetWorkFlowStatus();
        infoForm.setTamsWorkflowStatuses(
                adminService.getWorkFlowCategoryByCondition(workFlowFunction)
        );
        return this.getModelAndView(infoForm, "pageWorkFlowCategory");
    }

    /**
     * 新添工作流类型，无需获取当前所要修改的对象，此处前台直接ref bean调到addWorkFlowDialog对话框
     */
    /*
    @RequestMapping(params = "methodToCall=addWorkFlowCategory")
    public ModelAndView updateWorkFlowCategory(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        infoForm.setWorkflowstatus("");
        infoForm.setWorkfloworder("");
        return this.showDialog("addWorkFlowCaDialog", true, infoForm);
        //put conditions
        Map<String, String> conditions = new HashMap<>();
        conditions.put("workflowstatus", infoForm.getWorkflowstatus());
        conditions.put("workfloworder", infoForm.getWorkfloworder());
        conditions.put("workflowfunction", infoForm.getGetWorkFlowStatus());
        //选中某一行
        CollectionControllerServiceImpl.CollectionActionParameters parameters =
                new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
        int index = parameters.getSelectedLineIndex();
        if(infoForm.getTamsWorkflowStatuses().get(index) != null) {
            String status = infoForm.getTamsWorkflowStatuses().get(index).getWorkflowStatus();
            String order = infoForm.getTamsWorkflowStatuses().get(index).getOrder().toString();
            adminService.modifyWorkFlowCategory(conditions, status, order);
        }
        else
            adminService.addWorkFlowCategory(conditions);
        return this.getModelAndView(infoForm, "pageWorkFlowCategory");
    }
    */

    /**
     * 修改工作流类型，需要获取当前所要修改的对象
     */
    @RequestMapping(params = "methodToCall=modifyWorkFlowCategory")
    public ModelAndView modifyWorkFlowCategory(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        CollectionControllerServiceImpl.CollectionActionParameters parameters =
                new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
        int index = parameters.getSelectedLineIndex();
        String status = infoForm.getTamsWorkflowStatuses().get(index).getWorkflowStatus();
        String order = infoForm.getTamsWorkflowStatuses().get(index).getOrder().toString();
        infoForm.setWorkflowstatus(status);
        infoForm.setWorkfloworder(order);
        //用于判断是修改还是添加
        infoForm.setIndex(index);
        return this.showDialog("addWorkFlowCaDialog", true, infoForm);
    }

    /**
     * 添加或者修改工作流类型对话框的保存方法
     */
    @RequestMapping(params = "methodToCall=saveWorkFlowCategory")
    public ModelAndView saveWorkFlowCategory(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        if(infoForm.getWorkfloworder()==null){
            infoForm.setErrMsg("请设置工作流顺序！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }

        if(infoForm.getWorkflowstatus()==null){
            infoForm.setErrMsg("请设置工作流状态！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }

        TAMSWorkflowStatus tamsWorkflowStatus = new TAMSWorkflowStatus();
        tamsWorkflowStatus.setWorkflowStatus(infoForm.getWorkflowstatus());
        tamsWorkflowStatus.setOrder(Integer.parseInt(infoForm.getWorkfloworder()));
        tamsWorkflowStatus.setWorkflowFunctionId(infoForm.getGetWorkFlowStatus());
        if (infoForm.getIndex() != null) {
            TAMSWorkflowStatus tamsWorkflowStatus1 = infoForm.getTamsWorkflowStatuses().get(infoForm.getIndex());
            tamsWorkflowStatus1.setWorkflowStatus(infoForm.getWorkflowstatus());
            tamsWorkflowStatus1.setOrder(Integer.parseInt(infoForm.getWorkfloworder()));
            tamsWorkflowStatus1.setWorkflowFunctionId(infoForm.getGetWorkFlowStatus());
            adminService.saveWorkFlowCategory(tamsWorkflowStatus1);
        } else {
            adminService.saveWorkFlowCategory(tamsWorkflowStatus);
        }
//        return this.getModelAndView(infoForm, "pageWorkFlowCategory");
        return  this.getWorkFlowCategoryPage(infoForm);

    }

    /**
     * 删除工作流类型
     */
    @RequestMapping(params = "methodToCall=deleteWorkFlowCategory")
    public ModelAndView deleteWorkFlowCategory(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        //获取某一行
        CollectionControllerServiceImpl.CollectionActionParameters parameters =
                new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
        int index = parameters.getSelectedLineIndex();
        TAMSWorkflowStatus selectedWorkFlowStatus = infoForm.getTamsWorkflowStatuses().get(index);
        adminService.deleteWorkFlowCategory(selectedWorkFlowStatus);
//        return this.getModelAndView(infoForm, "pageWorkFlowCategory");
        return  this.getWorkFlowCategoryPage(infoForm);
    }


    /**
     * 新增一个时间段
     *
     * @param form
     * @return
     */

    @RequestMapping(params = "methodToCall=addNewTimeSet")
    public ModelAndView addNewTimeSet(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        String typeId = infoForm.getTimeType();

        if(infoForm.getSettingsTime() == null) {
            infoForm.setErrMsg("未设置时间段");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        String[] timeSets = infoForm.getSettingsTime().split("~");
        String startTime = timeSets[0];
        String endTime = timeSets[1];
        User user = (User) getUserSession().retrieveObject("user");
        if (user == null) //// TODO: 16-11-23 应当返回错误信息
            return this.getModelAndView(infoForm, "pageTimeSet");
        boolean result = adminService.addTimeSetting(user, typeId, startTime, endTime);

        return this.resetSettingTime(infoForm);
    }

    /**
     * 清空页面上已填的数值
     *
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=resetSettingTime")
    public ModelAndView resetSettingTime(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        infoForm.setTimeType(null);
        infoForm.setSettingsTime(null);
        String redirectURL = ConfigContext.getCurrentContextConfig().getProperty(KRADConstants.APPLICATION_URL_KEY) + "/portal/admin?methodToCall=getTimeSetPage&viewId=AdminView";
        return this.performRedirect(infoForm, redirectURL);
    }

    /**
     * 删除一个时间段
     *
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=deleteTimeSetting")
    public ModelAndView deleteTimeSetting(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
        int index = params.getSelectedLineIndex();
        TAMSTimeSettings selectedTimeSettings = infoForm.getTimeSettingsList().get(index);
        boolean result = adminService.deleteOneTimeSetting(selectedTimeSettings);
        return this.getTimeSetPage(infoForm);
    }


    @RequestMapping(params = "methodToCall=updateTimeSetting")
    public ModelAndView updateTimeSetting(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
        int index = params.getSelectedLineIndex();
        TAMSTimeSettings selectedTimeSettings = infoForm.getTimeSettingsList().get(index);
        infoForm.setTimeType(selectedTimeSettings.getTimeSettingType().getId());
        infoForm.setSettingsTime(selectedTimeSettings.getStartTime() + "~" + selectedTimeSettings.getEndTime());
        return this.getModelAndView(infoForm, "pageTimeSet");
    }

    //TODO 新增和删除对话框的实例  START

    /**
     * 新增权限对话框   新增的对话框将属性设为空值再直接弹对话框
     *
     * @param form
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=addPermissionDialog")
    public ModelAndView addPermissionDialog(@ModelAttribute("KualiForm") UifFormBase form) throws Exception {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        infoForm.setPermissionNM("");
        infoForm.setPermissionContent("");
        infoForm.setPermissionIndex(null);
        return this.showDialog("savePermissionDialog", true, infoForm);
    }

    /**
     * 更新的对话框需要取到当前需要修改的对象，然后将该对象中的值赋予对话框中的相应字段中。然后再弹对话框
     * PS:务必将选择对象的index放入到form中（或者用其他方法记录下来）
     *
     * @param form
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=updateKrimPerm")
    public ModelAndView updateKrimPerm(@ModelAttribute("KualiForm") UifFormBase form) throws Exception {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        CollectionActionParameters params = new CollectionActionParameters(infoForm, true);
        int index = params.getSelectedLineIndex();
        KRIM_PERM_T selectedKrim_perm_t = infoForm.getRMPkrimPermTs().get(index);
        KRIM_PERM_T krim_perm_t = new KRIM_PERM_T();
        infoForm.setPermissionNM(selectedKrim_perm_t.getName());         //给dialog中的字段赋值
        infoForm.setPermissionContent(selectedKrim_perm_t.getDescription());
        infoForm.setPermissionStatus(selectedKrim_perm_t.getActive());
        infoForm.setPermissionIndex(index);                  //记录index
        return this.showDialog("savePermissionDialog", true, infoForm);
    }

    /**
     * 保存/新增权限二合一方法
     * 根据index的值去判断页面执行的是更新还是新增操作。index如果值是null，则页面是进行的新增操作；反之则是进行的更新操作
     * PS:数据库操作结束之后务必将更新的对象重新放入到页面的显示对象中
     *
     * @param form
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=savePermission")
    public ModelAndView savePermission(@ModelAttribute("KualiForm") UifFormBase form) throws Exception {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        KRIM_PERM_T krimPermTs = new KRIM_PERM_T();
        if (infoForm.getPermissionIndex() != null) {
            krimPermTs = infoForm.getRMPkrimPermTs().get(infoForm.getPermissionIndex());
        }
        String permissionNM = infoForm.getPermissionNM();
        String permissionContent = infoForm.getPermissionContent();
        String permissionStatus = infoForm.getPermissionStatus();

        krimPermTs.setName(permissionNM);
        krimPermTs.setDescription(permissionContent);
        krimPermTs.setActive(permissionStatus);
        //TODO 权限属性分类
        krimPermTs.setTemplateId("56");
        KRIM_PERM_T exist = new KRIM_PERM_T_DaoJpa().findPermissionByName(permissionNM);
        if (exist != null) {
            if (exist.getDescription().equals(permissionContent)) {               //名字描述都相同返回错误信息
                infoForm.setErrMsg("已存在相同权限内容的权限");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            }
            exist.setDescription(permissionContent);
            new KRIM_PERM_T_DaoJpa().addPermissions(exist);
        }
        new KRIM_PERM_T_DaoJpa().addPermissions(krimPermTs);
        if (infoForm.getPermissionIndex() == null) {
            infoForm.getRMPkrimPermTs().add(krimPermTs);   //根据不同的操作对页面显示对象进行修改
        } else {
            infoForm.getRMPkrimPermTs().set((infoForm.getPermissionIndex()), krimPermTs);
        }
        return this.getModelAndView(infoForm, "pagePermissionManagement");
    }

    //TODO 新增和删除对话框的实例  END

    /**
     * 删除权限
     *
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */

    @RequestMapping(params = "methodToCall=deletePermission")
    public ModelAndView deletePermission(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        CollectionActionParameters params = new CollectionActionParameters(infoForm, true);
        int index = params.getSelectedLineIndex();
        KRIM_PERM_T selectedKrim_perm_t = infoForm.getRMPkrimPermTs().get(index);
        new KRIM_PERM_T_DaoJpa().delPermissions(selectedKrim_perm_t);
        infoForm.getRMPkrimPermTs().remove(index);
        return this.getModelAndView(infoForm, "pagePermissionManagement");
    }


    /**
     * 更新角色
     *
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=updateExmKrimRole")
    public ModelAndView updateExmKrimRole(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        List<KRIM_ROLE_T> krimRoleTs = new ArrayList<KRIM_ROLE_T>(infoForm.getRMPkrimRoleTs());

        /** 确定点击的角色 **/
        CollectionActionParameters params = new CollectionActionParameters(infoForm, true);
        int index = params.getSelectedLineIndex();
        KRIM_ROLE_T krimRoleT = krimRoleTs.get(index);

        /** 根据角色找出所有角色的权限 **/
        List<KRIM_ROLE_PERM_T> krimRolePermTs = new ArrayList<KRIM_ROLE_PERM_T>(
                new KRIM_ROLE_PERM_T_DaoJpa().getKrimRolePermTsByRole(krimRoleT));
        /** 列出所有已有权限 **/
        List<KRIM_PERM_T> krimPermTs = new ArrayList<KRIM_PERM_T>(new KRIM_PERM_T_DaoJpa().getAllPermissions());

        /** 给已经有的权限加上勾 **/
        for (KRIM_ROLE_PERM_T krimRolePermT : krimRolePermTs) {
            KRIM_PERM_T tempKrimPermT = krimRolePermT.getKrimPermT();
            if (krimPermTs.contains(tempKrimPermT)) {
                tempKrimPermT.setChecked(true);
                krimPermTs.set(krimPermTs.indexOf(tempKrimPermT), tempKrimPermT);
            }
        }

        infoForm.setRMPkrimRoleT(krimRoleT);
        infoForm.setRMPkrimPermTs(krimPermTs);
        return this.getModelAndView(infoForm, "pageUpdateRole");
    }


    /**
     * 新增角色
     *
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=addExmKrimRole")
    public ModelAndView addExmKrimRole(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        infoForm.setRMPkrimRoleT(null);
        infoForm.setRMPkrimPermTs(new KRIM_PERM_T_DaoJpa().getAllPermissions());

        return this.getModelAndView(infoForm, "pageUpdateRole");
    }


    /**
     * 保存角色以及权限
     *
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */

    @RequestMapping(params = "methodToCall=saveExmKrimRole")
    public ModelAndView saveExmKrimRole(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        KRIM_ROLE_T_Dao krimRoleTDao = new KRIM_ROLE_T_DaoJpa();
        List<KRIM_ROLE_T> krimRoleTs = new ArrayList<KRIM_ROLE_T>(infoForm.getRMPkrimRoleTs());
        KRIM_ROLE_T krimRoleT = infoForm.getRMPkrimRoleT();
        if (krimRoleT == null) {
            infoForm.setErrMsg("角色为空");
             return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        if (krimRoleT.getName() == null) {
            infoForm.setErrMsg("角色名称不能为空");
             return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        KRIM_ROLE_T checkKrimRoleT = krimRoleTDao.getKrimRoleTByName(krimRoleT.getName());
        if (checkKrimRoleT != null) {
            if (krimRoleT.getId() == null || !krimRoleT.getId().equals(checkKrimRoleT.getId())) {
                infoForm.setErrMsg("角色名称已存在");
                 return this.showDialog("refreshPageViewDialog", true, infoForm);
            }
        }
        krimRoleT = krimRoleTDao.saveKrimRoleT(krimRoleT);
        infoForm.setRMPkrimRoleT(krimRoleT);

        List<KRIM_PERM_T> krimPermTs = infoForm.getRMPkrimPermTs();
        new KRIM_ROLE_PERM_T_DaoJpa().saveKrimRolePermTByRoleAndPerms(krimRoleT, krimPermTs);

        infoForm.setRMPkrimRoleTs(new KRIM_ROLE_T_DaoJpa().getAllKrimRoleTs());
        return this.getModelAndView(infoForm, "pageUpdateRole");
    }

    /**
     * 选择用户
     *
     * @param form
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=selectURMInstructor")
    public ModelAndView selectURMInstructor(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                                            HttpServletResponse response) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        CollectionActionParameters params = new CollectionActionParameters(infoForm, true);
        int index = params.getSelectedLineIndex();

        List<UTInstructor> utInstructors = infoForm.getURMutInstructors();
        UTInstructor utInstructor = utInstructors.get(index);
        infoForm.setURMutInstructor(utInstructor);

        if (utInstructor.getIdNumber() == null) {
            infoForm.setErrMsg("该同志没有同一认证号呀");
             return this.showDialog("refreshPageViewDialog", true, infoForm);
        }

        List<KRIM_ROLE_MBR_T> krimRoleMbrTs = new ArrayList<>(
                new KRIM_ROLE_MBR_T_DaoJpa().getKrimEntityEntTypTsByMbrId(utInstructor.getId()));
        List<KRIM_ROLE_T> krimRoleTs = new ArrayList<>(new KRIM_ROLE_T_DaoJpa().getAllKrimRoleTs());

        for (KRIM_ROLE_MBR_T krimRoleMbrT : krimRoleMbrTs) {
            KRIM_ROLE_T tempKrimRoleT = krimRoleMbrT.getKrimRoleT();
            if (krimRoleTs.contains(tempKrimRoleT)) {
                tempKrimRoleT.setChecked(true);
                krimRoleTs.set(krimRoleTs.indexOf(tempKrimRoleT), tempKrimRoleT);
            }
        }
        infoForm.setURMPkrimRoleTs(krimRoleTs);

        return this.getModelAndView(infoForm, "pageUpdateUserRoleManager");
    }

    /**
     * 保存用户
     *
     * @param form
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=saveURMPuser")
    public ModelAndView saveURMPuser(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                                     HttpServletResponse response) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);

        UTInstructor utInstructor = infoForm.getURMutInstructor();
        KRIM_PRNCPL_T krimPrncplT = new KRIM_PRNCPL_T_DaoJpa().getKrimEntityEntTypTByPrncplId(utInstructor.getId());
        List<KRIM_ROLE_T> krimRoleTs = infoForm.getURMPkrimRoleTs();
        new KRIM_ROLE_MBR_T_DaoJpa().saveKrimRoleMbrTByPrncpltAndRoles(krimPrncplT, krimRoleTs);
        return this.getModelAndView(infoForm, "pageUserRoleManager");
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
    @RequestMapping(params = {"pageId=pageCourseManager", "methodToCall=exportCourseManagerExcel"})
    public ModelAndView exportCourseManagerExcel(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                                             HttpServletResponse response) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);


        if (infoForm.getCourseManagerViewObjects() == null || infoForm.getCourseManagerViewObjects().size() == 1) { //size=1是因为会设置至少一个空object让表格不会消失
            if(infoForm.getCourseManagerViewObjects().get(0).getId() == null) {
                infoForm.setErrMsg("列表为空！");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            }
        }

        List<CourseManagerViewObject> coursemanagerlist = infoForm.getCourseManagerViewObjects();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = "课程负责人列表" + "-" + getUserSession().getLoggedInUserPrincipalId() + "-" + sdf.format(new Date()) + ".xls";

        try {
            String filePath = excelService.printCourseManagerExcel(coursemanagerlist, "exportfolder", fileName, "2003");
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
     * 获取课程负责人页面
     * 127.0.0.1:8080/tams/portal/admin?methodToCall=getCourseManagerPage&viewId=AdminView
     *
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getCourseManagerPage")
    public ModelAndView getCourseManagerPage(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);

        User user = (User) getUserSession().retrieveObject("user");
        String uId = user.getCode();

        infoForm.setCourseManagerViewObjects(adminConverter.getCourseManagerToTableViewObject(
                adminService.getCourseManagerByUid(uId)
        ));
        return this.getModelAndView(infoForm, "pageCourseManager");
    }

    /**
     * 将课程负责人表格打印为PDF
     * @param form
     * @return
     *
     */
    @RequestMapping(params = {"pageId=pageCourseManager","methodToCall=exportCourseManagerPDF"})
    public ModelAndView exportCourseManagerPDF(@ModelAttribute("KualiForm") UifFormBase form){
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);

        if (infoForm.getCourseManagerViewObjects() == null || infoForm.getCourseManagerViewObjects().size() == 1) { //size=1是因为会设置至少一个空object让表格不会消失
            if(infoForm.getCourseManagerViewObjects().get(0).getId() == null) {
                infoForm.setErrMsg("列表为空！");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            }
        }
        List<CourseManagerViewObject> courseManagerViewObjectList=infoForm.getCourseManagerViewObjects();

        String filePath=adminService.prepareCourseManagerToPDF(courseManagerViewObjectList );
        if (filePath.equals("exception")){
            infoForm.setErrMsg("系统导出PDF文件错误！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }else{
            String baseUrl= CoreApiServiceLocator.getKualiConfigurationService().getPropertyValueAsString(KRADConstants.ConfigParameters.APPLICATION_URL);
            return this.performRedirect(infoForm,baseUrl+'/'+filePath);
        }

    }




    /**
     * 初始化课程负责人数据
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=initCourseManager")
    public ModelAndView initCourseManager(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        if(adminService.initCourseManagerData())
            return this.getCourseManagerPage(infoForm);
        else {
            infoForm.setErrMsg("初始化失败，请联系管理员！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
    }

    /**
     * 搜索职员表
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=searchInstructorForCourseManager")
    public ModelAndView searchInstructorForCourseManager(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);

        String courseManager = infoForm.getCourseManager();
        String instructorCode = infoForm.getInstructorCode();
        String departmentId = infoForm.getDeptCode();
        infoForm.setInstructorList(adminService.getInstructorByNameAndCodeAndDepartmentId(courseManager,instructorCode,departmentId));

        return this.getModelAndView(infoForm, "pageCourseManager");
    }


    /**
     * 为课程添加课程负责人
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=selectCourseManagerForCourse")
    public ModelAndView selectCourseManagerForCourse(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
        int index = params.getSelectedLineIndex();
        UTInstructor selectedInstructor = infoForm.getInstructorList().get(index);
        int outsideIndex = infoForm.getCourseManagerIndex();
        boolean result = adminService.addCourseManagerByInsIdAndCourseId(selectedInstructor.getId(),infoForm.getCourseManagerViewObjects().get(outsideIndex).getCourseId());
        infoForm.getCourseManagerViewObjects().get(outsideIndex).setCourseManager(selectedInstructor.getName());
        infoForm.getCourseManagerViewObjects().get(outsideIndex).setInstructorCode(selectedInstructor.getCode());
        if(result)
            return this.getModelAndView(infoForm, "pageCourseManager");
        else{
            infoForm.setErrMsg("添加失败！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }

    }


    /**
     * 编辑课程负责人信息
     */
    @Deprecated
    @RequestMapping(params = {"methodToCall=updateCourseManager"})
    public ModelAndView updateCourseManager(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);

        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
        int index = params.getSelectedLineIndex();
        CourseManagerViewObject selectedObject = infoForm.getCourseManagerViewObjects().get(index);
        infoForm.setCourseManagerIndex(index);
        infoForm.setSelectedCourseManagerObject(selectedObject);
        infoForm.setCourseManager(null);  //清空之前的数据
        infoForm.setInstructorCode(null);
        return this.showDialog("confirmEditManagerDialog", true, infoForm);
    }

/**
     * 更新课程负责人
     *
     * @param form
     * @return
     */
/*
    @Deprecated
    @RequestMapping(params = {"methodToCall=saveUpdateCourseManager"})
    public ModelAndView saveUpdateCourseManager(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);

        CourseManagerViewObject selectedObject = infoForm.getSelectedCourseManagerObject();
        TAMSCourseManager tamsCourseManager = tamsCourseManagerDaoJpa.getCourseManagerByInstructorId(selectedObject.getId());
        UTInstructor newManager = new UTInstructorDaoJpa().getInstructorByCode(infoForm.getInstructorCode());
        if (newManager != null) {
            tamsCourseManager.setCourseManagerId(newManager.getId());
            tamsCourseManagerDaoJpa.saveCourseManager(tamsCourseManager);
            infoForm.getCourseManagerViewObjects().get(infoForm.getCourseManagerIndex()).setCourseManager(newManager.getName());
            infoForm.getCourseManagerViewObjects().get(infoForm.getCourseManagerIndex()).setInstructorCode(newManager.getCode());
            infoForm.getCourseManagerViewObjects().get(infoForm.getCourseManagerIndex()).setId(newManager.getId());
            return this.getModelAndView(infoForm, "pageCourseManager");
        } else {
            infoForm.setErrMsg("查无此人");
        }
        //TODO 报错页面待做
        return null;
    }
*/


    /**
     * 课程负责人过滤
     */
    @RequestMapping(params = {"methodToCall=searchCourseManagerByCondition"})
    public ModelAndView searchCourseManagerByCondition(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);

        Map<String, String> conditions = new HashMap<>();
        //put conditions
        conditions.put("CourseName", infoForm.getSearchCourseNm());
        conditions.put("CourseNumber", infoForm.getSearchCourseNmb());
        conditions.put("InstructorName", infoForm.getSearchCourseManager());
        conditions.put("InstructorCode", infoForm.getSearchCourseInsCode());
        //转换成页面所需要的数据对象并调用DAO
        infoForm.setCourseManagerViewObjects(adminConverter.getCourseManagerToTableViewObject(
                adminService.getCourseManagerByCondition(conditions)
                )
        );

        //清除table页面信息缓存
        Map map = new HashMap();
        map.putAll(infoForm.getViewPostMetadata().getComponentPostMetadataMap().get("CourseManagerTable").getData());
        map.put("displayStart",0);
        infoForm.getViewPostMetadata().getComponentPostMetadataMap().get("CourseManagerTable").setData(map);

        return this.getModelAndView(infoForm, "pageCourseManager");
    }


    /**
     * PDF格式打印经费明细表格
     *127.0.0.1:8080/tams/portal/admin?methodToCall=getFundsPage&viewId=AdminView
     * @param form
     * @return
     *
     */
    @RequestMapping(params = {"pageId=pageFundsManagement","methodToCall=exportFundsDetailPDF"})
    public ModelAndView exportJingFeiPDF(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        if (infoForm.getDetailFunding() == null || infoForm.getDetailFunding().size() == 1) { //size=1是因为会设置至少一个空object让表格不会消失
            if(infoForm.getDetailFunding().get(0).getTaName() == null) {
                infoForm.setErrMsg("列表为空！");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            }
        }
//        List<CourseManagerViewObject> courseManagerViewObjectList=infoForm.getCourseManagerViewObjects();
//
            List<DetailFundingViewObject> JingFeiManager = infoForm.getDetailFunding();
            String filePath = adminService.prepareJingFeiToPDF(JingFeiManager);
            if (filePath.equals("exception")) {
                infoForm.setErrMsg("系统导出PDF文件错误！");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            } else {
                String baseUrl = CoreApiServiceLocator.getKualiConfigurationService().getPropertyValueAsString(KRADConstants.ConfigParameters.APPLICATION_URL);
                return this.performRedirect(infoForm, baseUrl + '/' + filePath);
            }

        }
    /**
     * PDF格式打印学校历史经费表格
     *127.0.0.1:8080/tams/portal/admin?methodToCall=getFundsPage&viewId=AdminView
     * @param  form
     * @return
     *
     */
    @RequestMapping(params = {"pageId=pageFundsManagement","methodToCall=exportSchoolHistoryFundsPDF"})
    public ModelAndView exportSchoolHistoryFundsPDF(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        if (infoForm.getPreviousSessionFundings() == null || infoForm.getPreviousSessionFundings().size() == 1) { //size=1是因为会设置至少一个空object让表格不会消失
            if(infoForm.getPreviousSessionFundings().get(0).getSessionName() == null) {
                infoForm.setErrMsg("列表为空！");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            }
        }
//        List<CourseManagerViewObject> courseManagerViewObjectList=infoForm.getCourseManagerViewObjects();
//
        List<SessionFundingViewObject> SchoolHistoryFundsManager=infoForm.getPreviousSessionFundings();
        String filePath = adminService.prepareSchoolHistoryFundsPDF(SchoolHistoryFundsManager);
        if (filePath.equals("exception")) {
            infoForm.setErrMsg("系统导出PDF文件错误！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        } else {
            String baseUrl = CoreApiServiceLocator.getKualiConfigurationService().getPropertyValueAsString(KRADConstants.ConfigParameters.APPLICATION_URL);
            return this.performRedirect(infoForm, baseUrl + '/' + filePath);
        }

    }
    /**
     * PDF格式打印学院历史经费表格
     *127.0.0.1:8080/tams/portal/admin?methodToCall=getFundsPage&viewId=AdminView
     * @param  form
     * @return
     *
     */
    @RequestMapping(params = {"pageId=pageFundsManagement","methodToCall=exportCollegeHistoryFundsPDF"})
    public ModelAndView exportCollegeHistoryFundsPDF(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        if (infoForm.getDepartmentPreFundings()== null || infoForm.getDepartmentPreFundings().size() == 1) { //size=1是因为会设置至少一个空object让表格不会消失
            if(infoForm.getDepartmentPreFundings().get(0).getSessionName() == null) {
                infoForm.setErrMsg("列表为空！");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            }
        }
//        List<CourseManagerViewObject> courseManagerViewObjectList=infoForm.getCourseManagerViewObjects();
//
        List<DepartmentFundingViewObject> CollegeHistoryFundsManager=infoForm.getDepartmentPreFundings();
        String filePath = adminService.prepareCollegeHistoryFundsPDF(CollegeHistoryFundsManager);
        if (filePath.equals("exception")) {
            infoForm.setErrMsg("系统导出PDF文件错误！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        } else {
            String baseUrl = CoreApiServiceLocator.getKualiConfigurationService().getPropertyValueAsString(KRADConstants.ConfigParameters.APPLICATION_URL);
            return this.performRedirect(infoForm, baseUrl + '/' + filePath);
        }

    }
    /**
     * PDF格式打印学院经费表格
     *127.0.0.1:8080/tams/portal/admin?methodToCall=getFundsPage&viewId=AdminView
     * @param  form
     * @return
     *
     */
    @RequestMapping(params = {"pageId=pageFundsManagement","methodToCall=exportCollegeFundsPDF"})
    public ModelAndView exportCollegeFundsPDF(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        if (infoForm.getDepartmentCurrFundings()== null || infoForm.getDepartmentCurrFundings().size() == 1) { //size=1是因为会设置至少一个空object让表格不会消失
            if(infoForm.getDepartmentPreFundings().get(0).getDepartmentId() == null) {
                infoForm.setErrMsg("列表为空！");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            }
        }
//        List<CourseManagerViewObject> courseManagerViewObjectList=infoForm.getCourseManagerViewObjects();
//
        List<DepartmentFundingViewObject> CollegeFundsManager=infoForm.getDepartmentCurrFundings();
        String filePath = adminService.prepareCollegeFundsPDF(CollegeFundsManager);
        if (filePath.equals("exception")) {
            infoForm.setErrMsg("系统导出PDF文件错误！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        } else {
            String baseUrl = CoreApiServiceLocator.getKualiConfigurationService().getPropertyValueAsString(KRADConstants.ConfigParameters.APPLICATION_URL);
            return this.performRedirect(infoForm, baseUrl + '/' + filePath);
        }

    }
    /**
     * excel格式导出课程经费
     *
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = {"pageId=pageFundsManagement", "methodToCall=exportCourseFundsExcel"})
    public ModelAndView exportCourseFundsExcel(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                                                 HttpServletResponse response) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);


        if (infoForm.getClassFundings() == null || infoForm.getClassFundings().size() == 1) { //size=1是因为会设置至少一个空object让表格不会消失
            if(infoForm.getClassFundings().get(0).getDepartment() == null) {
                infoForm.setErrMsg("列表为空！");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            }
        }

        List<ClassFundingViewObject> courseclasslist=infoForm.getClassFundings();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = "课程经费列表" + "-" + getUserSession().getLoggedInUserPrincipalId() + "-" + sdf.format(new Date()) + ".xls";

        try {
            String filePath = excelService.printCourseClassExcel(courseclasslist, "exportfolder", fileName, "2003");
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
     * 导出excel格式助教经费
     *
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = {"pageId=pageFundsManagement", "methodToCall=exportTaFundsExcel"})
    public ModelAndView exportTaFundsExcel(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                                               HttpServletResponse response) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);


        if (infoForm.getTaFunding() == null || infoForm.getTaFunding().size() == 1) { //size=1是因为会设置至少一个空object让表格不会消失
            if(infoForm.getTaFunding().get(0).getDepartmentName() == null) {
                infoForm.setErrMsg("列表为空！");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            }
        }

        List<TaFundingViewObject> tafundinglist=infoForm.getTaFunding();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = "助教经费列表" + "-" + getUserSession().getLoggedInUserPrincipalId() + "-" + sdf.format(new Date()) + ".xls";

        try {
            String filePath = excelService.printTafundingExcel(tafundinglist, "exportfolder", fileName, "2003");
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
     * 获取带charts的经费管理页面
     * 127.0.0.1:8080/tams/portal/admin?methodToCall=getFundsPage&viewId=AdminView
     *
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getFundsPage")
    public ModelAndView getFundsPage(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        String uId = userSession.getLoggedInUserPrincipalId();


        /*
        list.add(new PieChartsNameValuePair("高数", 10000));
        list.add(new PieChartsNameValuePair("线代", 5000));
        list.add(new PieChartsNameValuePair("离散", 4000));
        list.add(new PieChartsNameValuePair("数值", 2000));
        list.add(new PieChartsNameValuePair("C程", 4000));
        list.add(new PieChartsNameValuePair("C程", 4000));
        list.add(new PieChartsNameValuePair("C程", 4000));
        list.add(new PieChartsNameValuePair("C程", 4000));
        list.add(new PieChartsNameValuePair("C程", 4000));
        */



        /*
        infoForm.setSessionFundings(
                taConverter.sessionFundingToViewObject(
                        adminService.getCurrFundingBySession()
                )
        );

        infoForm.setPreviousSessionFundings(
                taConverter.sessionFundingToViewObject(
                        adminService.getPreviousFundingBySession()
                )
        );
        */

        /*
            确定当前是春季还是秋季
         */
        UTSession curSession = utSessionDao.getCurrentSession();
        if (curSession.getTerm().equals("春")) {
            infoForm.setSpringTerm(true);
            infoForm.setFallTerm(false);
        } else {
            infoForm.setFallTerm(true);
            infoForm.setSpringTerm(false);
        }

        if(iUserInfoService.isAcademicAffairsStaff(uId)||iUserInfoService.isSysAdmin(uId))
            infoForm.setAcademicAffairManager(true);
        else
            infoForm.setAcademicAffairManager(false);

        if(iUserInfoService.isCollegeStaff(uId)||iUserInfoService.isSysAdmin(uId)||iUserInfoService.isAcademicAffairsStaff(uId))
            infoForm.setDeptManager(true);
        else
            infoForm.setDeptManager(false);

        //显示批次当前批次经费
        infoForm.setSessionFundings(
                taConverter.sessionFundingToViewObject(
                        adminService.getCurrFundingBySession()
                )
        );


        //显示历史批次经费
        infoForm.setPreviousSessionFundings(
                taConverter.sessionFundingToViewObject(
                        adminService.getPreviousFundingBySession()
                )
        );

        //显示当前学期学院经费
        infoForm.setDepartmentCurrFundings(
                taConverter.departmentFundingToViewObject(
                        adminService.getDepartmentCurrFundingBySession()
                )
        );

        //显示历史学期学院经费
        infoForm.setDepartmentPreFundings(
                taConverter.departmentFundingToViewObject(
                        adminService.getDepartmentPreFundingBySession()
                )
        );

        //显示课程经费
        infoForm.setClassFundings(
                taConverter.classFundingToViewObject(
                        adminService.getFundingByClass()
                )
        );


        //学院经费处的预算经费：已设置/总预算
        infoForm.setSessionFundingStatistics(      //已设置经费/总经费
                adminService.getSessionFundingStatistics()
        );

        //学院经费处的批准经费：已设置/总批准
        infoForm.setSessionFundingTotalApproved(      //已批准经费
                adminService.getSessionFundingTotalApprove(infoForm.getSessionFundings() != null ?
                        infoForm.getSessionFundings().get(0).getActualFunding() : "0")
        );

        /*
            课程经费的已设置/总经费
         */
        infoForm.setClassFundingStatistics(
                taConverter.countClassFunding(
                        infoForm.getClassFundings(),
                        adminService.getClassTotalAssignedFunding(),
                        taConverter.countClassFundingTotalApproved(infoForm.getClassFundings())
                )
        );
         /*
            课程经费的已批准经费
         */
//        infoForm.setClassFundingTotalApproved(
//                taConverter.countClassFundingTotalApproved(
//                        infoForm.getClassFundings()
//                )
//        );

        //显示助教经费
        infoForm.setTaFunding(
                adminConverter.taFundingToViewObject(
                        taService.getAllTaFilteredByUid(uId)
                )
        );


        //显示经费明细
        infoForm.setDetailFunding(
                adminConverter.detailFundingToViewObject(
                        taService.getAllTaFilteredByUid(uId)
                )
        );


       // Gson gson = new Gson();

        //学院经费饼状图
//        List<PieChartsNameValuePair> list = new ArrayList<>();
//        List<DepartmentFundingViewObject> departmentFundingViewObjects = new ArrayList<>();
//        departmentFundingViewObjects = taConverter.departmentFundingToViewObject(
//                adminService.getDepartmentCurrFundingBySession()
//        );
//        if(departmentFundingViewObjects.size() != 0 && departmentFundingViewObjects.get(0).getDepartmentId() != null) {
//            for (DepartmentFundingViewObject per : departmentFundingViewObjects) {
//                list.add(new PieChartsNameValuePair(per.getDepartment(), Integer.parseInt(per.getTotal())));
//            }
//        }
//        else
//            list.add(new PieChartsNameValuePair(null, null));
//        String json = gson.toJson(list);
//        infoForm.setPieChartsNameValuePairs(json);

        //批次经费柱状图
//        List<HistogramNameValuePair> histogramNameValuePairs = new ArrayList<>();
//        List<SessionFundingViewObject> sessionFundingViewObjects = taConverter.sessionFundingToViewObject(
//                adminService.getPreviousFundingBySession()
//        );
//        if(sessionFundingViewObjects.size() != 0 && sessionFundingViewObjects.get(0).getSessionName() != null) {
//            for (SessionFundingViewObject per : sessionFundingViewObjects) {
//                histogramNameValuePairs.add(new HistogramNameValuePair(per.getSessionName(), Integer.parseInt(per.getTotal())));
//            }
//        }
//        else
//            histogramNameValuePairs.add(new HistogramNameValuePair(null, null));
//        String histojson = gson.toJson(histogramNameValuePairs);
//
//        infoForm.setHistogram(histojson);

        return this.getModelAndView(infoForm, "pageFundsManagement");
    }

    /**
     * 发布学院经费
     * @param form
     * @param request
     * @return
     */
    @RequestMapping(params = "methodToCall=ReleaseDeptFunding")
    public ModelAndView Release(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        short code = adminService.getWorkTime();
        if(code == 10){
            infoForm.setErrMsg("管理员未设置相应的时间！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }else if(code == 1) {
            infoForm.setErrMsg("非学院经费设置时间！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        else {
            List<DepartmentFundingViewObject> draftDepartmentFunding = infoForm.getDepartmentCurrFundings();
            Long needToChange = adminService.releaseDeptFunding(draftDepartmentFunding);

            infoForm.setSessionFundingStatistics(      //已设置经费/总经费
                    adminService.getSessionFundingStatistics()
            );

            infoForm.setSessionFundingTotalApproved(      //已批准经费
                    adminService.getSessionFundingTotalApprove(infoForm.getSessionFundings().get(0).getActualFunding())
            );

            //将变化体现到批次经费
            infoForm.getSessionFundings().get(0).setActualFunding(String.valueOf(Long.valueOf(infoForm.getSessionFundings().get(0).getActualFunding()) + needToChange));

            return this.getModelAndView(infoForm, "pageFundsManagement");
        }
    }


    /**
     * 发布课程经费
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=ReleaseCourseFunding")
    public ModelAndView ReleaseCourseFunding(@ModelAttribute("KualiForm") UifFormBase form ) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        short code = adminService.getWorkTime();
        if(code == 10){
            infoForm.setErrMsg("管理员未设置相应的时间！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }else if(code == 2) {
            infoForm.setErrMsg("非课程经费设置时间！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        else {

            List<ClassFundingViewObject> classFundingViewObjects = infoForm.getClassFundings();
            /*
            List<String> classids= new ArrayList<>();
            for(ClassFundingViewObject per: classFundingViewObjects) {
                String classid = per.getClassId();
                classids.add(classid);
            }
            */
            adminService.releaseClassFunding(classFundingViewObjects);

            infoForm.setClassFundingStatistics(
                    taConverter.countClassFunding(
                            infoForm.getClassFundings(),
                            adminService.getClassTotalAssignedFunding(),
                            taConverter.countClassFundingTotalApproved(infoForm.getClassFundings())
                    )
            );

            return this.getModelAndView(infoForm, "pageFundsManagement");
        }
    }

    /**
     * 保存课程经费
     */
    @RequestMapping(params = "methodToCall=SaveClassFunding")
    public ModelAndView SaveClassFunding(@ModelAttribute("KualiForm") UifFormBase form   ) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        short code = adminService.getWorkTime();
        if(code == 10){
            infoForm.setErrMsg("管理员未设置相应的时间！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }else if(code == 2) {
            infoForm.setErrMsg("非课程经费设置时间！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        else {
            List<ClassFundingViewObject> draftClassFunding = infoForm.getClassFundings();
            String totalAssignedClass = adminService.getClassTotalAssignedFunding();
            if(adminService.countClassFunding(draftClassFunding, totalAssignedClass) == 1) {
                infoForm.setErrMsg("课程经费超支！请重新设置");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            }
            if(adminService.countClassFunding(draftClassFunding, totalAssignedClass) == 2) {
                adminService.saveClassFunding(draftClassFunding);
                infoForm.setClassFundingStatistics(
                        taConverter.countClassFunding(
                                infoForm.getClassFundings(),
                                adminService.getClassTotalAssignedFunding(),
                                taConverter.countClassFundingTotalApproved(infoForm.getClassFundings())
                        )
                );
            }
            return this.getModelAndView(infoForm, "pageFundsManagement");
        }
    }

    /**
     *保存助教经费
     */
    @RequestMapping(params = "methodToCall=SaveTaFunding")
    public ModelAndView SaveTaFunding(@ModelAttribute("KualiForm") UifFormBase form   ) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        short code = adminService.getWorkTime();
        if(code == 10){
            infoForm.setErrMsg("管理员未设置相应的时间！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }else if(code == 3) {
            infoForm.setErrMsg("非助教经费设置时间！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        else {
            List<TaFundingViewObject> taFundingViewObjects = infoForm.getTaFunding();
            if(adminService.countTaFunding(taFundingViewObjects) == 1) {
                infoForm.setErrMsg("该课程的助教经费经费超支！请重新设置");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            }
            if(adminService.countTaFunding(taFundingViewObjects) == 2)
                adminService.saveTaFunding(taFundingViewObjects);
            return this.getModelAndView(infoForm, "pageFundsManagement");
        }
    }

    /**
     * 保存学院经费
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=SaveDeptFunding")
    public ModelAndView SaveDeptFunding(@ModelAttribute("KualiForm") UifFormBase form   ) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        short code = adminService.getWorkTime();
        if(code == 10){
            infoForm.setErrMsg("管理员未设置相应的时间！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }else if(code == 1) {
            infoForm.setErrMsg("非学院经费设置时间！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        else {
            //List<TAMSDeptFundingDraft> draftDepartmentFunding = adminService.getAllDeptFundingDraft();
            List<DepartmentFundingViewObject> draftDepartmentFundingViewObject = infoForm.getDepartmentCurrFundings();

            if(adminService.countDeptFunding(draftDepartmentFundingViewObject) == 1) {
                infoForm.setErrMsg("预算经费超支！请重新设置");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            }
            if(adminService.countDeptFunding(draftDepartmentFundingViewObject) == 2) {
                infoForm.setErrMsg("批准经费超支！请重新设置");
                return this.showDialog("refreshPageViewDialog", true, infoForm);
            }

            if(adminService.countDeptFunding(draftDepartmentFundingViewObject) == 3) {
                adminService.saveDeptFunding(draftDepartmentFundingViewObject);

                infoForm.setSessionFundingStatistics(      //已设置/总预算
                        adminService.getSessionFundingStatistics()
                );

                infoForm.setSessionFundingTotalApproved(      //已批准/总批准
                        adminService.getSessionFundingTotalApprove(draftDepartmentFundingViewObject)
                );
            }
            return this.getModelAndView(infoForm, "pageFundsManagement");

        }
    }

    /**
     * 保存批次经费
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=SaveSessionFunding")
    public ModelAndView SaveSessionFunding(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        short code = adminService.getWorkTime();
        if(code == 10){
            infoForm.setErrMsg("管理员未设置相应的时间！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }else if(code == 1) {
            infoForm.setErrMsg("非批次预算经费设置时间！");
            return this.showDialog("refreshPageViewDialog", true, infoForm);
        }
        else {
            List<SessionFundingViewObject> sessionFundingViewObjects = infoForm.getSessionFundings();
            adminService.saveSessionFunding(sessionFundingViewObjects);
            infoForm.setSessionFundingStatistics(      //已设置经费/总经费
                    adminService.getSessionFundingStatistics(sessionFundingViewObjects.get(0).getPlanFunding())
            );

            return this.getModelAndView(infoForm, "pageFundsManagement");
        }


    }

    /**
     * 学校（批次）历史经费过滤
     */
    @RequestMapping(params = {"methodToCall=searchUTFundingByCondition"})
    public ModelAndView searchUTFundingByCondition(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);

        Map<String, String> conditions = new HashMap<>();
        //put conditions
        conditions.put("Session", infoForm.getsTimes());
        conditions.put("PlanFunding", infoForm.getsPreFunds());
        conditions.put("ApplyFunding", infoForm.getsApplyFunds());
        conditions.put("ApprovalFunding", infoForm.getsApprovalFunds());
        conditions.put("AddFunding", infoForm.getsAddingFunds());
        conditions.put("Bonus", infoForm.getsRewardFunds());
        conditions.put("TravelFunding", infoForm.getdTrafficFunds());
        //conditions.put("TotalFunding", infoForm.getsTotalFunds());
        //转换成页面所需要的数据对象并调用DAO
        infoForm.setPreviousSessionFundings(taConverter.sessionFundingToViewObject(
                adminService.getUniFundPreByCondition(conditions)
                )
        );

        //清除table页面信息缓存
        Map map = new HashMap();
        map.putAll(infoForm.getViewPostMetadata().getComponentPostMetadataMap().get("FundsManagementSchoolTable").getData());
        map.put("displayStart",0);
        infoForm.getViewPostMetadata().getComponentPostMetadataMap().get("FundsManagementSchoolTable").setData(map);

        return this.getModelAndView(infoForm, "pageFundsManagement");
    }

    /**
     * 学院当前经费过滤
     */
    @RequestMapping(params = {"methodToCall=searchCurrDeptFundingByCondition"})
    public ModelAndView searchCurrDeptFundingByCondition(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);

        Map<String, String> conditions = new HashMap<>();
        //put conditions
        User user = (User) getUserSession().retrieveObject("user");
        String uId = user.getCode();
        if(userInfoService.isSysAdmin(uId) || userInfoService.isAcademicAffairsStaff(uId)) {
            conditions.put("Dept", infoForm.getDepartmentId());
        }else if(userInfoService.isCollegeStaff(uId)){
            conditions.put("Dept", user.getDepartmentId().toString());
        }
        //conditions.put("Dept", infoForm.getDepartmentId());
        conditions.put("PlanFunding", infoForm.getDepartmentPreFunds());
        conditions.put("ApplyFunding", infoForm.getDepartmentApplyFunds());
        conditions.put("ApprovalFunding", infoForm.getDepartmentApprovalFunds());
        conditions.put("PhdFunding", infoForm.getDepartmentAddingFunds());
        conditions.put("Bonus", infoForm.getDepartmentRewardFunds());
        conditions.put("TravelFunding", infoForm.getDepartmentTrafficFunds());
        //转换成页面所需要的数据对象并调用DAO
        infoForm.setDepartmentCurrFundings(
                taConverter.departmentFundingToViewObject(
                        adminService.getDeptFundCurrByCondition(conditions)
                )
        );

        //清除table页面信息缓存
        Map map = new HashMap();
        map.putAll(infoForm.getViewPostMetadata().getComponentPostMetadataMap().get("FundsManagerDepartmentTable").getData());
        map.put("displayStart",0);
        infoForm.getViewPostMetadata().getComponentPostMetadataMap().get("FundsManagerDepartmentTable").setData(map);

        return this.getModelAndView(infoForm, "pageFundsManagement");
    }


    /**
     * 课程经费过滤
     */
    @RequestMapping(params = {"methodToCall=searchClassFundingByCondition"})
    public ModelAndView searchClassFundingByCondition(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        //put conditions
        Map<String, String> conditions = new HashMap<>();

        User user = (User) getUserSession().retrieveObject("user");
        String uId = user.getCode();
        if(userInfoService.isSysAdmin(uId) || userInfoService.isAcademicAffairsStaff(uId)) {
            conditions.put("dept", infoForm.getcDept());
            conditions.put("teacher", infoForm.getcTeacher());
        }
        else if(userInfoService.isCollegeStaff(uId)){
            conditions.put("dept", user.getDepartmentId().toString());
            conditions.put("teacher", infoForm.getcTeacher());
        }
        else if (userInfoService.isInstructor(uId)) {
            conditions.put("dept", infoForm.getcDept());
            conditions.put("teacher", user.getName());
        }

        conditions.put("className", infoForm.getcName());
        conditions.put("classCode", infoForm.getcCode());
        conditions.put("classNbr", infoForm.getcNbr());
        //conditions.put("teacher", infoForm.getcTeacher());
        conditions.put("applyFunding", infoForm.getcApplyFunds());
        conditions.put("actualFunding", infoForm.getcActualFunds());
        conditions.put("phdFunding", infoForm.getcPhdFunds());
        conditions.put("bonus", infoForm.getcBonus());
        conditions.put("travelFunding", infoForm.getcTrafficFunds());
        infoForm.setClassFundings(
                adminConverter.combineClassFunding(
                        adminService.getClassFundByCondition(conditions)
                )
        );

        //清除table页面信息缓存
        Map map = new HashMap();
        map.putAll(infoForm.getViewPostMetadata().getComponentPostMetadataMap().get("FundsManagerCourseTable").getData());
        map.put("displayStart",0);
        infoForm.getViewPostMetadata().getComponentPostMetadataMap().get("FundsManagerCourseTable").setData(map);

        return this.getModelAndView(infoForm, "pageFundsManagement");
    }

    /**
     * 助教经费过滤
     */
    @RequestMapping(params = {"methodToCall=searchTaFundingByCondition"})
    public ModelAndView searchTaFundingByCondition(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        //put conditions
        Map<String, String> conditions = new HashMap<>();

        User user = (User) getUserSession().retrieveObject("user");
        String uId = user.getCode();
        if(userInfoService.isSysAdmin(uId) || userInfoService.isAcademicAffairsStaff(uId)) {
            conditions.put("dept", infoForm.gettDept());
            conditions.put("user", null);
            conditions.put("Number", infoForm.gettNumber());
            conditions.put("Name", infoForm.gettName());
        }
        else if(userInfoService.isCollegeStaff(uId)){
            conditions.put("dept", user.getDepartmentId().toString());
            conditions.put("user", null);
            conditions.put("Name", infoForm.gettName());
            conditions.put("Number", infoForm.gettNumber());
        }
        else if(userInfoService.isInstructor(uId)){
            conditions.put("dept", infoForm.gettDept());
            conditions.put("user", uId);
            conditions.put("Name", infoForm.gettName());
            conditions.put("Number", infoForm.gettNumber());
        }
        else{
            conditions.put("dept", user.getDepartmentId().toString());
            conditions.put("user", uId);
            conditions.put("Name", infoForm.gettName());
            conditions.put("Number", uId);
        }

        conditions.put("Type", infoForm.gettType());
        conditions.put("CourseName", infoForm.gettCourseName());
        conditions.put("CourseCode", infoForm.gettCourseCode());
        conditions.put("ClassNbr", infoForm.gettClassNbr());
        conditions.put("AssignedFunding", infoForm.gettAssignedFunds());
        conditions.put("PhdFunding", infoForm.gettPhdFunds());
        conditions.put("TravelFunding", infoForm.gettTrafficFunds());
        conditions.put("Bonus", infoForm.gettBonus());
        infoForm.setTaFunding(
                adminConverter.combineTaFunding(
                        adminService.getTaFundByCondition(conditions)
                )
        );

        //清除table页面信息缓存
        Map map = new HashMap();
        map.putAll(infoForm.getViewPostMetadata().getComponentPostMetadataMap().get("FundsManagerAssistantTable").getData());
        map.put("displayStart",0);
        infoForm.getViewPostMetadata().getComponentPostMetadataMap().get("FundsManagerAssistantTable").setData(map);

        return this.getModelAndView(infoForm, "pageFundsManagement");
    }

    /**
     * 经费明细过滤
     */
    @RequestMapping(params = {"methodToCall=searchDetailFundingByCondition"})
    public ModelAndView searchDetailFundingByCondition(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        //put conditions
        Map<String, String> conditions = new HashMap<>();
        //获取uId(主键)
        /*
        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        String uId = userSession.getLoggedInUserPrincipalId();
        */
        User user = (User) getUserSession().retrieveObject("user");
        String uId = user.getCode();
        //若是系统管理员和教务处管理员，则查看全部
        if(userInfoService.isSysAdmin(uId) || userInfoService.isAcademicAffairsStaff(uId)){
            conditions.put("dept", null);
            conditions.put("user", null);

            conditions.put("Name", infoForm.getDetailsName());
            conditions.put("Number", infoForm.getDetailsNumber());

        }
        //若是学院管理员，则查看本学院的助教的经费明细
        else if(userInfoService.isCollegeStaff(uId)){
            conditions.put("dept", user.getDepartment());
            conditions.put("user", null);

            conditions.put("Name", infoForm.getDetailsName());
            conditions.put("Number", infoForm.getDetailsNumber());

        }
        //若是教师，查看自己的助教的经费
        else if(userInfoService.isInstructor(uId)){
            conditions.put("dept", null);
            conditions.put("user", uId);

            conditions.put("Name", infoForm.getDetailsName());
            conditions.put("Number", infoForm.getDetailsNumber());

        }
        //若是助教，则查看自己的
        else{
            conditions.put("dept",null);
            conditions.put("user", null);

            conditions.put("Name", infoForm.getDetailsName());
            conditions.put("Number", uId);
            //conditions.put("Number", infoForm.getDetailsNumber());

        }

        conditions.put("Bank", infoForm.getDetailsBank());
        conditions.put("BankNbr", infoForm.getDetailsBankNumber());
        conditions.put("IdCard", infoForm.getDetailsIDCard());
        conditions.put("CourseName", infoForm.getDetailsCourseName());
        conditions.put("CourseCode", infoForm.getDetailsCourseCode());
        conditions.put("classNbr", infoForm.getDetailsClassNbr());
        conditions.put("month1", infoForm.getMonth1());
        conditions.put("month2", infoForm.getMonth2());
        conditions.put("month3", infoForm.getMonth3());
        conditions.put("month4", infoForm.getMonth4());
        conditions.put("month5", infoForm.getMonth5());
        conditions.put("month6", infoForm.getMonth6());
        conditions.put("month7", infoForm.getMonth7());
        conditions.put("month8", infoForm.getMonth8());
        conditions.put("month9", infoForm.getMonth9());
        conditions.put("month10", infoForm.getMonth10());
        conditions.put("month11", infoForm.getMonth11());
        conditions.put("month12", infoForm.getMonth12());

        infoForm.setDetailFunding(
                adminConverter.combineDetailFunding(
                        adminService.getDetailFundByCondition(conditions))
                );

        //清除table页面信息缓存
        Map map = new HashMap();
        map.putAll(infoForm.getViewPostMetadata().getComponentPostMetadataMap().get("FundsDetailsTable").getData());
        map.put("displayStart",0);
        infoForm.getViewPostMetadata().getComponentPostMetadataMap().get("FundsDetailsTable").setData(map);

        return this.getModelAndView(infoForm, "pageFundsManagement");
    }


    /**
     * 用户管理页面过滤
     */
    @RequestMapping(params = {"methodToCall=searchUserByCondition"})
    public ModelAndView searchUserByCondition(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        Map<String, String> conditions = new HashMap<>();
        conditions.put("departmentId",infoForm.getUserRoleDepartmentName());
        conditions.put("userName",infoForm.getUserRoleName());
        conditions.put("userGender",infoForm.getUserRoleGender());
        conditions.put("userCode",infoForm.getUserRoleNumber());
        conditions.put("userAuthId",infoForm.getUserRoleIDNumber());
        infoForm.setURMutInstructors(adminService.getInstructorByconditions(conditions));

        //清除table页面信息缓存
        Map map = new HashMap();
        map.putAll(infoForm.getViewPostMetadata().getComponentPostMetadataMap().get("UserRoleManagerTable").getData());
        map.put("displayStart",0);
        infoForm.getViewPostMetadata().getComponentPostMetadataMap().get("UserRoleManagerTable").setData(map);

        return this.getModelAndView(infoForm, "pageUserRoleManager");
    }


    /**
     * 删除课程负责人
     */
    @RequestMapping(params = {"methodToCall=deleteCourseManager"})
    public ModelAndView deleteCourseManager(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);

        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
        int index = params.getSelectedLineIndex();
        CourseManagerViewObject selectedObject = infoForm.getCourseManagerViewObjects().get(index);
        tamsCourseManagerDao.deleteCourseManager(tamsCourseManagerDao.getCourseManagerByInstructorId(selectedObject.getId()));
        infoForm.getCourseManagerViewObjects().remove(index);
        return this.getModelAndView(infoForm, "pageCourseManager");
    }


    /**
     * 获取课程类别管理页面
     * 127.0.0.1:8080/tams/portal/admin?methodToCall=getCourseCategoryPage&viewId=AdminView
     *
     * @param form
     * @return
     */
    @RequestMapping(params = {"methodToCall=getCourseCategoryPage"})
    public ModelAndView getCourseCategoryPage(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        infoForm.setAllClassifications(
                adminService.getAllClassification()
        );
        return this.getModelAndView(infoForm, "pageCourseCategory");
    }

    /**
     * 编辑/添加项返回方法
     * pageCourseCategory
     *
     * @param form
     * @return
     */
    @RequestMapping(params = {"pageId=pageCourseCategory", "methodToCall=selectCurObj"})
    public ModelAndView selectCurObj(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);

        try {
            // if index is exit, then enter edit dialog
            CollectionControllerServiceImpl.CollectionActionParameters params =
                    new CollectionControllerServiceImpl.CollectionActionParameters(adminInfoForm, true);
            int index = params.getSelectedLineIndex();

            adminInfoForm.setClassification(adminInfoForm.getAllClassifications().get(index));

            return this.showDialog("editCourseCategoryDialog", true, adminInfoForm);
        } catch (RuntimeException e) {
            adminInfoForm.setClassification(new CMCourseClassification());
            return this.showDialog("addCourseCategoryDialog", true, adminInfoForm);
        }
    }

    /**
     * 编辑课程大类
     * pageCourseCategory
     *
     * @param form
     * @return
     */
    @RequestMapping(params = {"methodToCall=updateCourseCategory"})
    public ModelAndView updateCourseCategory(@ModelAttribute("KualiForm") UifFormBase form,
                                             HttpServletRequest request,
                                             HttpServletResponse response) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);
        if(adminInfoForm.getClassification().getName()==null){
            adminInfoForm.setErrMsg("请填写名称！");
            return this.showDialog("refreshPageViewDialog", true, adminInfoForm);
        }
        adminService.changeCourseClassificationNameById(adminInfoForm.getClassification().getId(),
                adminInfoForm.getClassification().getName());

        return this.getCourseCategoryPage(form);
    }

    /**
     * 添加新的课程大类
     * pageCourseCategory
     *
     * @param form
     * @return
     */
    @RequestMapping(params = {"methodToCall=addNewCategory"})
    public ModelAndView addNewCategory(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);
        if(adminInfoForm.getClassification().getName()==null){
            adminInfoForm.setErrMsg("请填写名称！");
            return this.showDialog("refreshPageViewDialog", true, adminInfoForm);
        }
        // 新添加的term，对应外部的dialog
        adminService.addCourseClassificationOnlyWithName(adminInfoForm.getClassification().getName());

        return this.getCourseCategoryPage(form);
    }

    /**
     * 删除课程大类
     * pageCourseCategory
     *
     * @param form
     * @return
     */
    @RequestMapping(params = {"methodToCall=deleteTermCourseCategory"})
    public ModelAndView deleteTermCourseCategory(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);

        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(adminInfoForm, true);

        int index = params.getSelectedLineIndex();

        CMCourseClassification cmCourseClassification = adminInfoForm.getAllClassifications().get(index);

        if (adminService.removeCourseClassificationById(cmCourseClassification.getId()))
            return this.getCourseCategoryPage(form);
        else
            // TODO: 2016/10/29 弹出错误提示，具体错误信息待补充
            adminInfoForm.setErrMsg("删除失败(修改为错误提示)");
        return this.showDialog("adminErrDialog", true, adminInfoForm);
    }

    /**
     * 获取助教类别管理页面
     * 127.0.0.1:8080/tams/portal/admin?methodToCall=getTaCategoryPage&viewId=AdminView
     *
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getTaCategoryPage")
    public ModelAndView getTaCategoryPage(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);

        adminInfoForm.setAllTaCategories(
                adminService.getAllTaCategories()
        );

        return this.getModelAndView(adminInfoForm, "pageTaCategory");
    }

    /**
     * 编辑/删除现有项返回方法
     * pageTaCategory
     *
     * @param form
     * @return
     */
    @RequestMapping(params = {"pageId=pageTaCategory", "methodToCall=selectCurTa"})
    public ModelAndView selectCurTa(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);

        try {
            //存在index进入edit dialog
            CollectionControllerServiceImpl.CollectionActionParameters params =
                    new CollectionControllerServiceImpl.CollectionActionParameters(adminInfoForm, true);
            int index = params.getSelectedLineIndex();

            adminInfoForm.setOldTaCategory(adminInfoForm.getAllTaCategories().get(index));
            adminInfoForm.setTaIndex(index);

            return this.showDialog("editTaCategoryDialog", true, adminInfoForm);
        } catch (RuntimeException e) {
            //没有选中则进入catch，进而进入new dialog
            adminInfoForm.setOldTaCategory(new TAMSTaCategory());

            return this.showDialog("addTaCategoryDialog", true, adminInfoForm);
        }
    }

    /**
     * add,update助教的方法二合一
     * 根据是否有index来判断本次操作类型是update还是add
     *
     * @param form
     * @return
     */
    @RequestMapping(params = {"methodToCall=saveTaCategory"})
    public ModelAndView saveTaCategory(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);

        if(adminInfoForm.getOldTaCategory().getName()==null||adminInfoForm.getOldTaCategory().getHourlyWage()==null){
            adminInfoForm.setErrMsg("请填写名称和时薪!");
            return this.showDialog("refreshPageViewDialog", true, adminInfoForm);
        }


        if (adminInfoForm.getTaIndex() != null) {
            if (!adminService.changeTaCategoryByEntiy(adminInfoForm.getOldTaCategory())) {
                // TODO: 2016/10/29 弹出错误提示，具体错误信息待补充
                adminInfoForm.setErrMsg("编辑助教类别失败!");
                return this.showDialog("refreshPageViewDialog", true, adminInfoForm);
            }
        } else {
            if (!adminService.addTaCategory(adminInfoForm.getOldTaCategory())) {
                // TODO: 2016/10/29 弹出错误提示，具体错误信息待补充
                adminInfoForm.setErrMsg("添加助教类别失败!");
                return this.showDialog("refreshPageViewDialog", true, adminInfoForm);
            }
        }
        return this.getTaCategoryPage(form);
    }

    /**
     * 删除助教类别
     * pageCourseCategory
     *
     * @param form
     * @return
     */
    @RequestMapping(params = {"methodToCall=deleteTaCategory"})
    public ModelAndView deleteTaCategory(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);

        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(adminInfoForm, true);
        int index = params.getSelectedLineIndex();

        TAMSTaCategory taCategory = adminInfoForm.getAllTaCategories().get(index);

        if (adminService.removeTaCategoryById(Integer.parseInt(taCategory.getId()))) {
            return this.getTaCategoryPage(form);
        } else {
            // TODO: 2016/10/29 弹出错误提示，具体错误信息待补充
            adminInfoForm.setErrMsg("删除失败(修改为错误提示)");
            return this.showDialog("adminErrDialog", true, adminInfoForm);
        }
    }

    /**
     * 获取任务类别管理页面
     * 127.0.0.1:8080/tams/portal/admin?methodToCall=getTaskCategoryPage&viewId=AdminView
     *
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getTaskCategoryPage")
    public ModelAndView getTaskCategoryPage(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);

        adminInfoForm.setAllIssueTypes(adminService.getAllIssueTypes());

        return this.getModelAndView(adminInfoForm, "pageTaskCategory");
    }

    /**
     * 编辑/添加项返回方法
     * pageTaskCategory
     *
     * @param form
     * @return
     */
    @RequestMapping(params = {"pageId=pageTaskCategory", "methodToCall=selectCurTask"})
    public ModelAndView selectCurTask(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);
        try {
            //存在index进入edit dialog
            CollectionControllerServiceImpl.CollectionActionParameters params =
                    new CollectionControllerServiceImpl.CollectionActionParameters(adminInfoForm, true);
            int index = params.getSelectedLineIndex();
            adminInfoForm.setIssueType(adminInfoForm.getAllIssueTypes().get((index)));
            adminInfoForm.setIssueIndex(index);
            return this.showDialog("editTaskCategoryDialog", true, adminInfoForm);
        } catch (RuntimeException e) {
            //没有选中则进入catch
            //进而进入new dialog
            adminInfoForm.setIssueType(new TAMSIssueType());

            return this.showDialog("addTaskCategoryDialog", true, adminInfoForm);
        }

    }

    /**
     * add,update任务类型的方法二合一
     * 根据是否有index来判断本次操作类型是update还是add
     *
     * @param form
     * @return
     */
    @RequestMapping(params = {"methodToCall=saveTaskCategory"})
    public ModelAndView saveTaskCategory(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);
        if(adminInfoForm.getIssueType().getTypeName()==null){
            adminInfoForm.setErrMsg("请填写任务类型名称!");
            return this.showDialog("refreshPageViewDialog", true, adminInfoForm);

        }



            if (adminInfoForm.getIssueIndex() != null) {
            // index不为空说明要调用update
            if (!adminService.changeIssueType(adminInfoForm.getIssueType())) {
                // TODO: 2016/10/29 弹出错误提示，具体错误信息待补充
                adminInfoForm.setErrMsg("编辑失败！");
                return this.showDialog("refreshPageViewDialog", true, adminInfoForm);
            }
        } else {
            // add
            if (!adminService.addTaIssueType(adminInfoForm.getIssueType())) {
                // TODO: 2016/10/29 弹出错误提示，具体错误信息待补充
                adminInfoForm.setErrMsg("添加失败！");
                return this.showDialog("refreshPageViewDialog", true, adminInfoForm);
            }
        }
        return this.getTaskCategoryPage(form);
    }

    /**
     * 删除任务类别
     * pageTaskCategory
     *
     * @param form
     * @return
     */
    @RequestMapping(params = {"methodToCall=deleteTaskCategory"})
    public ModelAndView deleteTaskCategory(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);

        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(adminInfoForm, true);
        int index = params.getSelectedLineIndex();

        TAMSIssueType issueType = adminInfoForm.getAllIssueTypes().get(index);

        if (adminService.removeIssueTypeById(issueType.getId())) {
            return this.getTaskCategoryPage(form);
        } else {
            // TODO: 2016/10/29 弹出错误提示，具体错误信息待补充
            adminInfoForm.setErrMsg("删除失败！");
            return this.showDialog("refreshPageViewDialog", true, adminInfoForm);
        }
    }

    /**
     * 获取term(学期或批次)管理页面
     * 127.0.0.1:8080/tams/portal/admin?methodToCall=getTermManagePage&viewId=AdminView
     *
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getTermManagePage")
    public ModelAndView getTermManagePage(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);

        adminInfoForm.setAllTerms(
                taConverter.termInfoToViewObject(
                        adminService.getAllSessions()
                )
        );
        adminInfoForm.setOldTerms(adminInfoForm.getAllTerms());

        return this.getModelAndView(adminInfoForm, "pageTermManagement");
    }

    /**
     * 查询批次
     * pageTermManage
     * 三个条件不能缺省
     *
     * @param form
     * @return
     */

    @RequestMapping(params = "methodToCall=searchTerm")
    public ModelAndView searchTerm(@ModelAttribute("KualiForm") UifFormBase form) throws ParseException {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);

        String termName = adminInfoForm.getTermName();
        String startTime = adminInfoForm.getStartTime();
        String endTime = adminInfoForm.getEndTime();

        if (adminInfoForm.getTotalMoney() != null) {
            // FIXME: 2016/11/5 暂不使用这一参数，如需使用需修改
            adminInfoForm.setErrMsg("这一参数不可用!");
            return this.showDialog("refreshPageViewDialog", true, adminInfoForm);
        }

        // 参数全空，返回原来值
        if (termName == null && startTime == null && endTime == null) {
            adminInfoForm.setAllTerms(adminInfoForm.getOldTerms());

            return this.getModelAndView(adminInfoForm, "pageTermManagement");
        } else if (termName == null || startTime == null || endTime == null) {
            // 有参数就不能缺省
            // FIXME: 2016/11/5 错误提示可以修改，条件也许可以变成缺省，也可以增加条件
            adminInfoForm.setErrMsg("缺少条件！");
            return this.showDialog("refreshPageViewDialog", true, adminInfoForm);
        } else {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            Date begin = format.parse(startTime);
            Date end = format.parse(endTime);
            // 时间颠倒
            if (begin.after(end)) {
                adminInfoForm.setErrMsg("时间错误，请重新输入!");
                return this.showDialog("refreshPageViewDialog", true, adminInfoForm);
            }

            // 格式异常处理
            List<UTSession> results = adminService.getSelectedSessions(termName, startTime, endTime);
            if (results.size() != 0) {
                String testError = results.get(0).getYear();
                if (testError.charAt(0) == '1') {
                    adminInfoForm.setErrMsg("批次名称格式错误，应为\"xxxx年x季\"，例如 2014年秋季");
                    return this.showDialog("refreshPageViewDialog", true, adminInfoForm);
                }
            }
            adminInfoForm.setAllTerms(
                    taConverter.termInfoToViewObject(
                            results
                    )
            );
            return this.getModelAndView(adminInfoForm, "pageTermManagement");
        }
    }

    /**
     * 编辑/添加项返回方法
     * pageTaskCategory
     *
     * @param form
     * @return
     */
    @RequestMapping(params = {"pageId=pageTermManagement", "methodToCall=selectCurTerm"})
    public ModelAndView selectCurTerm(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);

        try {
            //存在index进入edit dialog
            CollectionControllerServiceImpl.CollectionActionParameters params =
                    new CollectionControllerServiceImpl.CollectionActionParameters(adminInfoForm, true);
            int index = params.getSelectedLineIndex();
            TermManagerViewObject termManagerViewObject = adminInfoForm.getAllTerms().get(index);
            String termName = termManagerViewObject.getTermName();
            String year = termName.substring(0, termName.indexOf("年"));
            String term = termName.substring(termName.indexOf("年") + 1);
            termManagerViewObject.setTermYear(year);
            termManagerViewObject.setTermTerm(term);
            adminInfoForm.setCurrentTerm(termManagerViewObject);
            adminInfoForm.setTermIndex(index);
            return this.showDialog("confirmEditTermDialog", true, adminInfoForm);
        } catch (RuntimeException e) {
            //没有选中则进入catch
            //进而进入new dialog
            //// FIXME: 16-10-30 无法实现默认选择当前年份
            TermManagerViewObject newTerm = new TermManagerViewObject();
            newTerm.setTermYear(
                    Integer.toString(
                            Calendar.getInstance().get(
                                    Calendar.YEAR
                            )
                    )
            );
            adminInfoForm.setCurrentTerm(new TermManagerViewObject());
            adminInfoForm.setTermIndex(null);

            return this.showDialog("addNewTermDialog", true, adminInfoForm);
        }
    }

    /**
     * add,update任务类型的方法二合一
     * 根据是否有index来判断本次操作类型是update还是add
     *
     * @param form
     * @return
     */
    @RequestMapping(params = {"methodToCall=saveTerm"})
    public ModelAndView saveTerm(@ModelAttribute("KualiForm") UifFormBase form) throws ParseException {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);
        //批次总经费
        //String sessionFunds = adminInfoForm.getCurrentTerm().getBudget().toString();
        if (adminInfoForm.getTermIndex() != null) {
            if(adminInfoForm.getCurrentTerm().getStartDate()==null||adminInfoForm.getCurrentTerm().getEndDate()==null){
                adminInfoForm.setErrMsg("请填写开始时间以及完成时间！");
                return this.showDialog("adminErrDialog", true, adminInfoForm);
            }
            // index不为空说明要调用update
            if (!adminService.changeSession(
                    taConverter.termToDataObject(
                            adminInfoForm.getCurrentTerm()
                    ))) {
                // TODO: 2016/10/29 弹出错误提示，具体错误信息待补充
                adminInfoForm.setErrMsg("编辑失败(修改为错误提示)");
                return this.showDialog("adminErrDialog", true, adminInfoForm);
            }
        } else {
            // add
            //同时进行批次和学院经费初始化
            if(adminInfoForm.getCurrentTerm().getStartDate()==null||adminInfoForm.getCurrentTerm().getEndDate()==null){
                adminInfoForm.setErrMsg("请填写开始时间以及完成时间！");
                return this.showDialog("adminErrDialog", true, adminInfoForm);
            }
            if (!adminService.addSession(
                    taConverter.termToDataObject(
                            adminInfoForm.getCurrentTerm()
                    )
            )
                    ) {
                // TODO: 2016/10/29 弹出错误提示，具体错误信息待补充
                adminInfoForm.setErrMsg("添加失败(修改为错误提示)");
                return this.showDialog("adminErrDialog", true, adminInfoForm);
            }
        }
        return this.getTermManagePage(form);
    }

    /**
     * 删除批次
     * pageTermManagement
     *
     * @param form
     * @return
     */
    @RequestMapping(params = {"methodToCall=deleteTermCategory"})
    public ModelAndView deleteTermCategory(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);

        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(adminInfoForm, true);
        int index = params.getSelectedLineIndex();

        TermManagerViewObject termManagerViewObject = adminInfoForm.getAllTerms().get(index);

        String termName = termManagerViewObject.getTermName();
        String year = termName.substring(0, termName.indexOf("年"));
        String term = termName.substring(termName.indexOf("年") + 1, termName.indexOf("季"));
        if (adminService.removeTermByYearAndTerm(year,
                term)) {
            return this.getTermManagePage(form);
        } else {
            // TODO: 2016/10/29 弹出错误提示，具体错误信息待补充
            adminInfoForm.setErrMsg("删除失败(修改为错误提示)");
            return this.showDialog("adminErrDialog", true, adminInfoForm);
        }
    }


    @RequestMapping(params = {"methodToCall=setCurrentSession"})
    public ModelAndView setCurrentSession(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);

        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(adminInfoForm, true);
        int index = params.getSelectedLineIndex();

        TermManagerViewObject termManagerViewObject = adminInfoForm.getAllTerms().get(index);

        String termName = termManagerViewObject.getTermName();
        String year = termName.substring(0, termName.indexOf("年"));
        String term = termName.substring(termName.indexOf("年") + 1, termName.indexOf("季"));
        if (adminService.setCurrentSession(year, term)) {
            return this.getTermManagePage(form);
        } else {
            // TODO: 2016/10/29 弹出错误提示，具体错误信息待补充
            adminInfoForm.setErrMsg("设置失败(修改为错误提示)");
            return this.showDialog("adminErrDialog", true, adminInfoForm);
        }
    }

    /**
     * 获取助教酬劳管理页面
     * 127.0.0.1:8080/tams/portal/admin?methodToCall=getTaRewardPage&viewId=AdminView
     *
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getTaRewardPage")
    public ModelAndView getTaRewardPage(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);

        adminInfoForm.setAllTaCategories(
                adminService.getAllTaCategories()
        );

        return this.getModelAndView(adminInfoForm, "pageTaReward");
    }

    /**
     * 修改助教酬劳
     *
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=selectTaReward")
    public ModelAndView selectTaReward(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(adminInfoForm, true);
        int index = params.getSelectedLineIndex();
        adminInfoForm.setOldTaCategory(adminInfoForm.getAllTaCategories().get(index));
        adminInfoForm.setTaIndex(index);
        return this.showDialog("editTaRewardDialog", true, adminInfoForm);
    }

    /**
     * 修改助教酬劳
     *
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=saveTaReward")
    public ModelAndView saveTaReward(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);

        TAMSTaCategory newTaReward = adminInfoForm.getOldTaCategory();
        if (!adminService.changeTaCategoryByEntiy(adminInfoForm.getOldTaCategory())) {
            // TODO: 2016/11/8 弹出错误提示，具体错误信息待补充
            adminInfoForm.setErrMsg("编辑助教类别失败(修改为错误提示)");
            return this.showDialog("adminErrDialog", true, adminInfoForm);
        }
        return this.getTaRewardPage(form);
    }

    /**
     * 删除助教酬劳
     *
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=deleteTaReward")
    public ModelAndView deleteTaReward(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);

        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(adminInfoForm, true);
        int index = params.getSelectedLineIndex();
        new TAMSTaCategoryDaoJpa().deleteOneByEntity(adminInfoForm.getAllTaCategories().get(index));
        return this.getModelAndView(form, "pageTaReward");
    }


    /**
     * 按条件选取学院历史经费
     */
    @RequestMapping(params = "methodToCall=searchPreDeptFundingByCondition")
    public ModelAndView searchPreDeptFundingByCondition(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
/*
        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        String uId = userSession.getLoggedInUserPrincipalId();*/
        Map<String, String> conditions = new HashMap<>();

        //put conditions

        User user = (User) getUserSession().retrieveObject("user");
        String uId = user.getCode();
        if(userInfoService.isSysAdmin(uId) || userInfoService.isAcademicAffairsStaff(uId)){
            conditions.put("deptId", infoForm.getDeptId());  //选择学院
        }
        else if(userInfoService.isCollegeStaff(uId)) {
            conditions.put("deptId", user.getDepartmentId().toString());
        }
        conditions.put("dTimes", infoForm.getdTimes());  //选择批次
        //文字搜索
        conditions.put("dPreFunds", infoForm.getdPreFunds());
        conditions.put("dApplyFunds", infoForm.getdApplyFunds());
        conditions.put("dApprovalFunds", infoForm.getdApprovalFunds());
        conditions.put("dAddingFunds", infoForm.getdAddingFunds());
        conditions.put("dRewardFunds", infoForm.getsRewardFunds());
        conditions.put("dTotalFunds", infoForm.getdTotalFunds());
        conditions.put("dTrafficFunds", infoForm.getdTrafficFunds());

        infoForm.setDepartmentPreFundings(
                taConverter.departmentFundingToViewObject(
                        adminService.getDepartmentPreFundingByCondition(conditions)
                )
        );

        //清除table页面信息缓存
        Map map = new HashMap();
        map.putAll(infoForm.getViewPostMetadata().getComponentPostMetadataMap().get("FundsManagerDeptTable").getData());
        map.put("displayStart",0);
        infoForm.getViewPostMetadata().getComponentPostMetadataMap().get("FundsManagerDeptTable").setData(map);

        return this.getModelAndView(infoForm, "pageFundsManagement");
    }


    /**
     * 获取工作流程管理页面
     * 127.0.0.1:8080/tams/portal/admin?methodToCall=getWorkFlowManagePage&viewId=AdminView
     *
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getWorkFlowManagePage")
    public ModelAndView getWorkFlowManagePage(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);

        Gson gson = new Gson();

        RelationTable rt = new RelationTable("default");

        String json = gson.toJson(rt);

        infoForm.setWorkflowRelationTable(json);

        return this.getModelAndView(infoForm, "pageWorkFlowManage");
    }

    /**
     * 工作流查询
     *
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=search")
    public ModelAndView search(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);

        RelationTable rt = taConverter.workflowStatusRtoJson(
                infoForm.getFunctionId(),
                adminService.getWorkflowStatusRelationByRoleFunctionId(
                        adminService.getRoleFunctionIdByRoleIdAndFunctionId(
                                infoForm.getRoleId(),
                                infoForm.getFunctionId()
                        )
                )
        );

        Gson gson = new Gson();

        String json = gson.toJson(rt);

        infoForm.setWorkflowRelationTable(json);

        return this.getModelAndView(infoForm, "pageWorkFlowManage");
    }

    /**
     * 工作流保存
     *
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=save")
    public ModelAndView save(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);

        String json = infoForm.getWorkflowRelationTable();
        Gson gson = new Gson();
        RelationTable rt = gson.fromJson(json, RelationTable.class);

        String rfId = adminService.setRoleFunctionIdByRoleIdAndFunctionId(infoForm.getRoleId(), infoForm.getFunctionId());

        adminService.setWorkflowStatusRelationByRoleFunctionId(infoForm.getFunctionId(), rfId, rt);

        return this.getModelAndView(infoForm, "pageWorkFlowManage");
    }


    /**
     * 经费页面每一行编辑过后就调用此方法将数据存储到草稿中
     * 经费页面存在多个tab(学校、学院、教学班)，通过curTabFlag来进行区分
     * 存储数据之后，需要及时更新页面上的'金额总计'属性
     *
     * @param form
     * @return 更新成功时返回pageFundsManagement页面, 失败时弹出errDialog。
     */
    @RequestMapping(params = "methodToCall=savaFundsDraft")
    public ModelAndView savaFundsDraft(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);

        try {
            CollectionControllerServiceImpl.CollectionActionParameters params =
                    new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
            int index = params.getSelectedLineIndex();

            String curTabFlag = infoForm.getCurTabFlag();
            switch (curTabFlag) {
                case AdminInfoForm.TAB_FLAG_SCHOOL:
                    SessionFundingViewObject curSchoolFundObj = infoForm.getPreviousSessionFundings().get(index);
                    // TODO: 2016/11/25 把这个对象存到数据库的draft中。

                    // TODO: 2016/11/25 重新计算该tab的经费总计。
                    break;
                case AdminInfoForm.TAB_FLAG_DEPARTMENT:
                    DepartmentFundingViewObject curDepartFundObj = infoForm.getDepartmentCurrFundings().get(index);
                    System.out.println(curDepartFundObj.getPlanFunding());
                    // TODO: 2016/11/25 把这个对象存到数据库的draft中。

                    // TODO: 2016/11/25 重新计算该tab的经费总计。
                    break;
                case AdminInfoForm.TAB_FLAG_CLASS:

                    break;
                default:
                    // TODO: 2016/11/25 return error
                    break;
            }

        } catch (Exception e) {
            // TODO: 2016/11/25 return error
        }

        return this.getModelAndView(infoForm, "pageFundsManagement");
    }


    /**
     * 同步数据
     *
     * @return
     */
    @RequestMapping(params = "methodToCall=syncInfo")
    public ModelAndView syncInfo(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        baseStart(infoForm);

        String[] syncInfo = infoForm.getCheckboxesTest().split(",");

        String hostType = infoForm.getSystemHostType();

        String hostIp = infoForm.getSystemHostIP();
        if (hostIp == null) {
            hostIp = "";
        } else {
            hostIp = hostIp.trim();
        }

        String hostPort = infoForm.getSystemHostPort();
        if (hostPort == null) {
            hostPort = "";
        } else {
            hostPort = hostPort.trim();
        }

        String dbName = infoForm.getSystemDbName();
        if (dbName == null) {
            dbName = "";
        } else {
            dbName = dbName.trim();
        }
        String dbUserName = infoForm.getSystemDbUserName();
        if (dbUserName == null) {
            dbUserName = "";
        } else {
            dbUserName = dbUserName.trim();
        }

        String dbPassWd = infoForm.getSystemDbPassword();
        if (dbPassWd == null) {
            dbPassWd = "";
        } else {
            dbPassWd = dbPassWd.trim();
        }

        infoForm.getCheckboxesTest();


        Connection con = null;
        try {
            con = syncInfoService.getConnection(hostType, hostIp, hostPort, dbName, dbUserName, dbPassWd, syncInfo);
        } catch (SQLException | ClassNotFoundException e) {
            infoForm.setConnectMessage(e.getMessage());
            e.printStackTrace();
            return this.getModelAndView(infoForm, "pageSyncInfo");
        } finally {
            if (con != null)
                try {
                    syncInfoService.closeConnection(con);
                } catch (SQLException e) {
                    infoForm.setConnectMessage(e.getMessage());
                    e.printStackTrace();
                    return this.getModelAndView(form, "pageSyncInfo");
                }
        }
        return this.getModelAndView(infoForm, "pageSyncInfo");
    }

    @RequestMapping(params = {"methodToCall=selectCurSession"})
    public ModelAndView selectCurSession(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        super.baseStart(infoForm);
        utSessionDao.setCurrentSession(utSessionDao.getUTSessionById(Integer.parseInt(infoForm.getSessionTermFinder())));
        request.getParameterMap().get("pageId");
        return this.getModelAndView(infoForm, infoForm.getPageId());
    }

    @Override
    protected UifFormBase createInitialForm() {

        return new AdminInfoForm();
    }

    /**
     * http://127.0.0.1:8080/tams/portal/admin?methodToCall=getManageStationPage&viewId=AdminView
     *
     * @param form
     * @return 管理台页面
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=getManageStationPage")
    public ModelAndView getManageStationPage(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);

        return this.getModelAndView(adminInfoForm, "pageManageStation");
    }


    @RequestMapping(params = "methodToCall=showTravelSubsidy")
    public ModelAndView showTravelSubsidy(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);

        String indexOFTaFunds = adminInfoForm.getIndexFundsManagerAssistantTable();
        TaFundingViewObject taFundingViewObject = adminInfoForm.getTaFunding().get(Integer.parseInt(indexOFTaFunds));
        if(taFundingViewObject!=null){
            String taId = taFundingViewObject.getStuId();
            String classId = taFundingViewObject.getClassId();
            List<TAMSTaTravelSubsidy> result = tamsTaTravelSubsidyDao.getTAMSTaTravelSubsidyByStuIdAndTaId(taId,classId);
            if(result==null||result.size()==0){
                List<TAMSTaTravelSubsidy> emptyList = new ArrayList<>();
                emptyList.add(new TAMSTaTravelSubsidy());
                adminInfoForm.setTravelSubsidies(emptyList);
            }else {
                adminInfoForm.setTravelSubsidies(result);
            }
        }
        return this.showDialog("confirmTrafficDialog",true,adminInfoForm);
    }

    /**
     * http://127.0.0.1:8080/tams/portal/admin?methodToCall=getBlackListPage&viewId=AdminView
     *
     * @param form
     * @return 黑名单
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=getBlackListPage")
    public ModelAndView getBlackListPage(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        super.baseStart(adminInfoForm);
        adminInfoForm.setTaBlackList(adminConverter.blackListToViewObject(adminService.getAllBlackList()));
        return this.getModelAndView(adminInfoForm, "pageBlackList");
    }

}