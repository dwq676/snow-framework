package com.zoe.snow.auth.shiro.realm;

import com.zoe.snow.auth.PasswordHelper;
import com.zoe.snow.cache.Cache;
import com.zoe.snow.cache.ExpirationWay;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.subject.Subject;

/**
 * 限制登录次数，如果5次出错，锁定5分钟
 *
 * @author Dai Wenqing
 * @date 2016/5/16
 */
public class LimitRetryHashedMatcher extends HashedCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authToken, AuthenticationInfo info) {
        boolean matches = false;
        Subject subject = SecurityUtils.getSubject();
        int count = 1;
        Object element = Cache.getInstance().get(subject.getSession().getId().toString());
        if (element != null)
            count = Integer.parseInt(element.toString());
        if (info.getPrincipals() != null) {
            // 匹配验证
            String sourcePassword = PasswordHelper.encryptPassword(authToken.getPrincipal().toString(),
                    String.valueOf((char[]) (authToken.getCredentials())));

            matches = sourcePassword.equals(info.getCredentials());
        }
        if (!matches) {
            if (element == null) {
                Cache.getInstance().put(subject.getSession().getId().toString(), 1);
            } else {
                Cache.getInstance().put(subject.getSession().getId().toString(), ++count, ExpirationWay.AbsoluteTime, 300000);
            }
        }
        if (count > 5) {
            // if retry count >5 throw
            throw new ExcessiveAttemptsException();
        }
        return matches;
    }
}
