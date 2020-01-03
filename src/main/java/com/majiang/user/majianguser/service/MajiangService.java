package com.majiang.user.majianguser.service;

import com.majiang.user.majianguser.bean.MajiangUserBean;
import com.majiang.user.majianguser.bean.majiangBean;
import com.majiang.user.majianguser.bean.vo.MajiangVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.CookieValue;

import javax.servlet.http.Cookie;
import java.util.List;

public interface MajiangService {
    MajiangVo getAllmajiang();

    Integer addMajiang(majiangBean majiang);

    majiangBean getMajiang(Integer KeyID);

    /**
     * 根据majiangkeyID和用户账号获取订单信息
     * @param majiangKeyID
     * @param UserPhone
     * @return 获取订单信息
     */
    MajiangVo getMUByKeyIDandUserPhone(@Param("majiangKeyID") String majiangKeyID, @Param("userPhone") String UserPhone);

    MajiangVo getMUByKeyIDandUserPhone(@Param("majiangKeyID") String majiangKeyID, @Param("userPhone") String UserPhone,Integer OrderStatus);


    MajiangVo getAllMajiangUserBean(String UserPhone);

    /**
     * 添加订单
     * @param majiangUserBean 订单信息
     * @return
     */
    Integer addAllMajiangUserBean(MajiangUserBean majiangUserBean);

    /**
     * 修改麻将桌
     * @param majiangBean
     * @return
     */
    Integer updateMajiang(majiangBean majiangBean);

    MajiangVo buyMajiang(String majiangKeyID, @CookieValue(required = false, value = "token") Cookie cookie,Integer num);

}
