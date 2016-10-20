package cn.edu.cqu.ngtl.controller.tamanagement;

import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by tangjing on 16-10-19.
 * 助教信息查看的相关view及function
 */
@Controller
@RequestMapping("/ta")
public class ViewInfoController extends UifControllerBase {

    @Override
    protected UifFormBase createInitialForm() {
        //TODO
        return null;
    }

    @RequestMapping(params = "methodToCall=getTAInfoPage")
    public ModelAndView getTAInfoPage(@ModelAttribute("KualiForm") UifFormBase form){
        //TODO
        return null;
    }

}
