package cn.edu.cqu.ngtl.controller.classmanagement;

import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.form.classmanagement.ClassInfoForm;
import cn.edu.cqu.ngtl.service.classservice.IClassInfoService;
import cn.edu.cqu.ngtl.service.common.impl.ExcelServiceImpl;
import cn.edu.cqu.ngtl.service.riceservice.ITAConverter;
import cn.edu.cqu.ngtl.service.taservice.ITAService;
import cn.edu.cqu.ngtl.viewobject.classinfo.ApplyViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassDetailInfoViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassTeacherViewObject;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.GlobalVariables;
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
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tangjing on 16-10-20.
 * 课程管理相关的view及function
 */
@Controller
@RequestMapping("/class")
public class ClassController extends UifControllerBase {


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
     * http://127.0.0.1:8080/tams/portal/class?methodToCall=getClassListPage&viewId=ClassView
     **/
    @RequestMapping(params = "methodToCall=getClassListPage")
    public ModelAndView getClassListPage(@ModelAttribute("KualiForm") UifFormBase form,
                                         HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;

        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        String uId = userSession.getLoggedInUserPrincipalId();
        infoForm.setClassList(
                taConverter.classInfoToViewObject(
                        classInfoService.getAllClassesFilterByUid(uId)
                )
        );
        return this.getModelAndView(infoForm, "pageClassList");
    }

    /**
     * pageId限定了只接受来自pageClassList的请求
     * 从classlist跳转到某个class对应的classInfoPage
     *
     * @param form
     * @return
     */
    @RequestMapping(params = {"pageId=pageClassList", "methodToCall=getClassInfoPage"})
    public ModelAndView getClassInfoPage(@ModelAttribute("KualiForm") UifFormBase form) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
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
            Integer id = classObject.getId();

            UTClass utClass = classInfoService.getClassInfoById(classObject.getId());

            ClassDetailInfoViewObject detailInfoViewObject = taConverter.classInfoToViewObject(
                    utClass
            );

            infoForm.setDetailInfoViewObject(detailInfoViewObject);

        } catch (Exception e) {

        }
        return this.getModelAndView(infoForm, "pageClassInfo");
    }

    /**
     * http://127.0.0.1:8080/tams/portal/class?methodToCall=getApplyTAPage&viewId=ClassView
     **/
    @RequestMapping(params = "methodToCall=getApplyTAPage")
    public ModelAndView getApplyTAPage(@ModelAttribute("KualiForm") UifFormBase form,
                                       HttpServletRequest request) {
        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        String stuId = userSession.getLoggedInUserPrincipalId();
        //FIXME  不能写死
        stuId = "20131840";

        Integer classId = 290739;

        ClassInfoForm infoForm = (ClassInfoForm) form;

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

        taService.submitApplicationAssistant(taConverter.submitInfoToTaApplication(infoForm));

        return null;
    }

    /**
     * 根据条件查询班级列表
     * @param form
     * @return
     */

    @RequestMapping(params = "methodToCall=searchClassByCondition")
    public ModelAndView searchClassByCondition(@ModelAttribute("KualiForm") UifFormBase form,
                                               HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;

        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        String uId = userSession.getLoggedInUserPrincipalId();
        Map<String, String> conditions = new HashMap<>();

        //put conditions
        conditions.put("ClassNumber", infoForm.getCondClassNumber());
        conditions.put("DepartmentId", infoForm.getCondDepartmentName());
        conditions.put("InstructorName", infoForm.getCondInstructorName());
//        conditions.put("Year", infoForm.getCondSessionYear());
//        conditions.put("IsRequired", infoForm.getCondIsRequired());
//        conditions.put("ProgramId", infoForm.getCondProgramName());
//        conditions.put("Classification", infoForm.getCondCourseClassification());
        conditions.put("CourseName", infoForm.getCondCourseName());
        conditions.put("CourseCode", infoForm.getCondCourseCode());
//        conditions.put("CourseHour", infoForm.getCondCourseHour());
//        conditions.put("CourseCredit", infoForm.getCondCourseCredit());

        infoForm.setClassList(
                taConverter.classInfoToViewObject(
                        classInfoService.getAllClassesFilterByUidAndCondition(uId, conditions)
                )
        );

        return this.getModelAndView(infoForm, "pageClassList");
    }


    /**
     * http://127.0.0.1:8080/tams/portal/class?methodToCall=getRequestTaPage&viewId=ClassView
     **/
    @RequestMapping(params = "methodToCall=getRequestTaPage")
    public ModelAndView getRequestTaPage(@ModelAttribute("KualiForm") UifFormBase form,
                                       HttpServletRequest request) {

        ClassInfoForm infoForm = (ClassInfoForm) form;

        infoForm.setApplyViewObject(new ApplyViewObject());
        infoForm.setClassList(
                taConverter.classInfoToViewObject(
                        classInfoService.getAllClassesMappedByDepartment()
                )
        );

        return this.getModelAndView(infoForm, "pageRequestTa");
    }

    @RequestMapping(params = "methodToCall=submitRequestTaPage")
    public ModelAndView submitRequestTaPage(@ModelAttribute("KualiForm") UifFormBase form,
                                         HttpServletRequest request) {

        ClassInfoForm infoForm = (ClassInfoForm) form;

        List<ClassTeacherViewObject> classList=infoForm.getClassList();


        return this.getModelAndView(infoForm, "pageRequestTa");
    }




//     * pageId限定了只接受来自pageClassList的请求
//     * 从classlist跳转到某个class对应的talss
//     * @param form
//     * @return
//     */
//    @RequestMapping(params = {"pageId=pageClassList", "methodToCall=getTaskListPage"})
//    public ModelAndView getTaskListPage(@ModelAttribute("KualiForm") UifFormBase form) {
//        ClassInfoForm infoForm = (ClassInfoForm) form;
//
//        try {
//            CollectionControllerServiceImpl.CollectionActionParameters params =
//                    new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
//            int index = params.getSelectedLineIndex();
//
////            TestObject object = infoForm.getClassList().get(index);
//        } catch (Exception e) {
//
//        }
//
//        return this.getModelAndView(infoForm, "pageTaskList");
//    }

    // endregion


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
                                             HttpServletResponse response) throws Exception {
        ClassInfoForm infoForm = (ClassInfoForm) form;


        if (infoForm.getClassList() == null) {
            // TODO: 2016/10/21 错误处理
//            examForm.setErrMsg("导出内容为空");
            return this.showDialog("errWarnDialog", true, infoForm);
        }

        List<ClassTeacherViewObject> classList = infoForm.getClassList();
        String fileName = "教学班列表" + "-" + GlobalVariables.getUserSession().getLoggedInUserPrincipalId() + "-" + System.currentTimeMillis() + ".xls";

        String filePath = new ExcelServiceImpl().printClasslistExcel(classList, "exportfolder", fileName, "2003");
        String baseUrl = CoreApiServiceLocator.getKualiConfigurationService()
                .getPropertyValueAsString(KRADConstants.ConfigParameters.APPLICATION_URL);

        return this.performRedirect(infoForm, baseUrl + File.separator + filePath);
    }


    @Override
    protected UifFormBase createInitialForm() {
        return new ClassInfoForm();
    }

    @RequestMapping(params = "methodToCall=getTAInfoPage")
    public ModelAndView getTAInfoPage(@ModelAttribute("KualiForm") UifFormBase form) {
        //TODO
        return null;
    }

}
