package cn.edu.cqu.ngtl.controller.adminmanagement;

import cn.edu.cqu.ngtl.form.adminmanagement.AdminInfoForm;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by awake on 2016-10-21.
 */
@Controller
@RequestMapping("/admin")
public class adminController extends UifControllerBase {


    @Override
    protected UifFormBase createInitialForm() {

        return new AdminInfoForm();

    }


}