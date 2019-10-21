package com.majiang.user.majianguser.controller;

import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.bean.vo.UserReqVO;
import com.majiang.user.majianguser.bean.vo.UserVO;
import com.majiang.user.majianguser.enums.UserEnum;
import com.majiang.user.majianguser.service.UserInfoservice;
import com.majiang.user.majianguser.service.impl.UserInfoserviceImpl;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.security.pkcs11.P11Util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.midi.Soundbank;

@Controller
public class UserController {

    @Autowired
    UserInfoservice userInfoservice;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoserviceImpl.class);



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
        LOGGER.warn("UserController.addUser 返回值"+userVO);
        return userVO;
    }


    /**
     * 用户手机号登录
     */
    @RequestMapping(value = "/userLogin",method = RequestMethod.POST)
    @ResponseBody
    public UserVO userLogin( @RequestBody UserReqVO userInfo, HttpServletResponse response){
        System.out.println("登录");

        return userInfoservice.userLogin(userInfo,  response);
    }


    @ResponseBody
    @RequestMapping(value = "/getAllUser",method = RequestMethod.POST)
    public UserVO getAllUser(){
        return userInfoservice.selectAllUser();
    }

    /**
     * 退出
     * @return
     */
    @RequestMapping(value = "/outApp")
    public String outApp(@CookieValue(value = "token")Cookie cookie, HttpSession session,HttpServletResponse response){
        System.out.println("进入方法");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        session.invalidate();
        boolean b = userInfoservice.outApp(cookie.getValue());
        return "redirect:/";
    }

}
