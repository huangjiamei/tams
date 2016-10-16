package cn.edu.cqu.ngtl.controller;

import cn.edu.cqu.ngtl.form.TestForm;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by hp on 2016/10/7.
 */
@Controller
@RequestMapping("/mytest")
public class TestController extends UifControllerBase{


    @RequestMapping("/func")
    public void func(){
        System.out.println("func");
    }

    @RequestMapping(params = "methodToCall=getMyInformationPage")
    public ModelAndView getMyInformationPage(@ModelAttribute("KualiForm") UifFormBase form){
        TestForm testForm=(TestForm) form;
        return this.getModelAndView(testForm, "pageTest1");
    }

    @RequestMapping(params = "methodToCall=getTeacherCoursePage")
    public ModelAndView getDivPage(@ModelAttribute("KualiForm") UifFormBase form){
        TestForm testForm=(TestForm) form;
        return this.getModelAndView(testForm, "pageCourseTeacher");
    }

    @RequestMapping(params = "methodToCall=getDatatablePage")
    public ModelAndView getDatatablePage(@ModelAttribute("KualiForm") UifFormBase form){
        TestForm testForm=(TestForm) form;
        return this.getModelAndView(testForm, "pageDatatable");
    }

    @Override
    protected UifFormBase createInitialForm() {
        return new TestForm();
    }
}
