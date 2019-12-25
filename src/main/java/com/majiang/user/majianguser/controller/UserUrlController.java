package com.majiang.user.majianguser.controller;

import com.majiang.user.majianguser.MajianguserApplication;
import com.majiang.user.majianguser.bean.MajiangUserBean;
import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.enums.UserEnum;
import com.majiang.user.majianguser.enums.UserExceptionEnum;
import com.majiang.user.majianguser.exception.MyShiroException;
import com.majiang.user.majianguser.exception.majiangRunTimeException;
import com.majiang.user.majianguser.service.UserInfoservice;
import com.majiang.user.majianguser.utils.RedisUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class UserUrlController {


    @Value("${majiang.redis.ORDERKEY}")
    private String ORDERKEY;

    @Autowired
    RedisUtils redisUtils;
    @Autowired
    UserInfoservice userInfoservice;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserUrlController.class);

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String getMain(@CookieValue(required = false, value = "token") Cookie cookie, HttpSession session) {
      LOGGER.warn("进入：UserUrlController.getMain");
        if (cookie != null) {
            String token = cookie.getValue();
            LOGGER.warn("UserUrlController.getMain："+token);
            UserInfo user = (UserInfo) redisUtils.get(token);
            LOGGER.warn("UserUrlController.getMain：>>>>"+user);
            if (null != user) {
                session.setAttribute("userinfo", user);
            }
        }
        return "index";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String userLogin() {
        LOGGER.warn("进入跳转方法UserUrlController.userLogin");
        return "login";
    }
    /**
     * 退出
     * @return
     */
    @RequestMapping(value = "/outApp",method = RequestMethod.GET)
    public String outApp(@CookieValue(value = "token")Cookie cookie, HttpSession session, HttpServletResponse response){
        LOGGER.warn("进入退出方法UserUrlController.outApp");
        Cookie token = new Cookie("token", null);
        token.setMaxAge(0);
        token.setPath("/user");
        response.addCookie(token);
        //removeAttribute()销毁session中的某一项属性，下一次用户访问的sessionID是不会变的。如果这个地方用session.invalidate(); 会报错
        session.removeAttribute("userinfo");
        boolean b = userInfoservice.outApp(cookie.getValue());
        return "redirect:/";
    }
    @RequestMapping(value = "/getUserList",method = RequestMethod.GET)
    public String getUserList() {
        LOGGER.warn("进入跳转方法UserUrlController.getUserList");
        return "page/userList";
    }



    @RequestMapping(value = "/userOrder",method = RequestMethod.GET)
    public String getUserOrder(@CookieValue(value = "token") Cookie cookie){
        String token = cookie.getValue();
        UserInfo userInfo = (UserInfo)redisUtils.get(token);
        System.out.println("redis获取的userinfo："+userInfo);
        if (token!=null&&userInfo!=null) {
            MajiangUserBean majiangUserBean = (MajiangUserBean)redisUtils.get(ORDERKEY + "_" + userInfo.getPhone() );
            System.out.println("获取的majiangUserBean"+majiangUserBean);
        }




        return  "page/userOrder";
    }

}
