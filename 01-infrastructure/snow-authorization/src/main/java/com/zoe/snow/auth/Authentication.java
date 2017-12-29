package com.zoe.snow.auth;


/**
 * Authentication
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/6/11
 */
public interface Authentication {
    /**
     * 登录
     *
     * @param ip 登录IP
     * @return
     */
    Object login(String ip, AccountViewModel accountViewModel);

    Object logout(String token);

    Object verify(String token);

    Object verify(PermissionBean permissionBean, String token);
}
