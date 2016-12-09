package com.zoe.snow.auth;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * 认证预留扩展接口
 *
 * @author Dai Wenqing
 * @date 2016/11/3
 */
public interface Authentication {
    void doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException;
}
