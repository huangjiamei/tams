package cn.edu.cqu.ngtl.service.common;

import com.wiscom.is.IdentityManager;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;


/**
 * Created by awake on 2016/12/26.
 */
public interface IdstarIdentityManagerService {

    /**
     * @return: 返回IdentityManager，null为没有用户登录
     * @throws Exception
     */
    public IdentityManager getIdentityManager() throws Exception;

    /**
     * @param: request
     * @return: 获取当前是否由用户通过重庆大学统一身份认证平台登录，返回登录用户账号，null为没有用户登录
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    public String getLoginUser(HttpServletRequest request) throws UnsupportedEncodingException, Exception;

    /**
     * @return: 获取跳转到重庆大学统一身份认证平台登录页面URL
     * @throws Exception
     */
    public String getLoginUrl() throws Exception;

    /**
     * @return: 注销登录用户，获取跳转到重庆大学统一身份认证平台页面URL
     * @throws Exception
     */
    public String getLogoutUrl() throws Exception;

}

