package cn.edu.cqu.ngtl.controller.homemanagement;

import cn.edu.cqu.ngtl.form.commonhome.CommonHomePage;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tangjing on 16-10-20.
 */
@Controller
@RequestMapping("/home")
public class HomeController extends UifControllerBase {

    private final static String KRAD_PATH="portal";
    private final static String CONTROLLER_PATH="home";
    private final static String VIEW_ID="PortalView";
    private final static String HOMEPAGE_METHOD="getCommonHome";

    @RequestMapping(params = "methodToCall=getCommonHome")
    public ModelAndView getCommonHome(@ModelAttribute("KualiForm") UifFormBase form) {
        CommonHomePage homeForm = (CommonHomePage) form;
        return this.getModelAndView(homeForm, "pageCommonHome");
    }

    @RequestMapping(params = "methodToCall=logout")
    public ModelAndView logout(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        UserSession userSession = GlobalVariables.getUserSession();
        if (userSession.isBackdoorInUse()) {
            userSession.clearBackdoorUser();
        }

        request.getSession().invalidate();

        StringBuilder redirectUrl = new StringBuilder(ConfigContext.getCurrentContextConfig().getProperty(KRADConstants.APPLICATION_URL_KEY));
        redirectUrl.append(String.format("/%s/%s?methodToCall=%s&viewId=%s",
                KRAD_PATH,CONTROLLER_PATH,HOMEPAGE_METHOD,VIEW_ID));
        return this.performRedirect(form, redirectUrl.toString());
    }

    @Override
    protected UifFormBase createInitialForm() {
        return new CommonHomePage();
    }

}
