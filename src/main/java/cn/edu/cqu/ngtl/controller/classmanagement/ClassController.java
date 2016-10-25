package cn.edu.cqu.ngtl.controller.classmanagement;

import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.form.TestForm;
import cn.edu.cqu.ngtl.form.classmanagement.ClassInfoForm;
import cn.edu.cqu.ngtl.service.classservice.IClassInfoService;
import cn.edu.cqu.ngtl.service.common.impl.ExcelServiceImpl;
import cn.edu.cqu.ngtl.service.riceservice.ITAConverter;
import cn.edu.cqu.ngtl.service.taservice.ITAService;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassDetailInfoViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassTeacherViewObject;
import org.kuali.rice.core.api.CoreApiServiceLocator;
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
import java.util.List;

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

    /**
     * http://127.0.0.1:8080/tams/portal/class?methodToCall=getClassListPage&viewId=ClassView
     **/
    @RequestMapping(params = "methodToCall=getClassListPage")
    public ModelAndView getClassListPage(@ModelAttribute("KualiForm") UifFormBase form) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        infoForm.setClassList(
                taConverter.classInfoToViewObject(
                        classInfoService.getAllClassesMappedByDepartment()
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


    // region # 暂时不可用的方法

    @RequestMapping(params = "methodToCall=getTaskDetailPage")
    public ModelAndView getTaskDetailPage(@ModelAttribute("KualiForm") UifFormBase form) {
        TestForm testForm = (TestForm) form;
        return this.getModelAndView(testForm, "pageTaskDetail");
    }

    @RequestMapping(params = "methodToCall=getAddTaskPage")
    public ModelAndView getAddTaskPage(@ModelAttribute("KualiForm") UifFormBase form) {
        TestForm testForm = (TestForm) form;
        return this.getModelAndView(testForm, "pageAddNewTask");
    }

    /**
     * http://127.0.0.1:8080/tams/portal/class?methodToCall=getApplyTAPage&viewId=ClassView
     **/
    @RequestMapping(params = "methodToCall=getApplyTAPage")
    public ModelAndView getApplyTAPage(@ModelAttribute("KualiForm") UifFormBase form,
                                       HttpServletRequest request) {
        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        String stuId = userSession.getLoggedInUserPrincipalId();
        //TODO
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
     * http://127.0.0.1:8080/tams/portal/class?methodToCall=pageDeclareClass&viewId=ClassView
     **/
    @RequestMapping(params = "methodToCall=pageDeclareClass")
    public ModelAndView pageDeclareClass(@ModelAttribute("KualiForm") UifFormBase form,
                                       HttpServletRequest request) {

        ClassInfoForm infoForm = (ClassInfoForm) form;

        return this.getModelAndView(infoForm, "pageDeclareClass");
    }

    //    /**
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
