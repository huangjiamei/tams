package cn.edu.cqu.ngtl.controller.tamanagement;

import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by tangjing on 16-10-19.
 * 助教申请相关的view及function
 */
public class applicationController extends UifControllerBase {

    @Override
    protected UifFormBase createInitialForm() {
        //TODO
        return null;
    }

    @RequestMapping(params = "methodToCall=applicationPage")
    public ModelAndView getApplicationPage(@ModelAttribute("KualiForm") UifFormBase form){
        //TODO
        return null;
    }

}
