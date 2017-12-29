package com.zoe.snow.auth;

import com.alibaba.fastjson.JSONObject;
import com.zoe.snow.model.support.user.role.BaseRoleModel;

/**
 * Token
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/10/12
 */
public interface Token {

    JSONObject getCustom();

    public void setCustom(JSONObject custom);

    public boolean isSuperAdmin();

    public void setSuperAdmin(boolean superAdmin);

    public String getDescription();

    public void setDescription(String description);

    public String getEmail();

    public void setEmail(String email);

    public boolean isAdmin();

    public void setAdmin(boolean admin);

    public String getUsername();

    public void setUsername(String username);

    public String getAppid();

    public void setAppid(String appid);

    public String getUid();

    public void setUid(String uid);

    public String getToken();

    public void setToken(String token);

    public long getExpiredIn();

    public void setExpiredIn(long expiredIn);

    public BaseRoleModel getRoleModel();

    public void setRoleModel(BaseRoleModel roleModel);
}
