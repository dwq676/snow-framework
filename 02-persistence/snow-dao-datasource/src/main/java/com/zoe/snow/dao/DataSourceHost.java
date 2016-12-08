package com.zoe.snow.dao;

import com.zoe.snow.util.Security;
import org.springframework.stereotype.Repository;

/**
 * 主机相关信息
 *
 * @author Dai Wenqing
 * @date 2016/10/12
 */
@Repository("snow.dao.datasource.host")
public class DataSourceHost {
    //private String mode;
    private String ip;
    private String user;
    private String password;
    private int port;
    private boolean hostSwitch;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean getHostSwitch() {
        return hostSwitch;
    }

    public void setHostSwitch(boolean hostSwitch) {
        this.hostSwitch = hostSwitch;
    }

    /*public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }*/

    public String getIp() {
        return Security.AESDecode(ip);
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUser() {
        return Security.AESDecode(user);
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return Security.AESDecode(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
