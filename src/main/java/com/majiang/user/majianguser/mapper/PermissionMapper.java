package com.majiang.user.majianguser.mapper;

import com.majiang.user.majianguser.bean.Permission;

import java.util.List;

public interface PermissionMapper {
    /**
     * 根据手机号查询对应权限
      * @return
     */
    List<Permission> findByUserPhone(String Phone);

    /**
     * 根据角色名称查询对应的权限
     * @param Name
     * @return
     */
    List<Permission> findByRoleName(String Name);
    /**
     * 查询所有的权限
     * @return
     */
    List<Permission> findAll();

    /**
     * 添加权限
     */
    Integer addPermission(Permission permission);
}
