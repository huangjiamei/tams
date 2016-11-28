package cn.edu.cqu.ngtl.controller;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.form.BaseForm;
import cn.edu.cqu.ngtl.form.TestForm;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by awake on 2016/11/28.
 */

@Controller
@RequestMapping("/Base")
public class BaseController extends UifControllerBase {

    public ModelAndView baseStart(BaseForm form) {
        User user = (User) GlobalVariables.getUserSession().retrieveObject("user");
        form.setUser(user);
        String code = user.getCode();
        return super.start(form);
    }


    @Override
    protected UifFormBase createInitialForm() {
        return new TestForm();
    }
}
