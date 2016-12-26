package cn.edu.cqu.ngtl.controller.homemanagement;

import cn.edu.cqu.ngtl.controller.BaseController;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.form.commonhome.CommonHomePage;
import cn.edu.cqu.ngtl.service.common.impl.IdstarIdentityManagerServiceImpl;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.beans.factory.annotation.Autowired;
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
public class HomeController extends BaseController {

    @Autowired
    private UTSessionDao utSessionDao;


    private final static String KRAD_PATH="portal";
    private final static String CONTROLLER_PATH="home";
    private final static String VIEW_ID="PortalView";
    private final static String HOMEPAGE_METHOD="getCommonHome";

    @RequestMapping(params = "methodToCall=getCommonHome")
    public ModelAndView getCommonHome(@ModelAttribute("KualiForm") UifFormBase form) {
        CommonHomePage homeForm = (CommonHomePage) form;
        super.baseStart(homeForm);
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
        String ifUseIdstar = ConfigContext.getCurrentContextConfig().getProperty("filter.login.class");
        if (ifUseIdstar.contains("IdstarLoginFilter")) {
            // 将用户从重庆大学统一认证平台注销
            return this.performRedirect(form, new IdstarIdentityManagerServiceImpl().getLogoutUrl());
        } else {
            return this.returnToHub(form);
        }
    }


    @RequestMapping(params =  {"methodToCall=selectCurSession"})
    public ModelAndView selectCurSession(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request) {
        CommonHomePage homeForm = (CommonHomePage) form;
        super.baseStart(homeForm);
        utSessionDao.setCurrentSession(utSessionDao.getUTSessionById(Integer.parseInt(homeForm.getSessionTermFinder())));
        return this.getModelAndView(homeForm, homeForm.getPageId());
    }



    @Override
    protected UifFormBase createInitialForm() {
        return new CommonHomePage();
    }

}
