package com.majiang.user.majianguser.controller;

import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.bean.vo.UserReqVO;
import com.majiang.user.majianguser.bean.vo.UserVO;
import com.majiang.user.majianguser.enums.UserEnum;
import com.majiang.user.majianguser.service.UserInfoservice;
import io.swagger.annotations.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Soundbank;
import java.util.logging.Logger;

@Controller
public class UserController {

    @Autowired
    UserInfoservice userInfoservice;




    /**
     * 新增用户
     * @param userInfo
     * @return
     * @throws UnknownError
     */
    @ApiOperation(value = "新增用户",notes = "手机号为唯一")
    @ResponseBody
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public UserVO addUser( @RequestBody UserReqVO userInfo) throws  Exception {
        System.out.println("进入");

        UserVO userVO = userInfoservice.insertUser(userInfo);
        System.out.println("获取到的值: "+userVO);
        return userVO;
    }
    @ApiOperation(value = "打招呼")
    @ResponseBody
    @RequestMapping(value = "/seyHello",method = RequestMethod.GET)
    public String hello(){
        /*new UserVO(UserEnum.SUCSS);*/
        return "hello";
    }


    /**
     * 用户手机号登录
     */
    @RequestMapping(value = "/userLogin",method = RequestMethod.POST)
    @ResponseBody
    public UserVO userLogin(@RequestBody UserReqVO userInfo){
        System.out.println("登录");

        return userInfoservice.userLogin(userInfo);
    }

}
