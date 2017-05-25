package com.zoe.snow.auth;

/**
 * Token
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/5/25
 */
public class Token {
    private String appid;
    private String uid;
    private long time;
    private String token;
    private String roleid;

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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }
}
