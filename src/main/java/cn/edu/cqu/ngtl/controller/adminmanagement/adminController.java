package cn.edu.cqu.ngtl.controller.adminmanagement;

import cn.edu.cqu.ngtl.form.TestForm;
import cn.edu.cqu.ngtl.form.adminmanagement.AdminInfoForm;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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


    @Override
    protected UifFormBase createInitialForm() {
        return new AdminInfoForm();
    }


}