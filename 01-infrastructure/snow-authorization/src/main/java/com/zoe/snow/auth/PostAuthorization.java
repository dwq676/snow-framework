package com.zoe.snow.auth;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 授权预留扩展接口
 *
 * @author Dai Wenqing
 * @date 2016/11/3
 */
public interface PostAuthorization {
    AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals, AuthorizationInfo authorizationInfo);
}
