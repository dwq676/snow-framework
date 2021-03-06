package com.zoe.snow.auth.realm;

import com.zoe.snow.auth.PasswordHelper;
import com.zoe.snow.cache.Cache;
import com.zoe.snow.cache.ExpirationWay;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

/**
 * 限制登录次数，如果5次出错，锁定5分钟
 *
 * @author Dai Wenqing
 * @date 2016/5/16
 */
public class LimitRetryHashedMatcher extends HashedCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authToken, AuthenticationInfo info) {
        String userName = (String) authToken.getPrincipal();
        //AtomicInteger retryCount = new AtomicInteger(Integer.parseInt(element.toString()));
        boolean matches = false;
        Object element = Cache.getInstance().get(userName);
        int count = 0;
        if (element != null)
            count = Integer.parseInt(element.toString());
        if (count > 5) {
            // if retry count >5 throw
            throw new ExcessiveAttemptsException();
        }
        // 匹配验证
        String sourcePassword = PasswordHelper.encryptPassword(authToken.getPrincipal().toString(),
                String.valueOf((char[]) (authToken.getCredentials())));
        matches = sourcePassword.equals(info.getCredentials());
        if (matches) {
            Cache.getInstance().remove(userName);
        } else {
            if (element == null) {
                Cache.getInstance().put(userName, 1);
            } else {
                Cache.getInstance().put(userName, ++count, ExpirationWay.AbsoluteTime, 300000);
            }
        }
        return matches;
    }
}
