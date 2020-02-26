package com.majiang.user.majianguser.controller;

import com.majiang.user.majianguser.bean.MajiangUserBean;
import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.bean.vo.MajiangVo;
import com.majiang.user.majianguser.enums.UserEnum;
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

    /**
     * 下单
     *
     * @param cookie
     * @param majiangKeyID
     * @param num
     * @return
     */
    @RequestMapping(value = "/buy", method = RequestMethod.GET)
    //@RequiresRoles({"admin"})
    @ResponseBody
    public MajiangVo buyMajiang(@CookieValue(required = false, value = "token") Cookie cookie, String majiangKeyID, Integer num) {
        LOGGER.warn("MajiangController.buyMajiang>>>>>>>>>>>>>>>>" + majiangKeyID);
        MajiangVo majiangVo = null;
        try {
            if (cookie != null) {
                majiangVo = majiangService.buyMajiang(majiangKeyID, cookie, num);
            } else {
                LOGGER.warn("MajiangController.buyMajiang未进入service层");
                majiangVo = new MajiangVo(majiangEnum.LOGINFORNOW);
            }
        } catch (Exception e) {
            LOGGER.error("系统异常", e);
            majiangVo = new MajiangVo(UserEnum.application);
        } finally {
            LOGGER.warn("MajiangController.buyMajiang返回值:" + majiangVo);
            return majiangVo;
        }


    }


    /**
     * 获取指定桌数指定人员的订单
     *
     * @param cookie
     * @param majiangKeyID 对应的订单
     * @return
     */
    @RequestMapping(value = "/getMU", method = RequestMethod.GET)
    @ResponseBody
    public MajiangVo getMU(@CookieValue(required = false, value = "token") Cookie cookie, String majiangKeyID, Integer OrderStatus) {
        LOGGER.warn("MajiangController.getMU>>>>>>>>>>>>>>>>" + majiangKeyID);
        MajiangVo majiangVo = null;
        if (cookie != null) {
            UserInfo userInfo = (UserInfo) redisUtils.get(cookie.getValue());
            userInfo.setPhone(DesUtil.encode(DesUtil.KEY, userInfo.getPhone()));
            majiangVo = majiangService.getMUByKeyIDandUserPhone(majiangKeyID, userInfo.getPhone(), OrderStatus);
        } else {
            LOGGER.warn("MajiangController.getMU未进入service层");
            majiangVo = new MajiangVo(majiangEnum.LOGINFORNOW);
        }
        LOGGER.warn("MajiangController.getMU返回值:" + majiangVo);
        return majiangVo;
    }


    /**
     * @描述 获取所有订单数量
     * @参数 [cookie]
     * @返回值 com.majiang.user.majianguser.bean.vo.MajiangVo
     * @创建人 qiuqingx
     * @创建时间 2020-02-26 17:12:47
     * @修改人和其它信息
     */
    @RequestMapping(value = "/getAllOrder", method = RequestMethod.GET)
    @ResponseBody
    public MajiangVo getAllOrder(@CookieValue(required = false, value = "token") Cookie cookie) {

        LOGGER.warn("MajiangController.getAllOrder>>>>>>>>>>>>>>>>");
        MajiangVo majiangVo = null;
        if (cookie != null) {
            UserInfo userInfo = (UserInfo) redisUtils.get(cookie.getValue());
            userInfo.setPhone(DesUtil.encode(DesUtil.KEY, userInfo.getPhone()));
            majiangVo = majiangService.getAllMajiangUserBean(userInfo.getPhone());
        } else {
            LOGGER.warn("MajiangController.getAllOrder未进入service层");
            majiangVo = new MajiangVo(majiangEnum.LOGINFORNOW);
        }

        return majiangVo;
    }

    /**
     * @描述
     * @参数
     * @返回值
     * @创建人 qiuqingx
     * @创建时间 2020-02-26 17:05:13
     * @修改人和其它信息
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public MajiangUserBean getUserOrderByMajiangKeyID() {
        return null;
    }

    /**
     * @描述 添加麻将
     * @参数 要添加的麻将桌数量，系统自动生成
     * @返回值
     * @创建人 qiuqingx
     * @创建时间 2020-02-26 17:13:17
     * @修改人和其它信息
     */
    @RequestMapping(value = "/addMjiang", method = RequestMethod.POST)
    public MajiangVo addMajang(Integer majiangnum) {
        return majiangService.addMajiang(majiangnum);
    }
}
