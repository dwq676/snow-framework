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
     * @param appId 平台或者租户
     * @param ip    登录IP
     * @return
     */
    Object login(String appId, String ip, AccountViewModel accountViewModel);

    Object logout(String token);

    Object verify(String token);
}
