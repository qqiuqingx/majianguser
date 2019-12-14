package com.majiang.user.majianguser.service.impl;

import com.majiang.user.majianguser.bean.Role;
import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.mapper.RoleMapper;
import com.majiang.user.majianguser.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class RoleServiceImpl implements RoleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);
    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Role> findByUserPhone(String Phone) {
        return null;
    }


    @Override
    public List<Role> findAll() {
        return null;
    }

    @Override
    public Integer addRole(Role role) {
        return null;
    }

    @Override
    public Integer addRolePermiss(Integer reloNo, List<Integer> permissions) {
        return null;
    }

    @Override
    public void addUserRole(String Phone, List<Role> roles) throws Exception {
        List<Integer> roleNos = null;
        Integer integer = null;
        List<Role> allroles = null;
        List<Integer> collect=null;
        try {
            allroles = roleMapper.findAll();
            if (null == allroles) {
                throw new Exception("未查询到角色:" + allroles);
            }
/*
            //
            for (Role role:roles){

                collect = allroles.stream().filter(role1 -> role.getName().equals(role1.getName())).map(Role::getRoleNo).collect(Collectors.toList());
            }
            System.out.println("新方法得到的:"+collect);
*/
            for (Role role1 : roles) {
                for (Role role : allroles) {
                    if (role1.getName().equals(role.getName())) {
                        role1.setRoleNo(role.getRoleNo());
                    }
                }
            }
           //过滤掉roleNO为null的，然后再获取到roleno并生成List<Integer> roleNos
            roleNos = roles.stream().filter(role -> role.getRoleNo()!=null).map(Role::getRoleNo).collect(Collectors.toList());
            roleMapper.addUserRole(Phone, roleNos);
        } catch (Exception e) {
            LOGGER.error("添加用户角色关联错误:", e);
            throw new Exception(e);
        } finally {
            LOGGER.warn("添加用户:" + Phone + ",角色编号:" + roleNos);
        }

    }
}
