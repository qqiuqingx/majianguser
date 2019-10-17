package com.majiang.user.majianguser;

import com.google.gson.Gson;
import com.majiang.user.majianguser.bean.vo.UserReqVO;
import com.majiang.user.majianguser.bean.vo.UserVO;
import com.majiang.user.majianguser.controller.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testController {

    @Autowired
    UserController userController;

    @Autowired
    HttpServletResponse response;
    @Test
    public void testuser(){

        UserReqVO userReqVO = new UserReqVO();
        userReqVO.setPhone("123456");
        userReqVO.setPassWord("123456");
        Gson gson = new Gson();
        String s = gson.toJson(userReqVO);
        System.out.println(s);

        UserVO userVO = userController.userLogin(userReqVO, response);
        System.out.println(userVO);
    }
}
