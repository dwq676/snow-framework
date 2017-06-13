package com.zoe.snow.conf;

/**
 * AuthenticationConf
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/6/10
 */
public interface AuthenticationConf {
    //String getAuthenticationUrl

    /**
     * 认证方式，是否为第三方认证
     *
     * @return
     */
    boolean getAuthIsThirdPart();

    String getAuthHost();

    int getAuthPort();

    String getAuthProject();

    long getAuthExpiredIn();

    long getAuthExpiredRemember();

    boolean getAuthMulti();
}
