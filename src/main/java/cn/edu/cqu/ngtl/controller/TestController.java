package cn.edu.cqu.ngtl.controller;

import cn.edu.cqu.ngtl.dataobject.TestObject;
import cn.edu.cqu.ngtl.form.TestForm;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krad.web.service.impl.CollectionControllerServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hp on 2016/10/7.
 */
@Controller
@RequestMapping("/mytest")
public class TestController extends UifControllerBase {


    @RequestMapping(params = "methodToCall=getMyInformationPage")
    public ModelAndView getMyInformationPage(@ModelAttribute("KualiForm") UifFormBase form) {
        TestForm testForm = (TestForm) form;
        return this.getModelAndView(testForm, "pageTest1");
    }


    // ------------ pages原型 --------------

    @RequestMapping(params = "methodToCall=getTeacherCoursePage")
    public ModelAndView getTeacherCoursePage(@ModelAttribute("KualiForm") UifFormBase form) {
        TestForm testForm = (TestForm) form;

        return this.getModelAndView(testForm, "pageCourseTeacher");
    }

    @RequestMapping(params = "methodToCall=getApplyTAPage")
    public ModelAndView getApplyTAPage(@ModelAttribute("KualiForm") UifFormBase form) {
        TestForm testForm = (TestForm) form;
        return this.getModelAndView(testForm, "pageApplyForTaForm");
    }

//    @RequestMapping(params = "methodToCall=getCommonHome")
//    public ModelAndView getCommonHome(@ModelAttribute("KualiForm") UifFormBase form) {
//        TestForm testForm = (TestForm) form;
//        return this.getModelAndView(testForm, "pageCommonHome");
//    }

    @RequestMapping(params = "methodToCall=getTaskListPage")
    public ModelAndView getTaskListPage(@ModelAttribute("KualiForm") UifFormBase form) {
        TestForm testForm = (TestForm) form;
        return this.getModelAndView(testForm, "pageTaskList");
    }

    @RequestMapping(params = "methodToCall=getClassInfo")
    public ModelAndView getClassInfo(@ModelAttribute("KualiForm") UifFormBase form) {
        TestForm testForm = (TestForm) form;
        return this.getModelAndView(testForm, "pageClassInfo");
    }

    @RequestMapping(params = "methodToCall=getTaskDetail")
    public ModelAndView getTaskDetail(@ModelAttribute("KualiForm") UifFormBase form) {
        TestForm testForm = (TestForm) form;
        return this.getModelAndView(testForm, "pageTaskDetail");
    }

    @RequestMapping(params = "methodToCall=getAddTaskPage")
    public ModelAndView getAddTaskPage(@ModelAttribute("KualiForm") UifFormBase form) {
        TestForm testForm = (TestForm) form;
        return this.getModelAndView(testForm, "pageAddNewTask");
    }


    // ------------ 部分后台调用 --------------
    @RequestMapping(params = {"pageId=pageCourseTeacher", "methodToCall=getTaskListDetail"})
    public ModelAndView getTaskListDetail(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        TestForm testForm = (TestForm) form;

        try {
            CollectionControllerServiceImpl.CollectionActionParameters params =
                    new CollectionControllerServiceImpl.CollectionActionParameters(testForm, true);
            int index = params.getSelectedLineIndex();

            TestObject object = testForm.getCollection().get(index);
        } catch (Exception e) {

        }

        // FIXME: 2016/10/15 可以触发successCallback，但是btn的navigatetoPage出错
        // FIXME: 2016/10/15 即使换成btn也会出错？？普通的申请表用btn提交不会有这种问题，可能与datatable的特性有关？
        return this.getModelAndView(testForm, "pageTaskList");
    }

    @RequestMapping(params = {"pageId=pageApplyForTaForm", "methodToCall=submitTaForm"})
    public ModelAndView submitTaForm(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        TestForm testForm = (TestForm) form;

        return this.getModelAndView(testForm, "pageCommonHome");
    }


    @Override
    protected UifFormBase createInitialForm() {
        return new TestForm();
    }
}
