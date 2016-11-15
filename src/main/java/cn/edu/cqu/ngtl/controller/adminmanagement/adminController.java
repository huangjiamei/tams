package cn.edu.cqu.ngtl.controller.adminmanagement;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dao.krim.KRIM_ROLE_T_Dao;
import cn.edu.cqu.ngtl.dao.krim.impl.*;
import cn.edu.cqu.ngtl.dao.tams.impl.TAMSCourseManagerDaoJpa;
import cn.edu.cqu.ngtl.dao.tams.impl.TAMSTaCategoryDaoJpa;
import cn.edu.cqu.ngtl.dao.ut.impl.UTInstructorDaoJpa;
import cn.edu.cqu.ngtl.dataobject.cm.CMCourseClassification;
import cn.edu.cqu.ngtl.dataobject.krim.*;
import cn.edu.cqu.ngtl.dataobject.tams.*;
import cn.edu.cqu.ngtl.dataobject.ut.UTInstructor;
import cn.edu.cqu.ngtl.form.adminmanagement.AdminInfoForm;
import cn.edu.cqu.ngtl.service.adminservice.IAdminService;
import cn.edu.cqu.ngtl.service.riceservice.ITAConverter;
import cn.edu.cqu.ngtl.service.riceservice.impl.AdminConverterimpl;
import cn.edu.cqu.ngtl.viewobject.adminInfo.CourseManagerViewObject;
import cn.edu.cqu.ngtl.viewobject.adminInfo.PieChartsNameValuePair;
import cn.edu.cqu.ngtl.viewobject.adminInfo.RelationTable;
import cn.edu.cqu.ngtl.viewobject.adminInfo.TermManagerViewObject;
import com.google.gson.Gson;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by awake on 2016-10-21.
 */
@Controller
@RequestMapping("/admin")
public class adminController extends UifControllerBase {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private TAMSCourseManagerDaoJpa tamsCourseManagerDaoJpa;

    @Autowired
    private ITAConverter taConverter;


    @RequestMapping(params = "methodToCall=logout")
    public ModelAndView logout(@ModelAttribute("KualiForm") UifFormBase form) throws Exception {
        String redirctURL = ConfigContext.getCurrentContextConfig().getProperty(KRADConstants.APPLICATION_URL_KEY) + "/portal/home?methodToCall=logout&viewId=PortalView";
        return this.performRedirect(form, redirctURL);
    }

    /**
     * 127.0.0.1:8080/tams/portal/admin?methodToCall=getConsolePage&viewId=AdminView
     *
     * @param form
     * @return
     */
    /**权限控制Start**/
    @RequestMapping(params = "methodToCall=getConsolePage")
    public ModelAndView getConsolePage(@ModelAttribute("KualiForm") UifFormBase form) {
        if(new cn.edu.cqu.ngtl.service.userservice.impl.UserInfoServiceImpl().hasPermission((User) GlobalVariables.getUserSession().retrieveObject("user"),"ViewConsolePage")) {
            AdminInfoForm infoForm = (AdminInfoForm) form;
            return this.getModelAndView(infoForm, "pageConsole");
        }
        StringBuilder redirectUrl = new StringBuilder(ConfigContext.getCurrentContextConfig().getProperty(KRADConstants.APPLICATION_URL_KEY));
        return this.performRedirect(form, redirectUrl.toString());
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
        List<KRIM_PERM_T> krimPermTs = new ArrayList<KRIM_PERM_T>(new KRIM_PERM_T_DaoJpa().getAllPermissions());
        infoForm.setRMPkrimPermTs(krimPermTs);

        return this.getModelAndView(infoForm, "pagePermissionManagement");
    }


    //TODO 新增和删除对话框的实例  START
    /**
     * 新增权限对话框   新增的对话框将属性设为空值再直接弹对话框
     * @param form
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=addPermissionDialog")
    public ModelAndView addPermissionDialog(@ModelAttribute("KualiForm") UifFormBase form) throws Exception {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        infoForm.setPermissionNM("");
        infoForm.setPermissionContent("");
        infoForm.setPermissionIndex(null);
        return this.showDialog("savePermissionDialog",true,infoForm);
    }

    /**
     * 更新的对话框需要取到当前需要修改的对象，然后将该对象中的值赋予对话框中的相应字段中。然后再弹对话框
     * PS:务必将选择对象的index放入到form中（或者用其他方法记录下来）
     * @param form
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=updateKrimPerm")
    public ModelAndView updateKrimPerm(@ModelAttribute("KualiForm") UifFormBase form) throws Exception {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        CollectionActionParameters params = new CollectionActionParameters(infoForm, true);
        int index = params.getSelectedLineIndex();
        KRIM_PERM_T selectedKrim_perm_t = infoForm.getRMPkrimPermTs().get(index);
        KRIM_PERM_T krim_perm_t = new KRIM_PERM_T();
        infoForm.setPermissionNM(selectedKrim_perm_t.getName());         //给dialog中的字段赋值
        infoForm.setPermissionContent(selectedKrim_perm_t.getDescription());
        infoForm.setPermissionStatus(selectedKrim_perm_t.getActive());
        infoForm.setPermissionIndex(index);                  //记录index
        return this.showDialog("savePermissionDialog",true,infoForm);
    }

    /**
     * 保存/新增权限二合一方法
     * 根据index的值去判断页面执行的是更新还是新增操作。index如果值是null，则页面是进行的新增操作；反之则是进行的更新操作
     * PS:数据库操作结束之后务必将更新的对象重新放入到页面的显示对象中
     * @param form
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=savePermission")
    public ModelAndView savePermission(@ModelAttribute("KualiForm") UifFormBase form) throws Exception {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        KRIM_PERM_T krimPermTs = new KRIM_PERM_T();
        if(infoForm.getPermissionIndex()!=null){
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

        if(new KRIM_PERM_T_DaoJpa().findPermissionByName(permissionNM)!=null){
            infoForm.setErrMsg("已存在相同权限内容的权限");
            return this.showDialog("adminErrDialog",true,infoForm);
        }
        new KRIM_PERM_T_DaoJpa().addPermissions(krimPermTs);
        if(infoForm.getPermissionIndex()==null) {
            infoForm.getRMPkrimPermTs().add(krimPermTs);   //根据不同的操作对页面显示对象进行修改
        }else{
            infoForm.getRMPkrimPermTs().set((infoForm.getPermissionIndex()),krimPermTs);
        }
        return this.getModelAndView(infoForm, "pagePermissionManagement");
    }

    //TODO 新增和删除对话框的实例  END

    /**
     * 删除权限
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
        KRIM_ROLE_T_Dao krimRoleTDao = new KRIM_ROLE_T_DaoJpa();
        List<KRIM_ROLE_T> krimRoleTs = new ArrayList<KRIM_ROLE_T>(infoForm.getRMPkrimRoleTs());
        KRIM_ROLE_T krimRoleT = infoForm.getRMPkrimRoleT();
        if (krimRoleT == null) {
            infoForm.setErrMsg("角色为空");
            return this.showDialog("adminErrDialog", true, infoForm);
        }
        if (krimRoleT.getName() == null) {
            infoForm.setErrMsg("角色名称不能为空");
            return this.showDialog("adminErrDialog", true, infoForm);
        }
        KRIM_ROLE_T checkKrimRoleT = krimRoleTDao.getKrimRoleTByName(krimRoleT.getName());
        if (checkKrimRoleT != null) {
            if (krimRoleT.getId() == null || !krimRoleT.getId().equals(checkKrimRoleT.getId())) {
                infoForm.setErrMsg("角色名称已存在");
                return this.showDialog("adminErrDialog", true, infoForm);
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

        CollectionActionParameters params = new CollectionActionParameters(infoForm, true);
        int index = params.getSelectedLineIndex();

        List<UTInstructor> utInstructors = infoForm.getURMutInstructors();
        UTInstructor utInstructor = utInstructors.get(index);
        infoForm.setURMutInstructor(utInstructor);

        if (utInstructor.getIdNumber() == null) {
            infoForm.setErrMsg("该同志没有同一认证号呀");
            return this.showDialog("adminErrDialog", true, infoForm);
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
        UTInstructor utInstructor = infoForm.getURMutInstructor();
        KRIM_PRNCPL_T krimPrncplT = new KRIM_PRNCPL_T_DaoJpa().getKrimEntityEntTypTByPrncplId(utInstructor.getId());
        List<KRIM_ROLE_T> krimRoleTs = infoForm.getURMPkrimRoleTs();
        new KRIM_ROLE_MBR_T_DaoJpa().saveKrimRoleMbrTByPrncpltAndRoles(krimPrncplT, krimRoleTs);
        return this.getModelAndView(infoForm, "pageUserRoleManager");
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
        infoForm.setCourseManagerViewObjects(new AdminConverterimpl().getCourseManagerToTableViewObject(
                new TAMSCourseManagerDaoJpa().getAllCourseManager()
        ));
        return this.getModelAndView(infoForm, "pageCourseManager");
    }


    /**
     * 编辑课程负责人信息
     */
    @RequestMapping(params = {"methodToCall=updateCourseManager"})
    public ModelAndView updateCourseManager(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
        int index = params.getSelectedLineIndex();
        CourseManagerViewObject selectedObject = infoForm.getCourseManagerViewObjects().get(index);
        infoForm.setCourseManagerIndex(index);
        infoForm.setSelectedCourseManagerObject(selectedObject);
        infoForm.setCourseNm(selectedObject.getCourseNm());
        infoForm.setCourseNmb(selectedObject.getCourseNmb());
        infoForm.setCourseManager(selectedObject.getCourseManager());
        infoForm.setInstructorCode(selectedObject.getInstructorCode());
        return this.showDialog("confirmEditManagerDialog",true,infoForm);
    }

    /**
     * 更新课程负责人
     * @param form
     * @return
     */
    @RequestMapping(params = {"methodToCall=saveUpdateCourseManager"})
    public ModelAndView saveUpdateCourseManager(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        CourseManagerViewObject selectedObject = infoForm.getSelectedCourseManagerObject();
        TAMSCourseManager tamsCourseManager = tamsCourseManagerDaoJpa.getCourseManagerByInstructorId(selectedObject.getId());
        UTInstructor newManager = new UTInstructorDaoJpa().getInstructorByCode(infoForm.getInstructorCode());
        if(newManager!=null) {
            tamsCourseManager.setCourseManagerId(newManager.getId());
            tamsCourseManagerDaoJpa.saveCourseManager(tamsCourseManager);
            infoForm.getCourseManagerViewObjects().get(infoForm.getCourseManagerIndex()).setCourseManager(newManager.getName());
            infoForm.getCourseManagerViewObjects().get(infoForm.getCourseManagerIndex()).setInstructorCode(newManager.getCode());
            infoForm.getCourseManagerViewObjects().get(infoForm.getCourseManagerIndex()).setId(newManager.getId());
            return this.getModelAndView(infoForm, "pageCourseManager");
        }else{
            infoForm.setErrMsg("查无此人");
        }
        //TODO 报错页面待做
        return null;
    }


    /**
     * 删除课程负责人
     */
    @RequestMapping(params = {"methodToCall=deleteCourseManager"})
    public ModelAndView deleteCourseManager(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
        int index = params.getSelectedLineIndex();
        CourseManagerViewObject selectedObject = infoForm.getCourseManagerViewObjects().get(index);
        tamsCourseManagerDaoJpa.deleteCourseManager(tamsCourseManagerDaoJpa.getCourseManagerByInstructorId(selectedObject.getId()));
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
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        adminInfoForm.setAllClassifications(
                adminService.getAllClassification()
        );

        return this.getModelAndView(adminInfoForm, "pageCourseCategory");
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
        try {
            // if index is exit, then enter edit dialog
            CollectionControllerServiceImpl.CollectionActionParameters params =
                    new CollectionControllerServiceImpl.CollectionActionParameters(adminInfoForm, true);
            int index = params.getSelectedLineIndex();

            adminInfoForm.setClassification(adminInfoForm.getAllClassifications().get(index));

            return this.showDialog("editCourseCategoryDialog", true, adminInfoForm);
        }
        catch (RuntimeException e) {
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
        try {
            //存在index进入edit dialog
            CollectionControllerServiceImpl.CollectionActionParameters params =
                    new CollectionControllerServiceImpl.CollectionActionParameters(adminInfoForm, true);
            int index = params.getSelectedLineIndex();

            adminInfoForm.setOldTaCategory(adminInfoForm.getAllTaCategories().get(index));
            adminInfoForm.setTaIndex(index);

            return this.showDialog("editTaCategoryDialog", true, adminInfoForm);
        }
        catch (RuntimeException e) {
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

        if(adminInfoForm.getTaIndex()!=null){
            if(!adminService.changeTaCategoryByEntiy(adminInfoForm.getOldTaCategory())){
                // TODO: 2016/10/29 弹出错误提示，具体错误信息待补充
                adminInfoForm.setErrMsg("编辑助教类别失败(修改为错误提示)");
                return this.showDialog("adminErrDialog", true, adminInfoForm);
            }
        }else{
            if(!adminService.addTaCategory(adminInfoForm.getOldTaCategory())){
                // TODO: 2016/10/29 弹出错误提示，具体错误信息待补充
                adminInfoForm.setErrMsg("添加助教类别失败(修改为错误提示)");
                return this.showDialog("adminErrDialog", true, adminInfoForm);
            }
        }
        return this.getTaCategoryPage(form);
    }

    /**
     * 删除助教类别
     * pageCourseCategory
     * @param form
     * @return
     */
    @RequestMapping(params = {"methodToCall=deleteTaCategory"})
    public ModelAndView deleteTaCategory(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(adminInfoForm, true);
        int index = params.getSelectedLineIndex();

        TAMSTaCategory taCategory=adminInfoForm.getAllTaCategories().get(index);

        if(adminService.removeTaCategoryById(Integer.parseInt(taCategory.getId()))){
            return this.getTaCategoryPage(form);
        }
        else{
            // TODO: 2016/10/29 弹出错误提示，具体错误信息待补充
            adminInfoForm.setErrMsg("删除失败(修改为错误提示)");
            return this.showDialog("adminErrDialog", true, adminInfoForm);
        }
    }

    /**
     * 获取任务类别管理页面
     * 127.0.0.1:8080/tams/portal/admin?methodToCall=getTaskCategoryPage&viewId=AdminView
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getTaskCategoryPage")
    public ModelAndView getTaskCategoryPage(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;

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

        try {
            //存在index进入edit dialog
            CollectionControllerServiceImpl.CollectionActionParameters params =
                    new CollectionControllerServiceImpl.CollectionActionParameters(adminInfoForm, true);
            int index = params.getSelectedLineIndex();
            adminInfoForm.setIssueType(adminInfoForm.getAllIssueTypes().get((index)));
            adminInfoForm.setIssueIndex(index);
            return this.showDialog("editTaskCategoryDialog", true, adminInfoForm);
        }
        catch (RuntimeException e) {
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

        if(adminInfoForm.getIssueIndex()!=null){
            // index不为空说明要调用update
            if(!adminService.changeIssueType(adminInfoForm.getIssueType())){
                // TODO: 2016/10/29 弹出错误提示，具体错误信息待补充
                adminInfoForm.setErrMsg("编辑失败(修改为错误提示)");
                return this.showDialog("adminErrDialog", true, adminInfoForm);
            }
        }else{
            // add
            if(!adminService.addTaIssueType(adminInfoForm.getIssueType())){
                // TODO: 2016/10/29 弹出错误提示，具体错误信息待补充
                adminInfoForm.setErrMsg("添加失败(修改为错误提示)");
                return this.showDialog("adminErrDialog", true, adminInfoForm);
            }
        }
        return this.getTaskCategoryPage(form);
    }

    /**
     * 删除任务类别
     * pageTaskCategory
     * @param form
     * @return
     */
    @RequestMapping(params = {"methodToCall=deleteTaskCategory"})
    public ModelAndView deleteTaskCategory(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(adminInfoForm, true);
        int index = params.getSelectedLineIndex();

        TAMSIssueType issueType = adminInfoForm.getAllIssueTypes().get(index);

        if(adminService.removeIssueTypeById(issueType.getId())){
            return this.getTaskCategoryPage(form);
        }
        else{
            // TODO: 2016/10/29 弹出错误提示，具体错误信息待补充
            adminInfoForm.setErrMsg("删除失败(修改为错误提示)");
            return this.showDialog("adminErrDialog", true, adminInfoForm);
        }
    }

    /**
     * 获取term(学期或批次)管理页面
     * 127.0.0.1:8080/tams/portal/admin?methodToCall=getTermManagePage&viewId=AdminView
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getTermManagePage")
    public ModelAndView getTermManagePage(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;

        adminInfoForm.setAllTerms(
                taConverter.termInfoToViewObject(
                        adminService.getAllSessions()
                )
        );

        return this.getModelAndView(adminInfoForm, "pageTermManagement");
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
        }
        catch (RuntimeException e) {
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

        if(adminInfoForm.getTermIndex()!=null){
            // index不为空说明要调用update
            if(!adminService.changeSession(taConverter.termToDataObject(
                    adminInfoForm.getCurrentTerm()))){
                // TODO: 2016/10/29 弹出错误提示，具体错误信息待补充
                adminInfoForm.setErrMsg("编辑失败(修改为错误提示)");
                return this.showDialog("adminErrDialog", true, adminInfoForm);
            }
        }else{
            // add
            if(!adminService.addSession(taConverter.termToDataObject(
                    adminInfoForm.getCurrentTerm()))){
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
     * @param form
     * @return
     */
    @RequestMapping(params = {"methodToCall=deleteTermCategory"})
    public ModelAndView deleteTermCategory(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(adminInfoForm, true);
        int index = params.getSelectedLineIndex();

        TermManagerViewObject termManagerViewObject = adminInfoForm.getAllTerms().get(index);

        String termName = termManagerViewObject.getTermName();
        String year = termName.substring(0, termName.indexOf("年"));
        String term = termName.substring(termName.indexOf("年") + 1, termName.indexOf("季"));
        if(adminService.removeTermByYearAndTerm(year,
                term)){
            return this.getTermManagePage(form);
        }
        else{
            // TODO: 2016/10/29 弹出错误提示，具体错误信息待补充
            adminInfoForm.setErrMsg("删除失败(修改为错误提示)");
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

        adminInfoForm.setAllTaCategories(
                adminService.getAllTaCategories()
        );

        return this.getModelAndView(adminInfoForm, "pageTaReward");
    }

    /**
     * 修改助教酬劳
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
        return this.showDialog("editTaRewardDialog" ,true,adminInfoForm);
    }

    /**
     * 修改助教酬劳
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=saveTaReward")
    public ModelAndView saveTaReward(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        TAMSTaCategory newTaReward  = adminInfoForm.getOldTaCategory();
        if(!adminService.changeTaCategoryByEntiy(adminInfoForm.getOldTaCategory())){
            // TODO: 2016/11/8 弹出错误提示，具体错误信息待补充
            adminInfoForm.setErrMsg("编辑助教类别失败(修改为错误提示)");
            return this.showDialog("adminErrDialog", true, adminInfoForm);
        }
        return this.getTaRewardPage(form);
    }

    /**
     * 删除助教酬劳
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=deleteTaReward")
    public ModelAndView deleteTaReward(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm adminInfoForm = (AdminInfoForm) form;
        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(adminInfoForm, true);
        int index = params.getSelectedLineIndex();
        new TAMSTaCategoryDaoJpa().deleteOneByEntity(adminInfoForm.getAllTaCategories().get(index));
        return this.getModelAndView(form, "pageTaReward");
    }

    /**
     * 获取带charts的经费管理页面
     * 127.0.0.1:8080/tams/portal/admin?methodToCall=getFundsPage&viewId=AdminView
     *
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getFundsPage")
    public ModelAndView getFundsPage(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        List<PieChartsNameValuePair> list = new ArrayList<>();
        list.add(new PieChartsNameValuePair("高数", 10000));
        list.add(new PieChartsNameValuePair("线代", 5000));
        list.add(new PieChartsNameValuePair("离散", 4000));
        list.add(new PieChartsNameValuePair("数值", 2000));
        list.add(new PieChartsNameValuePair("C程", 4000));
        Gson gson = new Gson();

        String json = gson.toJson(list);

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

        infoForm.setPieChartsNameValuePairs(json);
        return this.getModelAndView(infoForm, "pageFundsManagement");
    }

    /**
     * 获取工作流程管理页面
     * 127.0.0.1:8080/tams/portal/admin?methodToCall=getWorkFlowManagePage&viewId=AdminView
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getWorkFlowManagePage")
    public ModelAndView getWorkFlowManagePage(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm = (AdminInfoForm) form;
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

        RelationTable rt = taConverter.workflowStatusRtoJson(
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

        String json = infoForm.getWorkflowRelationTable();
        Gson gson = new Gson();
        RelationTable rt = gson.fromJson(json, RelationTable.class);

        String rfId = adminService.setRoleFunctionIdByRoleIdAndFunctionId(infoForm.getRoleId(), infoForm.getFunctionId());

        adminService.setWorkflowStatusRelationByRoleFunctionId(rfId, rt);

        return this.getModelAndView(infoForm, "pageWorkFlowManage");
    }

    @Override
    protected UifFormBase createInitialForm() {

        return new AdminInfoForm();
    }

}