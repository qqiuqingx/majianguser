package com.majiang.user.majianguser.controller;

import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.bean.vo.UserVO;
import com.majiang.user.majianguser.service.UserInfoservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
    @Autowired
    UserInfoservice userInfoservice;

    @RequestMapping(value = "/addUser",method = RequestMethod.GET)
    public UserVO addUser(UserInfo userInfo) throws UnknownError{
        return userInfoservice.insertUser(userInfo);
    }
}
