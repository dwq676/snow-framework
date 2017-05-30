package com.zoe.snow.auth.service;

import com.zoe.snow.model.support.user.BaseUserModel;

import java.util.List;
import java.util.Set;

/**
 * BaseUserServiceSupport
 *
 * @author Dai Wenqing
 * @date 2016/2/28
 */
public interface BaseUserService {
    /*BaseUserModel createUser(BaseUserModel user); // 创建账户

    void changePassword(Long userId, String newPassword);// 修改密码

    void correlationRoles(Long userId, Long... roleIds); // 添加用户-角色关系

    void unCorrelationRoles(Long userId, Long... roleIds);// 移除用户-角色关系*/

    List<BaseUserModel> findByUsername(String username, String... domain);// 根据用户名查找用户

    BaseUserModel findByPhone(String phone);

    BaseUserModel findByUserId(String id);

    BaseUserModel findByIdCard(String idCard);

    Set<String> findRoles(String username);// 根据用户名查找其角色
}
