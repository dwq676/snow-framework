package com.zoe.snow.context;

import com.zoe.snow.cache.Cache;
import com.zoe.snow.cache.ExpirationWay;
import com.zoe.snow.context.session.Session;
import com.zoe.snow.model.support.user.BaseUserModelSupport;
import com.zoe.snow.model.support.user.UserHelper;
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
        BaseUserModelSupport user = getUser(token);
        return user == null ? "" : user.getId();
    }

    @Override
    public BaseUserModelSupport getUser(String... token) {
        BaseUserModelSupport baseUserModelSupport = null;
        if (session != null)
            baseUserModelSupport = session.get("##user");
        if (baseUserModelSupport == null)
            if (token.length > 0)
                baseUserModelSupport = Cache.getInstance().get(token[0]);
        return baseUserModelSupport;
    }

    @Override
    public String getUsername(String... token) {
        BaseUserModelSupport user = getUser(token);
        return user == null ? "" : user.getUserName();
    }

    @Override
    public String getDomain(String... token) {
        BaseUserModelSupport user = getUser(token);
        return user == null ? "0" : user.getDomain();
    }

    @Override
    public void setUser(BaseUserModelSupport user, String... token) {
        if (user != null) {
            session.put("##user", user);
            if (token.length > 0)
                Cache.getInstance().put(token[0], user, ExpirationWay.SlidingTime, session.getExpiration() * 1000);
        }
    }
}