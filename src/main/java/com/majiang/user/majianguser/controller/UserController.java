package com.majiang.user.majianguser.controller;

import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.bean.vo.UserVO;
import com.majiang.user.majianguser.enums.UserEnum;
import com.majiang.user.majianguser.service.UserInfoservice;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    UserInfoservice userInfoservice;

    /**
     * 添加员工
     * @param userInfo
     * @return
     * @throws UnknownError
     */
    @ApiOperation(value = "新增用户",notes = "手机号为唯一")
    @ResponseBody
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public UserVO addUser( @RequestBody UserInfo userInfo) throws UnknownError{
        System.out.println("进入");
        return userInfoservice.insertUser(userInfo);
    }
    @ApiOperation(value = "打招呼")
    @ResponseBody
    @RequestMapping(value = "/seyHello",method = RequestMethod.GET)
    public String hello(){
        /*new UserVO(UserEnum.SUCSS);*/
        return "hello";
    }
}
