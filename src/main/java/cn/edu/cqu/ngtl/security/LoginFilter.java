package cn.edu.cqu.ngtl.security;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.service.UserService.UserInfoService;
import cn.edu.cqu.ngtl.service.UserService.impl.UserInfoServiceImpl;
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

/**
 * Created by hp on 2016/10/16.
 */
public class LoginFilter implements Filter {
    static Logger logger = Logger.getLogger(LoginFilter.class);

    private String loginPath;
    private boolean showPassword = false;

    @Override
    public void init(FilterConfig filterConfig) {
        this.loginPath = ConfigContext.getCurrentContextConfig().getProperty("loginPath");
        this.showPassword = Boolean.valueOf(ConfigContext.getCurrentContextConfig().getProperty("showPassword")).booleanValue();

        if (this.loginPath == null) {
            this.loginPath = "/kr-login/login?viewId=DummyLoginView";
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        // 输入localhost:8080/tams/时就会触发
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        final UserSession session = KRADUtils.getUserSessionFromRequest(httpRequest);
//        request=(HttpServletRequest)request;
//        UserSession session = KRADUtils.getUserSessionFromRequest(request);

        if (session == null) {
            this.loginRequired(httpRequest, httpResponse, chain);
            return;
        } else {
            if (!StringUtils.equals("admin", session.getLoggedInUserPrincipalName())) {
//                if (StringUtils.equals(request.getServletPath(), "/portal.do")) {
//                    this.autoLoginWhileSession(request, response);
//                    return;
//                }
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

    private void loginRequired(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain chain) {
        if (StringUtils.isNotBlank(httpRequest.getParameter("__login_user"))) {
            // 输入用户名之后会调用
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
                // 用户未登录时会进入这里
                httpResponse.sendRedirect(this.getLoginRedirectUrl(httpRequest));
            }
        }
    }

    private void performLoginAttempt(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        IdentityService auth = KimApiServiceLocator.getIdentityService();
        final String user = request.getParameter("__login_user");
        String password = request.getParameter("__login_pw");

        if (this.showPassword && StringUtils.isBlank(password)) {
            this.handleInvalidLogin(request, response);
            return;
        }
        Principal principal = this.showPassword ? auth.getPrincipalByPrincipalNameAndPassword(user, password) :
                auth.getPrincipalByPrincipalName(user);
        if (principal == null) {
            this.handleInvalidLogin(request, response);
            return;
        }

        UserSession userSession = new UserSession(user);

        // Test if session was successfully build for this user
        if ( userSession.getPerson() == null ) {
            throw new AuthenticationException("Invalid User: " + user  );
        }

        String Agent = request.getHeader("User-Agent");

        UserInfoService userinfoservice=new UserInfoServiceImpl();
        User loginUser = userinfoservice.getUserByUserSession(userSession);
        userSession.addObject("user", loginUser);

        request.getSession().setAttribute(KRADConstants.USER_SESSION_KEY, userSession);

        // 设置用户无操作session失效时间：暂定为5分钟
        request.getSession().setMaxInactiveInterval(30000);

        //在会话存在的情况下获取学生ID，IP地址，保存至MDC中
        MDC.put("remoteHost",request.getRemoteAddr());
        MDC.put("UserType",loginUser.getType());
        MDC.put("UserId",loginUser.getTag());
        MDC.put("UserName",loginUser.getName());
        logger.info("登录系统");

        StringBuilder redirectUrl = new StringBuilder(ConfigContext.getCurrentContextConfig().getProperty(KRADConstants.APPLICATION_URL_KEY));
        if (Agent.contains("MSIE 9.0")||Agent.contains("MSIE 8.0")||Agent.contains("MSIE 7.0")||Agent.contains("MSIE 6.0")) {
            redirectUrl.append("/Message.html");
            response.sendRedirect(redirectUrl.toString());
        }else if(StringUtils.equals(userSession.getPrincipalId(),"admin")){
            // FIXME: 暂时append和普通用户一样的路径,/xxx & viiewid正式使用时都需要修改
//            redirectUrl.append("/portal.do");
            redirectUrl.append("/hello/?methodToCall=getCommonHome&viewId=MyTestView");
            response.sendRedirect(redirectUrl.toString());
        } else{
            redirectUrl.append("/portal/?methodToCall=getHomePage&viewId=PortalView&pageId=pageHome");
            response.sendRedirect(redirectUrl.toString());
        }
    }

    @Override
    public void destroy() {

    }
}
