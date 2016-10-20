package cn.edu.cqu.ngtl.controller.classmanagement;

import cn.edu.cqu.ngtl.form.TestForm;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
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

    @Override
    protected UifFormBase createInitialForm() {
        //TODO
        return null;
    }

}
