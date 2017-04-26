package com.zoe.snow.auth.realm;

import com.zoe.snow.auth.Authentication;
import com.zoe.snow.auth.Authorization;
import com.zoe.snow.auth.TokenProcessor;
import com.zoe.snow.auth.service.BaseDomainService;
import com.zoe.snow.auth.service.BaseRoleService;
import com.zoe.snow.auth.service.BaseUserService;
import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.model.support.user.BaseUserModelSupport;
import com.zoe.snow.model.support.user.UserHelper;
import org.apache.commons.lang.NotImplementedException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.Collection;
import java.util.Set;

/**
 * UserRealm
 *
 * @author Dai Wenqing
 * @date 2016/2/28
 */
public class UserRealm extends AuthorizingRealm {

    private BaseUserService baseUserService;// = new UserServiceImpl();
    private PasswordService passwordService;

    public void setPasswordService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Override
    /**
     *  /为当前登陆成功的用户授予权限和角色，针对已经登陆成功的用户
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) throws AuthorizationException {
        String username = (String) principals.getPrimaryPrincipal();
        baseUserService = BeanFactory.getBean(BaseUserService.class);
        if (baseUserService == null)
            throw new NotImplementedException("BaseUserServiceSupport must be Implemented");
        Set<String> roles = baseUserService.findRoles(username);
        if (roles == null) {
            return null;
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        Authorization authorization = BeanFactory.getBean(Authorization.class);
        Collection<Authorization> authorizationList = BeanFactory.getBeans(Authorization.class);
        if (authorizationList != null) {
            authorizationList.forEach(c -> {
                c.doGetAuthorizationInfo(principals, authorizationInfo);
            });
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String key = (String) token.getPrincipal();
        baseUserService = BeanFactory.getBean(BaseUserService.class);
        if (baseUserService == null)
            throw new NotImplementedException("BaseUserServiceSupport must be Implemented");
        BaseUserModelSupport user = baseUserService.findByUsername(key);
        if (user == null) {
            user = baseUserService.findByPhone(key);
            if (user == null) {
                user = baseUserService.findByIdCard(key);
                if (user == null) {
                    throw new UnknownAccountException();// 没找到帐号
                }
            }
        }
        if (Boolean.TRUE.equals(user.getLocked())) {
            throw new LockedAccountException(); // 帐号锁定
        }

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = String.valueOf(usernamePasswordToken.getUsername());
        SimpleAuthenticationInfo authenticationInfo = null;
        if (null != user) {
            String password = new String(usernamePasswordToken.getPassword());
            // 密码校验移交给了shiro的提供的一个接口实现类，所以这里注释掉
            authenticationInfo = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), getName());
            authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(username + "_snow"));
            user.setToken(TokenProcessor.getInstance().generateToken(authenticationInfo.getCredentials().toString(), true));

            //--------设置管理--------------------------------------------------------------------
            BaseRoleService baseRoleService = BeanFactory.getBean(BaseRoleService.class);
            /*if (baseRoleService != null)
                user.setIsAdmin(baseRoleService.getIsAdmin(user));
            else {*/
            BaseDomainService baseDomainService = BeanFactory.getBean(BaseDomainService.class);
            if (baseDomainService != null)
                user.setIsAdmin(baseDomainService.getIsAdmin(user.getDomain()));
            else if (user.getDomain().equals("0"))
                user.setIsAdmin(true);
            //}
            //--------设置管理--------------------------------------------------------------------

            UserHelper userHelper = BeanFactory.getBean(UserHelper.class);
            userHelper.setUser(user);
        }

        Collection<Authentication> authenticationList = BeanFactory.getBeans(Authentication.class);
        if (authenticationList != null) {
            authenticationList.forEach(c -> {
                c.doGetAuthenticationInfo(token);
            });
        }
        return authenticationInfo;
    }

}
