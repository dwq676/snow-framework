package com.zoe.snow.auth;

import java.io.Serializable;

/**
 * AccountViewModel
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/4/20
 */
public class AccountViewModel implements Serializable {
    private String username;
    private String password;
    private String appid;
    private boolean rememberMe;

    public boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
