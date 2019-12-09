package com.majiang.user.majianguser.service;

import com.majiang.user.majianguser.bean.Role;
import com.majiang.user.majianguser.bean.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleService {
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
     * 添加角色
     */
    Integer addRole(Role role);

    /**
     * 添加角色权限关联表
     */
    Integer addRolePermiss(Integer reloNo,  List<Integer> permissions);

    /**
     * 添加用户角色关联表
     */
    void addUserRole(String Phone,List<Role> roles);
}
