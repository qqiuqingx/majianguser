package com.majiang.user.majianguser.mapper;


import com.majiang.user.majianguser.bean.Permission;
import com.majiang.user.majianguser.bean.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    /**
     * 根据手机号查询对应角色
     * @return
     */
    List<Role> findByUserPhone(String Phone);


    /**
     * 查询所有的角色
     * @return
     */
    List<Role> findAll();

    /**
     * 添加权限
     */
    Integer addRole(Role role);

    /**
     * 添加角色权限关联表
     */
    Integer addRolePermiss(@Param("reloNo") Integer reloNo,@Param("permissionNo") List<Integer> permissions);
}
