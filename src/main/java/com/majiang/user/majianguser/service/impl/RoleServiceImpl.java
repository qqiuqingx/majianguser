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
    public void addUserRole(String Phone, List<Role> roles) {
        System.out.println("用户传进来的roles："+roles);
        List<Integer>  roleNos=null;
        Integer integer=null;
        List<Role> allroles=null;
        try {
            //TODO 根据roles获取到name在查询数据库获取到所有角色的编号
            List<String> collect = roles.stream().map(Role::getName).collect(Collectors.toList());
            allroles = roleMapper.findAll();
            System.out.println("数据库中获取到的roles："+allroles);
            if (null!=allroles) {
                for (Role role1 : roles) {
                    for (Role role : allroles) {
                        if (role1.getName().equals(role.getName())) {
                            System.out.println("遍历用户传进来的roles："+role1.getName()+",roleNO:"+role1.getRoleNo());
                            System.out.println("遍历数据库中得到的roles："+role.getName()+",roleNO:"+role.getRoleNo());
                            role1.setRoleNo(role.getRoleNo());
                        }
                    }
                }
            }
            System.out.println("赋值完用户传进来的数据:"+roles);
            roleNos= roles.stream().map(Role::getRoleNo).collect(Collectors.toList());
            roleMapper.addUserRole(Phone, roleNos);
        }catch (Exception e){
            LOGGER.error("添加用户角色关联错误",e);
        }finally {
            LOGGER.warn("添加用户:"+Phone+",角色编号:"+roleNos);
        }

    }
}
