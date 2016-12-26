package cn.edu.cqu.ngtl.security;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.service.common.IdstarIdentityManagerService;
import cn.edu.cqu.ngtl.service.common.impl.IdstarIdentityManagerServiceImpl;
import cn.edu.cqu.ngtl.service.userservice.IUserInfoService;
import cn.edu.cqu.ngtl.service.userservice.impl.UserInfoServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.kuali.rice.core.api.config.property.ConfigContext;
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
 * Created by awake on 2016/12/26.
 */
public class IdstarLoginFilter implements Filter {

    private final static String KRAD_PATH="portal";
    private final static String CONTROLLER_PATH="home";
    private final static String VIEW_ID="PortalView";
    private final static String HOMEPAGE_METHOD="getCommonHome";
    static Logger logger = Logger.getLogger(IdstarLoginFilter.class);

    IdstarIdentityManagerService idstarIdentityManagerService = new IdstarIdentityManagerServiceImpl();

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            this.doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws Exception {
        final UserSession session = KRADUtils.getUserSessionFromRequest(request);

        if (session == null) {
            this.loginRequired(request, response, chain);
            return;

        } else {

            if(!StringUtils.equals("admin",session.getLoggedInUserPrincipalName())){
                if (StringUtils.equals(request.getServletPath(), "/portal.do")) {
                    this.autoLoginWhileSession(request, response);
                    return;
                }
            }

            // Perform request as signed in user
            request = new HttpServletRequestWrapper(request) {
                @Override
                public String getRemoteUser() {
                    return session.getPrincipalId();
                }
            };
            chain.doFilter(request, response);
        }
    }

    private void autoLoginWhileSession(HttpServletRequest request, HttpServletResponse response) throws IOException{
        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        // Test if session was successfully build for this user

        if ( userSession.getPerson() == null ) {
            throw new AuthenticationException("Invalid User " );
        }

        StringBuilder redirectUrl = new StringBuilder(ConfigContext.getCurrentContextConfig().getProperty(KRADConstants.APPLICATION_URL_KEY));
        redirectUrl.append("/portal/?methodToCall=start&viewId=PortalView&pageId=pageHome");

        response.sendRedirect(redirectUrl.toString());
    }

    private void loginRequired(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws Exception {
        String curUser = this.idstarIdentityManagerService.getLoginUser(request);

        if (StringUtils.isNotBlank(curUser)) {

            this.performLoginAttempt(request, response);
        } else {
            // ignore ajax calls from login screen
            if (StringUtils.equals(request.getPathInfo(),"/listener")) {
                return;
            }
            // allow redirect and form submit from login screen
            if (StringUtils.equals(request.getPathInfo(),"/login")) {
                chain.doFilter(request, response);
            } else {
                // no session has been established and this is not a login form submission, so redirect to login page
                response.sendRedirect(this.idstarIdentityManagerService.getLoginUrl());
            }
        }
    }

    private void performLoginAttempt(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String user = this.idstarIdentityManagerService.getLoginUser(request);

        // 此处调用IdentityService方法判断登录用户是否为本系统的有效用户
        Principal isValidUser = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(user);
        if (isValidUser == null) {
            this.handleInvalidLogin(request, response);
            return;
        }

        UserSession userSession = new UserSession(user);

        // Test if session was successfully build for this user
        if ( userSession.getPerson() == null ) {
            throw new AuthenticationException("Invalid User: " + user  );
        }

        IUserInfoService userinfoservice=new UserInfoServiceImpl();
        User loginUser = userinfoservice.getUserByUserSession(userSession);
        userSession.addObject("user", loginUser);

        request.getSession().setAttribute(KRADConstants.USER_SESSION_KEY, userSession);

        // 设置用户无操作session失效时间：暂定为5分钟
        request.getSession().setMaxInactiveInterval(3000);

        //在会话存在的情况下获取学生ID，IP地址，保存至MDC中
        MDC.put("remoteHost",request.getRemoteAddr());
        MDC.put("UserType",loginUser.getType());
        MDC.put("UserId",loginUser.getTag());
        MDC.put("UserName",loginUser.getName());
        logger.info("登录系统");

        String Agent = request.getHeader("User-Agent");
        StringBuilder redirectUrl = new StringBuilder(ConfigContext.getCurrentContextConfig().getProperty(KRADConstants.APPLICATION_URL_KEY));
        if (Agent.contains("MSIE 9.0")||Agent.contains("MSIE 8.0")||Agent.contains("MSIE 7.0")||Agent.contains("MSIE 6.0")) {
            redirectUrl.append("/Message.html");
            response.sendRedirect(redirectUrl.toString());
        }else if(StringUtils.equals(userSession.getPrincipalId(),"admin")){
            redirectUrl.append("/portal.do");
            response.sendRedirect(redirectUrl.toString());
        } else{
            redirectUrl.append(String.format("/%s/%s?methodToCall=%s&viewId=%s",
                    KRAD_PATH,CONTROLLER_PATH,HOMEPAGE_METHOD,VIEW_ID));
            response.sendRedirect(redirectUrl.toString());
        }

    }

    /**
     * Handles and invalid login attempt.
     *
     * Sets error message and redirects to login screen
     *
     * @param request the incoming request
     * @param response the outgoing response
     * @throws Exception
     */
    private void handleInvalidLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendRedirect(this.idstarIdentityManagerService.getLogoutUrl());
    }

    @Override
    public void destroy() {
    }
}
