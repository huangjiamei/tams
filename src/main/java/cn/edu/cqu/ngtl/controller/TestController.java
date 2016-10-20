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

    @RequestMapping(params = "methodToCall=getEditor")
    public ModelAndView getClassInfoPage(@ModelAttribute("KualiForm") UifFormBase form) {
        TestForm testForm = (TestForm) form;
        return this.getModelAndView(testForm, "pageEditor");
    }

    @RequestMapping(params = "methodToCall=submitEditorContent")
    public void submitEditorContent(@ModelAttribute("KualiForm") UifFormBase form ,HttpServletRequest request, HttpServletResponse response){
        TestForm testForm = (TestForm) form;

        String content=testForm.getEditorContent();
        System.out.println(content);

    }

    @Override
    protected UifFormBase createInitialForm() {
        return new TestForm();
    }
}
