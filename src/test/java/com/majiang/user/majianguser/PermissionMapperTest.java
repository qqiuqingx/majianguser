package com.majiang.user.majianguser;

import com.majiang.user.majianguser.bean.Permission;
import com.majiang.user.majianguser.bean.Role;
import com.majiang.user.majianguser.mapper.PermissionMapper;
import com.majiang.user.majianguser.mapper.RoleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PermissionMapperTest {

    @Autowired
    PermissionMapper mapper;
    @Autowired
    RoleMapper roleMapper;
    @Test
    public void    findByUserPhone(){
        List<Permission> byUserPhone = mapper.findByUserPhone("4EB61209FDE1D883CED65465F0C48295");
        byUserPhone.stream().map(Permission::getName).forEach(System.out::println);
        System.out.println(byUserPhone);
    }
    @Test
    public void    findBy(){
        List<Permission> byUserPhone = mapper.findByRoleName("管理员");
        byUserPhone.stream().map(Permission::getName).forEach(System.out::println);
        System.out.println(byUserPhone);
    }
    @Test
    public void    findByAll(){
        List<Permission> byUserPhone = mapper.findAll();
        byUserPhone.stream().map(Permission::getName).forEach(System.out::println);
        System.out.println(byUserPhone);
    }
    @Test
    public void    add(){
        /*
 `parentId` = 0, `name` = '日志查询', `css` = 'fa-reorder', `href` = 'pages/log/logList.html', `type` = 1, `permission` = 'sys:log:query', `sort` = 13
(#{permissionNo},#{parentId}, #{name}, #{css}, #{href}, #{type}, #{permission}, #{sort})
         */
        Permission permission = new Permission();
        Integer s = mapper.addPermission(new Permission().setPermission("sys:log:query").setHref("pages/log/logList.html").setName("日志查询").setSort(13).setType(1).setCss("fa-reorder").setParentId(0).setIsDelete(0));
        System.out.println(s);
    }

    @Test
    public void    findByUserPhone2(){
        List<Role> byUserPhone = roleMapper.findByUserPhone("4EB61209FDE1D883CED65465F0C48295");
        byUserPhone.stream().forEach(System.out::println);

    }
    @Test
    public void    findAll(){
        List<Role> byUserPhone = roleMapper.findAll();
        byUserPhone.stream().forEach(System.out::println);

    }

    @Test
    public void    addRole(){
        Integer addRole = roleMapper.addRole(new Role().setName("爱谁谁").setAddTime(new Date()).setModifyTime(new Date()).setIsDelete(0));
        System.out.println(addRole);
    }

    @Test
    public void    addRoles(){
        List<Integer> asd = new ArrayList<>();
        asd.add(3);
        asd.add(4);
        asd.add(5);
        Integer integer = roleMapper.addRolePermiss(2, asd);
        System.out.println(integer);
    }
}
