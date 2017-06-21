package com.zoe.snow.auth;

import com.alibaba.fastjson.annotation.JSONField;
import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.conf.AuthenticationConf;
import com.zoe.snow.model.ResultSet;
import com.zoe.snow.model.support.user.role.BaseRoleModel;
import net.sf.json.JSONObject;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * Token
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/5/25
 */
public class Token implements Serializable {
    private String appid;
    private String uid;
    @JSONField(name = "expired_in")
    private long expiredIn;
    private String token;
    private String username;

    @JSONField(name = "role")
    private BaseRoleModel baseRoleModel;

    /**
     * 第三方认证或本地认证
     *
     * @param token
     * @return
     */
    public static boolean verify(String token) {
        AuthenticationConf conf = BeanFactory.getBean(AuthenticationConf.class);
        Authentication authentication = null;
        String remoteAuthBeanName = "snow.auth.service.remote";
        ResultSet result = null;
        boolean remoteResult = false;
        boolean isRemote = false;
        if (conf.getAuthIsThirdPart()) {
            authentication = BeanFactory.getBean(remoteAuthBeanName);
            JSONObject jsonObject = JSONObject.fromObject(authentication.verify(token).toString());
            remoteResult = jsonObject.getBoolean("success");
            isRemote = true;
        } else {
            Collection<Authentication> authentications = BeanFactory.getBeans(Authentication.class);
            for (Authentication auth : authentications) {
                if (!(auth instanceof Remote))
                    authentication = auth;
            }
            result = ResultSet.class.cast(authentication.verify(token));
        }
        if (!isRemote)
            if (result != null)
                return result.isSuccess();
            else
                return false;
        else
            return remoteResult;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiredIn() {
        return expiredIn;
    }

    public void setExpiredIn(long expiredIn) {
        this.expiredIn = expiredIn;
    }

    public BaseRoleModel getBaseRoleModel() {
        return baseRoleModel;
    }

    public void setBaseRoleModel(BaseRoleModel baseRoleModel) {
        this.baseRoleModel = baseRoleModel;
    }
}
