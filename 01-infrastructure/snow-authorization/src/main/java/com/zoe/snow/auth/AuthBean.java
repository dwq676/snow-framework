package com.zoe.snow.auth;

import org.springframework.stereotype.Component;

/**
 * DataSourceNode
 *
 * @author Dai Wenqing
 * @date 2016/10/12
 */
@Component("snow.auth.bean")
public class AuthBean {
    private boolean authSwitch = false;

    public boolean getAuthSwitch() {
        return authSwitch;
    }

    public void setAuthSwitch(boolean authSwitch) {
        this.authSwitch = authSwitch;
    }
}
