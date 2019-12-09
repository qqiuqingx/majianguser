package com.majiang.user.majianguser;

import com.majiang.user.majianguser.bean.Role;
import com.majiang.user.majianguser.service.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleServiceTest {
    @Autowired
    RoleService roleService;

    @Test
    public void addRU() {
        String Phone = "1888101468222";
        List<Role> roles = new ArrayList<>();
        roles.add(new Role().setName("123"));
        roles.add(new Role().setName("admin"));
        roles.add(new Role().setName("user"));
        try {
            roleService.addUserRole(Phone, roles);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
