package cn.edu.cqu.ngtl.security;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.service.userservice.IUserInfoService;
import cn.edu.cqu.ngtl.service.userservice.impl.UserInfoServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.exception.AuthenticationException;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.KRADUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by hp on 2016/10/16.
 */
public class LoginFilter implements Filter {

    private final static String KRAD_PATH="hello";
    private final static String CONTROLLER_PATH="mytest";
    private final static String HOMEPAGE_METHOD="getCommonHome";
    private final static String VIEW_ID="PortalView";

    static Logger logger = Logger.getLogger(LoginFilter.class);
    private String loginPath;

    private boolean needPassword = false;

    @Override
    public void init(FilterConfig filterConfig) {
        this.loginPath = ConfigContext.getCurrentContextConfig().getProperty("loginPath");
        this.needPassword = Boolean.valueOf(ConfigContext.getCurrentContextConfig().getProperty("needPassword")).booleanValue();

        if (this.loginPath == null) {
            this.loginPath = "/kr-login/login?viewId=DummyLoginView";
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 输入localhost:8080/tams/时就会触发
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        final UserSession session = KRADUtils.getUserSessionFromRequest(httpRequest);

        if (session == null) {
            this.loginRequired(httpRequest, httpResponse, chain);
            return;
        } else {
            if (!StringUtils.equals("admin", session.getLoggedInUserPrincipalName())) {
                // TODO: 2016/10/17 作用未知，不知道怎么进入这个if
                if (StringUtils.equals(httpRequest.getServletPath(), "/portal.do")) {
                    this.autoLoginWhileSession(httpRequest, httpResponse);
                    return;
                }
            }
            // Perform request as signed in user
            httpRequest = new HttpServletRequestWrapper(httpRequest) {
                @Override
                public String getRemoteUser() {
                    return session.getPrincipalId();
                }
            };
        }
        try {
            chain.doFilter(httpRequest, httpResponse);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }


    private void autoLoginWhileSession(HttpServletRequest request, HttpServletResponse response) throws IOException{
        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        // Test if session was successfully build for this user
        if ( userSession.getPerson() == null ) {
            throw new AuthenticationException("Invalid User " );
        }


        StringBuilder redirectUrl = new StringBuilder(ConfigContext.getCurrentContextConfig().getProperty(KRADConstants.APPLICATION_URL_KEY));
        //
        redirectUrl.append(String.format("/%s/%s?methodToCall=%s&viewId=%s",
                KRAD_PATH,CONTROLLER_PATH,HOMEPAGE_METHOD,VIEW_ID));

        response.sendRedirect(redirectUrl.toString());
    }

    private void loginRequired(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain chain) throws IOException, ServletException {
        if (StringUtils.isNotBlank(httpRequest.getParameter("__login_user"))) {
            // 如果有输入用户名，就会调用下面的方法
            this.performLoginAttempt(httpRequest, httpResponse);
        } else {
            // ignore ajax calls from login screen
            if (StringUtils.equals(httpRequest.getPathInfo(), "/listener")) {
                return;
            }
            // allow redirect and form submit from login screen
            if (StringUtils.equals(httpRequest.getPathInfo(), "/login")) {
//                chain.doFilter(httpRequest, httpResponse);
            } else {
                // no session has been established and this is not a login form submission, so redirect to login page
                httpResponse.sendRedirect(this.getLoginRedirectUrl(httpRequest));
            }
        }
    }

    private void performLoginAttempt(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
        IdentityService auth = KimApiServiceLocator.getIdentityService();
        final String user = httpRequest.getParameter("__login_user");
        String password = httpRequest.getParameter("__login_pw");


        if (this.needPassword && StringUtils.isBlank(password)) {
            this.handleInvalidLogin(httpRequest, httpResponse);
            return;
        }
        Principal principal = this.needPassword ? auth.getPrincipalByPrincipalNameAndPassword(user, password) :
                auth.getPrincipalByPrincipalName(user);
        if (principal == null) {
            this.handleInvalidLogin(httpRequest, httpResponse);
            return;
        }

        UserSession userSession = new UserSession(user);

        // Test if session was successfully build for this user
        if (userSession.getPerson() == null) {
            throw new AuthenticationException("Invalid User: " + user);
        }

        String Agent = httpRequest.getHeader("User-Agent");

        IUserInfoService userinfoservice = new UserInfoServiceImpl();
        User loginUser = userinfoservice.getUserByUserSession(userSession);
        userSession.addObject("user", loginUser);

        httpRequest.getSession().setAttribute(KRADConstants.USER_SESSION_KEY, userSession);

        // 设置用户无操作session失效时间：暂定为50分钟
        httpRequest.getSession().setMaxInactiveInterval(30000);

        //在会话存在的情况下获取学生ID，IP地址，保存至MDC中
        MDC.put("remoteHost", httpRequest.getRemoteAddr());
        MDC.put("UserType", loginUser.getType());
        MDC.put("UserId", loginUser.getTag());
        MDC.put("UserName", loginUser.getName());
        logger.info("登录系统");

        StringBuilder redirectUrl = new StringBuilder(ConfigContext.getCurrentContextConfig().getProperty(KRADConstants.APPLICATION_URL_KEY));
        if (Agent.contains("MSIE 9.0") || Agent.contains("MSIE 8.0") || Agent.contains("MSIE 7.0") || Agent.contains("MSIE 6.0")) {
            redirectUrl.append("/Message.html");
            httpResponse.sendRedirect(redirectUrl.toString());
        } else if (StringUtils.equals(userSession.getPrincipalId(), "admin")) {
            // FIXME: 暂时append和普通用户一样的路径,/xxx & viiewid正式使用时都需要修改
//            redirectUrl.append("/portal.do");
            redirectUrl.append(String.format("/%s/%s?methodToCall=%s&viewId=%s",
                    KRAD_PATH,CONTROLLER_PATH,HOMEPAGE_METHOD,VIEW_ID));
            httpResponse.sendRedirect(redirectUrl.toString());
        } else {
            redirectUrl.append(String.format("/%s/%s?methodToCall=%s&viewId=%s",
                    KRAD_PATH,CONTROLLER_PATH,HOMEPAGE_METHOD,VIEW_ID));
            httpResponse.sendRedirect(redirectUrl.toString());
        }
    }

    /**
     * Handles and invalid login attempt.
     * <p>
     * Sets error message and redirects to login screen
     *
     * @param request  the incoming request
     * @param response the outgoing response
     * @throws javax.servlet.ServletException if unable to handle the invalid login
     * @throws java.io.IOException            if unable to handle the invalid login
     */
    private void handleInvalidLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder redirectUrl = new StringBuilder(this.getLoginRedirectUrl(request));
        redirectUrl.append("&login_message=Invalid Login");
        response.sendRedirect(redirectUrl.toString());
    }

    /**
     * Construct Url to login screen with original target Url in returnLocation property
     *
     * @param request
     * @return Url string
     * @throws IOException
     */
    private String getLoginRedirectUrl(HttpServletRequest request) throws IOException {
        String targetUrl = this.findTargetUrl(request);

        StringBuilder redirectUrl = new StringBuilder(ConfigContext.getCurrentContextConfig().getProperty(KRADConstants.APPLICATION_URL_KEY));
        redirectUrl.append(this.loginPath);
        redirectUrl.append("&returnLocation=");
        redirectUrl.append(URLEncoder.encode(targetUrl, "UTF-8"));  // 控制登录之后跳转到哪里

        return redirectUrl.toString();
    }

    /**
     * Construct a url from a HttpServletRequest with login properties removed
     *
     * @param request
     * @return Url string
     */
    private String findTargetUrl(HttpServletRequest request) {
        StringBuilder targetUrl = new StringBuilder();
        targetUrl.append(request.getServletPath());

        if (StringUtils.isNotBlank(request.getPathInfo())) {
            targetUrl.append(request.getPathInfo());
        }

        // clean login params from query string
        if (StringUtils.isNotBlank(request.getQueryString())) {
            targetUrl.append("?");

            for (String keyValuePair : request.getQueryString().split("&")) {
                if (this.isValidProperty(keyValuePair)) targetUrl.append("&").append(keyValuePair);
            }

        }

        // clean up delimiters and return url string
        return targetUrl.toString().replace("&&", "&").replace("?&", "?");
    }
    /**
     * Test if property is needed (ie: Not a login property)
     *
     * @param keyValuePair
     * @return Boolean
     */
    private Boolean isValidProperty(String keyValuePair) {
        int eq = keyValuePair.indexOf("=");

        if (eq < 0) {
            // key with no value
            return Boolean.FALSE;
        }

        String key = keyValuePair.substring(0, eq);
        if (!key.equals("__login_pw")
                && !key.equals("__login_user")
                && !key.equals("login_message")) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    @Override
    public void destroy() {

    }
}
