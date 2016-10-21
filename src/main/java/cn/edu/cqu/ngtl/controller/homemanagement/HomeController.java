package cn.edu.cqu.ngtl.controller.homemanagement;

import cn.edu.cqu.ngtl.form.commonhome.CommonHomePage;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by tangjing on 16-10-20.
 */
@Controller
@RequestMapping("/home")
public class HomeController extends UifControllerBase {

    @RequestMapping(params = "methodToCall=getCommonHome")
    public ModelAndView getCommonHome(@ModelAttribute("KualiForm") UifFormBase form) {
        CommonHomePage homeForm = (CommonHomePage) form;
        return this.getModelAndView(homeForm, "pageCommonHome");
    }

    @Override
    protected UifFormBase createInitialForm() {
        return new CommonHomePage();
    }

}
