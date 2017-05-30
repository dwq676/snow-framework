package com.zoe.snow.model.support.user;

/**
 * UserHelper
 *
 * @author Dai Wenqing
 * @date 2016/3/7
 */
public interface UserHelper {
    String getUserId(String... token);

    String getUsername(String... token);

    String getDomain(String... token);

    BaseUserModel getUser(String... token);

    /**
     * 将用户数据缓存到会话或缓存中
     */
    //void setUser(BaseUserModelSupport user, String... token);
}
