package cn.edu.cqu.ngtl.service.common.impl;

import cn.edu.cqu.ngtl.service.common.IdstarIdentityManagerService;
import com.wiscom.is.IdentityFactory;
import com.wiscom.is.IdentityManager;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.util.KRADConstants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by awake on 2016/12/26.
 */
public class IdstarIdentityManagerServiceImpl implements IdstarIdentityManagerService {

    @Override
    public IdentityManager getIdentityManager() throws Exception {
        String dir = System.getProperty("catalina.home") + File.separator +
                "webapps" + File.separator +
                ConfigContext.getCurrentContextConfig().getProperty("application.id") + File.separator +
                "WEB-INF" + File.separator +
                "classes" + File.separator;
        String is_config = dir + "idstar.properties";
        return IdentityFactory.createFactory(is_config).getIdentityManager();
    }

    @Override
    public String getLoginUser(HttpServletRequest request) throws UnsupportedEncodingException, Exception {
        IdentityManager im = this.getIdentityManager();
        Cookie all_cookies[] = request.getCookies();
        Cookie myCookie;
        String decodedCookieValue = null;
        if (all_cookies != null) {
            for(int i=0; i< all_cookies.length; i++) {
                myCookie = all_cookies[i];
                if( myCookie.getName().equals("iPlanetDirectoryPro") ) {
                    decodedCookieValue = URLDecoder.decode(myCookie.getValue(),"GB2312");
                }
            }
        }

        String curUser = "";
        if (decodedCookieValue != null ) {
            curUser = im.getCurrentUser( decodedCookieValue );
        }

        return curUser;
    }

    @Override
    public String getLoginUrl() throws Exception {
        IdentityManager im = this.getIdentityManager();
        StringBuilder redirectUrl = new StringBuilder(ConfigContext.getCurrentContextConfig().getProperty(KRADConstants.APPLICATION_URL_KEY));

        // 这个URL将被忽略掉，所以随便填一个合法的就可以
        //redirectUrl.append("/portal/?methodToCall=start&viewId=PortalView&pageId=pageHome");

        return im.getLoginURL()+"?goto=" + redirectUrl.toString();
    }

    @Override
    public String getLogoutUrl() throws Exception {
        IdentityManager im = this.getIdentityManager();

        StringBuilder redirectUrl = new StringBuilder(ConfigContext.getCurrentContextConfig().getProperty(KRADConstants.APPLICATION_URL_KEY));

        // 这个URL将被忽略掉，所以随便填一个合法的就可以s
        // redirectUrl.append("/portal/?methodToCall=start");

        return im.getLogoutURL()+"?goto=" + redirectUrl.toString();
    }
}
