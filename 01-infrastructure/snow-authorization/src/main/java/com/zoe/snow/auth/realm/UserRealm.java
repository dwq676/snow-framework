package com.zoe.snow.auth.realm;

import com.zoe.snow.Global;
import com.zoe.snow.auth.Authentication;
import com.zoe.snow.auth.Authorization;
import com.zoe.snow.auth.TokenProcessor;
import com.zoe.snow.auth.service.BaseDomainService;
import com.zoe.snow.auth.service.BaseRoleService;
import com.zoe.snow.auth.service.BaseUserService;
import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.context.CoreConfig;
import com.zoe.snow.model.support.user.BaseUserModel;
import com.zoe.snow.util.Converter;
import com.zoe.snow.util.Security;
import com.zoe.snow.util.Validator;
import org.apache.commons.lang.NotImplementedException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

import java.util.Collection;
import java.util.List;
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
        //可能是手机号、用户名、邮箱等
        String key = (String) token.getPrincipal();
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        baseUserService = BeanFactory.getBean(BaseUserService.class);
        if (baseUserService == null)
            throw new NotImplementedException("BaseUserServiceSupport must be Implemented");
        BaseUserModel baseUserModel = null;

        Object domainObject = session.getAttribute(Global.DOMAIN);
        String domainId = domainObject == null ? "" : domainObject.toString();
        List<BaseUserModel> userModels = baseUserService.findByUsername(key, domainId);
        if (!Validator.isEmpty(domainId)) {
            if (userModels != null) {
                if (userModels.size() > 0) {
                    baseUserModel = userModels.get(0);
                }
            }
        }

        if (baseUserModel == null) {
            baseUserModel = baseUserService.findByPhone(key);
            if (baseUserModel == null) {
                baseUserModel = baseUserService.findByIdCard(key);
                /*if (baseUserModel != null) {
                    throw new UnknownAccountException();// 没找到帐号
                }*/
            }
        }

        /*if (!Validator.isEmpty(domainId)) {
            //Tenant tenant=bas
            BaseTenantService baseTenantService = BeanFactory.getBean(BaseTenantService.class);
            if (baseTenantService != null) {
                Tenant tenant = null;
                for (BaseUserModel u : userModels) {
                    tenant = baseTenantService.getTenant(domainId, u.getId());
                    if (tenant != null) {
                        baseUserModel = u;
                        break;
                    }
                }
                //如果多租户的接口有被实现
                if (tenant == null) {
                    throw new UnknownAccountException();
                }
            }
        }*/

        /*if (Boolean.TRUE.equals(baseUserModel.getLocked())) {
            throw new LockedAccountException(); // 帐号锁定
        }*/
        SimpleAuthenticationInfo authenticationInfo = null;
        if (baseUserModel != null) {
            UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
            String username = String.valueOf(usernamePasswordToken.getUsername());


            authenticationInfo = new SimpleAuthenticationInfo(baseUserModel.getUsername(), baseUserModel.getPassword(), getName());
            authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(username + "_snow"));
            baseUserModel.setToken(TokenProcessor.getInstance().generateToken(authenticationInfo.getCredentials().toString(), true));

            BaseRoleService baseRoleService = BeanFactory.getBean(BaseRoleService.class);
            /*if (baseRoleService != null)
                user.setIsAdmin(baseRoleService.getIsAdmin(user));
            else {*/
            BaseDomainService baseDomainService = BeanFactory.getBean(BaseDomainService.class);

        /*Domain domain = null;
        if (baseUserModel instanceof Domain)
            domain = (Domain) baseUserModel;
        if (domain != null) {
            if (baseDomainService != null)
                baseUserModel.setIsAdmin(baseDomainService.getIsAdmin(domain.getDomain()));
            else if (domain.getDomain().equals("0"))
                baseUserModel.setIsAdmin(true);
        }*/

            //UserHelper userHelper = BeanFactory.getBean(UserHelper.class);
        /*
        同一个账户多地同时连接到服务端，应该使用各自的token缓存用户信息
        避免一个地方长久没有交互，另一个地方一直交互，结果没有交互的地方会话一直不过期
        产生此原因是认证与业务逻辑相关服务分开部署
        */
            String userInfo = Security.md5(Validator.isEmpty(domainId) ? "" : domainId + baseUserModel.getUsername());
            session.setAttribute(token.getCredentials().toString(), baseUserModel);
            session.setAttribute(userInfo, baseUserModel);
            session.setTimeout(Converter.toLong(CoreConfig.getContextProperty("snow.session.time-out")) * 60 * 1000);
            //执行认证完全的回调
            Collection<Authentication> authenticationList = BeanFactory.getBeans(Authentication.class);
            if (authenticationList != null) {
                authenticationList.forEach(c -> {
                    c.doGetAuthenticationInfo(token);
                });
            }
        } else
            authenticationInfo = new SimpleAuthenticationInfo();
        return authenticationInfo;
    }

}
