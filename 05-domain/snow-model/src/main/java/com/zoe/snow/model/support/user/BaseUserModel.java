package com.zoe.snow.model.support.user;

import com.zoe.snow.model.Model;
import com.zoe.snow.model.support.Domain;

/**
 * BaseUserModel
 *
 * @author Dai Wenqing
 * @date 2015/9/14
 */
public interface BaseUserModel extends Model, Domain {

    String getUsername();

    void setUsername(String username);

    /*
     * String getDomain();
     * 
     * String setDomain(String domain);
     */

    String getPassword();

    void setPassword(String password);

    /**
     * 是否被锁住
     *
     * @return
     */
    boolean getLocked();

    void setLocked(boolean locked);

    /**
     * 一个账户是否允许多个地方同时登录
     *
     * @return
     */
    /*boolean getMulti();

    void setMulti(boolean multi);

    String getSalt();

    void setSalt(String salt);*/

    String getToken();

    void setToken(String token);

    boolean getIsAdmin();

    //boolean getRememberMe();

    void setIsAdmin(boolean isAdmin);
}
