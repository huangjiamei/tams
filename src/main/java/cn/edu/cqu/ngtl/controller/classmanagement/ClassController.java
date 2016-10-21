package cn.edu.cqu.ngtl.controller.classmanagement;

import cn.edu.cqu.ngtl.form.TestForm;
import cn.edu.cqu.ngtl.form.classmanagement.ClassInfoForm;
import cn.edu.cqu.ngtl.service.courseservice.ICourseInfoService;
import cn.edu.cqu.ngtl.service.riceservice.ITAConverter;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krad.web.service.impl.CollectionControllerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by tangjing on 16-10-20.
 * 课程管理相关的view及function
 */
@Controller
@RequestMapping("/class")
public class ClassController extends UifControllerBase {


    @Autowired
    private ICourseInfoService courseInfoService;

    @Autowired
    private ITAConverter taConverter;

    @RequestMapping(params = "methodToCall=getClassInfoPage")
    public ModelAndView getClassInfoPage(@ModelAttribute("KualiForm") UifFormBase form) {
        TestForm testForm = (TestForm) form;
        return this.getModelAndView(testForm, "pageClassInfo");
    }

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
     *
     * http://127.0.0.1:8080/tams/portal/class?methodToCall=getClassListPage&viewId=ClassView
     *
     * **/
    @RequestMapping(params = "methodToCall=getClassListPage")
    public ModelAndView getClassListPage(@ModelAttribute("KualiForm") UifFormBase form) {
        ClassInfoForm infoForm=(ClassInfoForm) form;
        infoForm.setClassList(
                taConverter.classInfoToViewObject(
                        courseInfoService.getAllCoursesMappedByDepartment()
                )
        );
        return this.getModelAndView(infoForm, "pageClassList");
    }

    @RequestMapping(params = "methodToCall=getApplyTAPage")
    public ModelAndView getApplyTAPage(@ModelAttribute("KualiForm") UifFormBase form) {
        TestForm testForm = (TestForm) form;
        return this.getModelAndView(testForm, "pageApplyForTaForm");
    }


    // -------------------------------------------------


    /**
     * pageId限定了只接受来自pageClassList的请求
     * 从classlist跳转到某个class对应的tasklist
     * @param form
     * @return
     */
    @RequestMapping(params = {"pageId=pageClassList", "methodToCall=getTaskListPage"})
    public ModelAndView getTaskListPage(@ModelAttribute("KualiForm") UifFormBase form) {
        ClassInfoForm infoForm = (ClassInfoForm) form;

        try {
            CollectionControllerServiceImpl.CollectionActionParameters params =
                    new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
            int index = params.getSelectedLineIndex();

//            TestObject object = infoForm.getClassList().get(index);
        } catch (Exception e) {

        }

        return this.getModelAndView(infoForm, "pageTaskList");
    }


    @Override
    protected UifFormBase createInitialForm() {
        return new ClassInfoForm();
    }

    @RequestMapping(params = "methodToCall=getTAInfoPage")
    public ModelAndView getTAInfoPage(@ModelAttribute("KualiForm") UifFormBase form){
        //TODO
        return null;
    }

}
