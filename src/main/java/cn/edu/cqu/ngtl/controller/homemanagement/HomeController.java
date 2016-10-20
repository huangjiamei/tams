package cn.edu.cqu.ngtl.controller.homemanagement;

import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tangjing on 16-10-20.
 */
@Controller
@RequestMapping("/home")
public class HomeController extends UifControllerBase {

    @Override
    protected UifFormBase createInitialForm() {
        //TODO
        return null;
    }

}
