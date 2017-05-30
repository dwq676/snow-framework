package com.zoe.snow.auth;

import com.alibaba.fastjson.annotation.JSONField;
import com.zoe.snow.model.support.user.role.BaseRoleModel;

/**
 * Token
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/5/25
 */
public class Token {
    private String appid;
    private String uid;
    private long expiration;
    private String token;

    @JSONField(name = "role")
    private BaseRoleModel baseRoleModel;

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

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public BaseRoleModel getBaseRoleModel() {
        return baseRoleModel;
    }

    public void setBaseRoleModel(BaseRoleModel baseRoleModel) {
        this.baseRoleModel = baseRoleModel;
    }
}
