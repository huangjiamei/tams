package cn.edu.cqu.ngtl.controller.adminmanagement;

import cn.edu.cqu.ngtl.dao.krim.KRIM_ROLE_T_Dao;
import cn.edu.cqu.ngtl.dao.krim.impl.*;
import cn.edu.cqu.ngtl.dao.ut.impl.UTInstructorDaoJpa;
import cn.edu.cqu.ngtl.dataobject.krim.*;
import cn.edu.cqu.ngtl.dataobject.ut.UTInstructor;
import cn.edu.cqu.ngtl.form.adminmanagement.AdminInfoForm;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krad.web.service.impl.CollectionControllerServiceImpl.CollectionActionParameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by awake on 2016-10-21.
 */
@Controller
@RequestMapping("/admin")
public class adminController extends UifControllerBase {

    /**
     * 127.0.0.1:8080/tams/portal/admin?methodToCall=getConsolePage&viewId=AdminView
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getConsolePage")
    public ModelAndView getConsolePage(@ModelAttribute("KualiForm") UifFormBase form) {
        AdminInfoForm infoForm= (AdminInfoForm) form;

        return this.getModelAndView(infoForm, "pageConsole");
    }

    /**
     * http://127.0.0.1:8080/tams/portal/admin?methodToCall=getRoleManagerPage&viewId=AdminView
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=getRoleManagerPage")
    public ModelAndView getRoleManagerPage(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        AdminInfoForm infoForm = (AdminInfoForm) form;
        String formKeyParam = request.getParameter(UifParameters.FORM_KEY);
        infoForm.setRMPkrimRoleTs(new KRIM_ROLE_T_DaoJpa().getAllKrimRoleTs());

        return this.getModelAndView(infoForm, "pageRoleManager");
    }


    /**
     * http://127.0.0.1:8080/tams/portal/admin?methodToCall=getUserRoleManagerPage&viewId=AdminView
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=getUserRoleManagerPage")
    public ModelAndView getUserRoleManagerPage(@ModelAttribute("KualiForm") UifFormBase form,
                                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        AdminInfoForm infoForm = (AdminInfoForm) form;
//        List<UTDepartment> utDepartment = new UTDepartmentDaoJpa().getAllUTDepartments();
//        for(UTDepartment utDet:utDepartment){
        infoForm.setURMutInstructors(new UTInstructorDaoJpa().getAllInstructors());
//        }

        return this.getModelAndView(infoForm, "pageUserRoleManager");
    }

    /**
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

        CollectionActionParameters params = new CollectionActionParameters(infoForm, true);
        int index = params.getSelectedLineIndex();
        KRIM_ROLE_T krimRoleT = krimRoleTs.get(index);

        List<KRIM_ROLE_PERM_T> krimRolePermTs = new ArrayList<KRIM_ROLE_PERM_T>(
                new KRIM_ROLE_PERM_T_DaoJpa().getKrimRolePermTsByRole(krimRoleT));
        List<KRIM_PERM_T> krimPermTs = new ArrayList<KRIM_PERM_T>(new KRIM_PERM_T_DaoJpa().getAllPermissions());

        for (KRIM_ROLE_PERM_T krimRolePermT : krimRolePermTs) {
            KRIM_PERM_T tempKrimPermT = krimRolePermT.getKrimPermT();
            if (krimPermTs.contains(tempKrimPermT)) {
                tempKrimPermT.setChecked(true);
                krimPermTs.set(krimPermTs.indexOf(tempKrimPermT), tempKrimPermT);
            }
        }

        infoForm.setRMPkrimRoleT(krimRoleT);
        infoForm.setRMPkrimPermTs(krimPermTs);
        System.out.println(infoForm.isUpdateComponentRequest());
        return this.getModelAndView(infoForm, "pageUpdateRole");
    }

    @RequestMapping(params = "methodToCall=addExmKrimRole")
    public ModelAndView addExmKrimRole(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        AdminInfoForm infoForm = (AdminInfoForm) form;

        infoForm.setRMPkrimRoleT(null);
        infoForm.setRMPkrimPermTs(new KRIM_PERM_T_DaoJpa().getAllPermissions());

        return this.getModelAndView(infoForm, "pageUpdateRole");
    }

    @RequestMapping(params = "methodToCall=saveExmKrimRole")
    public ModelAndView saveExmKrimRole(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        AdminInfoForm infoForm = (AdminInfoForm) form;

        KRIM_ROLE_T_Dao krimRoleTDao = new KRIM_ROLE_T_DaoJpa();
        List<KRIM_ROLE_T> krimRoleTs = new ArrayList<KRIM_ROLE_T>(infoForm.getRMPkrimRoleTs());

        KRIM_ROLE_T krimRoleT = infoForm.getRMPkrimRoleT();

        if (krimRoleT == null) {
            infoForm.setErrMsg("角色为空");
            return this.showDialog("errWarnDialog", true, infoForm);
        }
        if (krimRoleT.getName() == null) {
            infoForm.setErrMsg("角色名称不能为空");
            return this.showDialog("errWarnDialog", true, infoForm);
        }
        KRIM_ROLE_T checkKrimRoleT = krimRoleTDao.getKrimRoleTByName(krimRoleT.getName());
        if (checkKrimRoleT != null) {
            if (krimRoleT.getId() == null || !krimRoleT.getId().equals(checkKrimRoleT.getId())) {
                infoForm.setErrMsg("角色名称已存在");
                return this.showDialog("errWarnDialog", true, infoForm);
            }
        }
        krimRoleT = krimRoleTDao.saveKrimRoleT(krimRoleT);
        infoForm.setRMPkrimRoleT(krimRoleT);

        List<KRIM_PERM_T> krimPermTs = infoForm.getRMPkrimPermTs();
        new KRIM_ROLE_PERM_T_DaoJpa().saveKrimRolePermTByRoleAndPerms(krimRoleT, krimPermTs);

        infoForm.setRMPkrimRoleTs(new KRIM_ROLE_T_DaoJpa().getAllKrimRoleTs());
        return this.getModelAndView(infoForm, "pageUpdateRole");
    }


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
            return this.showDialog("errWarnDialog", true, infoForm);
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

    @Override
    protected UifFormBase createInitialForm() {

        return new AdminInfoForm();
    }


}