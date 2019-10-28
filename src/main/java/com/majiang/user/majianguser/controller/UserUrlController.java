package com.majiang.user.majianguser.controller;

import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

@Controller
public class UserUrlController {

    @Autowired
    RedisUtils redisUtils;

    @RequestMapping("/")
    public String getMain(@CookieValue(required = false, value = "token") Cookie cookie, HttpSession session) {
        System.out.println("进入：UserUrlController.getMain");
        if (cookie != null) {
            String token = cookie.getValue();
            System.out.println("UserUrlController.getMain："+token);
            UserInfo user = (UserInfo) redisUtils.get(token);
            System.out.println("UserUrlController.getMain：>>>>"+user);
            if (null != user) {
                System.out.println("UserUrlController.getMain："+user);
                session.setAttribute("userinfo", user);
            }
        }
        return "index";
    }

    @RequestMapping("/login")
    public String userLogin() {
        System.out.println("跳转》》》》》》》》》》》》。");
        return "login";
    }
}
