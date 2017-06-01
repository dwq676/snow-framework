package com.zoe.snow.context;

/*import com.zoe.snow.cache.Cache;
import com.zoe.snow.cache.ExpirationWay;
import com.zoe.snow.context.session.Session;*/

import com.zoe.snow.cache.Cache;
import com.zoe.snow.context.session.Session;
import com.zoe.snow.model.support.Domain;
import com.zoe.snow.model.support.user.BaseUserModel;
import com.zoe.snow.model.support.user.UserHelper;
import com.zoe.snow.util.Validator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户帮助类
 * <p>
 * 用于将用户缓存到会话或缓存到缓存中
 *
 * @author Dai Wenqing
 * @date 2016/3/7
 */
@Component("snow.service.user.helper")
public class UserHelperImpl implements UserHelper {
    @Autowired
    private Session session;

    @Override
    public String getUserId(String... token) {
        BaseUserModel user = getUser();
        return user == null ? "" : user.getId();
    }

    @Override
    public BaseUserModel getUser(String... token) {
        BaseUserModel baseUserModelSupport = null;
        if (token.length > 0) {
            baseUserModelSupport = session.get(token[0]);
            if (baseUserModelSupport == null)
                baseUserModelSupport = Cache.getInstance().get(token[0]);
        }

        if (baseUserModelSupport == null)
            baseUserModelSupport = session.get("##user");

        return baseUserModelSupport;

        /*if (token.length == 0) {
            return null;
        }

        Subject subject = SecurityUtils.getSubject();
        return (BaseUserModel) subject.getSession().getAttribute(token);*/
    }

    @Override
    public String getUsername(String... token) {
        BaseUserModel user = getUser();
        return user == null ? "" : user.getUsername();
    }

    @Override
    public String getDomain(String... token) {
        Domain user = (Domain) getUser();
        return user == null ? "0" : user.getDomain();
    }

    /*@Override
    public void setUser(BaseUserModel user, String... token) {
        if (user != null) {
            session.put("##user", user);
            if (token.length > 0)
                Cache.getInstance().put(token[0], user, ExpirationWay.SlidingTime, session.getExpiration() * 1000);
        }
    }*/
}
