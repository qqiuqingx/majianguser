package com.majiang.user.majianguser.controller;

import com.majiang.user.majianguser.bean.vo.UserReqVO;
import com.majiang.user.majianguser.bean.vo.UserVO;
import com.majiang.user.majianguser.enums.UserEnum;
import com.majiang.user.majianguser.service.UserInfoservice;
import com.majiang.user.majianguser.service.impl.UserInfoserviceImpl;
import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    UserInfoservice userInfoservice;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);



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
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userInfo.getPhone(), userInfo.getPassWord());
        try {
            SecurityUtils.getSubject().login(usernamePasswordToken);
            userInfoservice.userLogin(userInfo,response);
        }catch (UnknownAccountException e){
            LOGGER.warn("改手机号未注册",e);
                return  new UserVO(UserEnum.PhoneNotRegistered);
        }catch (AuthenticationException e){
            LOGGER.warn("用户名或密码错误",e);
            return  new UserVO(UserEnum.PassWordNotright);
        }catch (Exception e){
            LOGGER.error("登录错误:",e);
            return new UserVO(UserEnum.NoUser);
        } finally{
            LOGGER.warn("登陆的用户为:"+userInfo.getPhone());
        }
        //return userInfoservice.userLogin(userInfo,  response);
        return new UserVO(UserEnum.SUCSS);
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
        //removeAttribute()销毁session中的某一项属性，下一次用户访问的sessionID是不会变的。如果这个地方用session.invalidate(); 会报错
        session.removeAttribute("userinfo");
        boolean b = userInfoservice.outApp(cookie.getValue());
        return "redirect:/";
    }

}
