package com.majiang.user.majianguser.controller;

import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.bean.vo.MajiangVo;
import com.majiang.user.majianguser.enums.majiangEnum;
import com.majiang.user.majianguser.service.MajiangService;
import com.majiang.user.majianguser.utils.DesUtil;
import com.majiang.user.majianguser.utils.RedisUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Autowired
    RedisUtils redisUtils;
    @RequestMapping(value = "/buy", method = RequestMethod.GET)
    //@RequiresRoles({"admin"})
    @ResponseBody
    public MajiangVo buyMajiang(@CookieValue(required = false, value = "token") Cookie cookie, String majiangKeyID) {
        LOGGER.warn("MajiangController.buyMajiang>>>>>>>>>>>>>>>>" + majiangKeyID);
        MajiangVo majiangVo = null;
        if (cookie != null) {
            majiangVo = majiangService.buyMajiang(majiangKeyID, cookie);
        } else {
            LOGGER.warn("MajiangController.buyMajiang未进入service层");
            majiangVo = new MajiangVo(majiangEnum.LOGINFORNOW);
        }
        LOGGER.warn("MajiangController.buyMajiang返回值:" + majiangVo);
        return majiangVo;
    }


    /**
     * 获取单
     *
     * @return
     */
    @RequestMapping(value = "/getMU",method = RequestMethod.GET)
    @ResponseBody
    public MajiangVo getMU(@CookieValue(required = false, value = "token") Cookie cookie, String majiangKeyID) {
        LOGGER.warn("MajiangController.getMU>>>>>>>>>>>>>>>>" + majiangKeyID);
        MajiangVo majiangVo = null;
        if (cookie != null) {
            UserInfo userInfo = (UserInfo)redisUtils.get(cookie.getValue());
            userInfo.setPhone(DesUtil.encode(DesUtil.KEY,userInfo.getPhone()));
            majiangVo = majiangService.getMUByKeyIDandUserPhone(majiangKeyID, userInfo.getPhone());
        } else {
            LOGGER.warn("MajiangController.getMU未进入service层");
            majiangVo = new MajiangVo(majiangEnum.LOGINFORNOW);
        }
        LOGGER.warn("MajiangController.getMU返回值:" + majiangVo);
        return majiangVo;
    }

}
