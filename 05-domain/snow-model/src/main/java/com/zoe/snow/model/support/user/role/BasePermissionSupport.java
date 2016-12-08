package com.zoe.snow.model.support.user.role;

/**
 * Created by Administrator on 2016/3/14.
 */
public interface BasePermissionSupport {

    String getPermission();

    /**
     * 设置权限信息
     * @param permission
     */
    void setPermission(String permission);

    /**
     * 获取类型，这里指的是类型编码
     * @return
     */
    String getType();

    /**
     * 设置类型
     */
    void setType(String type);

}
