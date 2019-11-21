package com.majiang.user.majianguser.controller;
import com.majiang.user.majianguser.bean.majiangBean;
import com.majiang.user.majianguser.bean.vo.MajiangVo;
import com.majiang.user.majianguser.enums.UserEnum;
import com.majiang.user.majianguser.service.MajiangService;
import io.lettuce.core.ScriptOutputType;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;

@Controller
@RequestMapping("/majiang")
public class MajiangController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MajiangController.class);
    @Autowired
    MajiangService majiangService;
    @RequestMapping(value = "/buy",method = RequestMethod.GET)
    //@RequiresRoles({"admin"})
    @ResponseBody
    public MajiangVo buyMajiang( @CookieValue(required = false, value = "token") Cookie cookie,String majiangKeyID ){
        LOGGER.warn("MajiangController.buyMajiang>>>>>>>>>>>>>>>>"+majiangKeyID);
        MajiangVo majiangVo =null;
        if(cookie!=null) {
            majiangVo=majiangService.buyMajiang(majiangKeyID, cookie);
        }else {
            System.out.println("MajiangController.buyMajiang未进入service层");
            majiangVo=new MajiangVo(UserEnum.application);
        }
        LOGGER.warn("MajiangController.buyMajiang返回值:"+majiangVo);
        return majiangVo;
    }
}
