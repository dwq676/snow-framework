package com.zoe.snow.model.support.user.role;

import com.zoe.snow.model.Model;
import com.zoe.snow.model.support.BaseModel;

/**
 * BaseRoleModel
 *
 * @author Liu Hongzhen
 * @date 2016/3/11.
 */
public interface BaseRoleModel extends Model {

    /**
     * 获取角色名
     *
     * @return
     */
    String getName();

    void setName(String roleName);

    /**
     * 获取目录ID
     *
     * @return
     */
    String getParentId();

    void setParentId(String parent);

    String getCode();

    void setCode(String code);
}
