package com.zoe.snow.auth.shiro.permission;

import com.zoe.snow.auth.service.BaseRoleService;
import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.model.support.user.role.BasePermissionSupport;
import org.apache.commons.lang.NotImplementedException;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.List;

/**
 * Created by Administrator on 2016/3/14.
 */
public class BaseRolePermissionResolver implements RolePermissionResolver{
    private BaseRoleService baseRoleService;

    @Override
    public Collection<Permission> resolvePermissionsInRole(String roleString){
        baseRoleService = BeanFactory.getBean(BaseRoleService.class);
        if (baseRoleService == null)
            throw new NotImplementedException("BaseRoleServiceSupport must be Implemented");
        Set<BasePermissionSupport> permissions = baseRoleService.findPermissions(roleString);
        if(permissions == null){
            return null;
        }
        List list = new ArrayList();
        for(BasePermissionSupport permission : permissions){
            if(permission.getType().equals("StringPermission")){
                list.add(new StringPermission(permission.getPermission()));
            }
        }
        return list;
    }
}
