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
     * @param UserPhone 加密的手机号
     * @param OrderStatus 订单的对应状态，若为空则查出所有的  0:已支付，1:待支付,2:已取消，3:已退款,4：逾期未支付
     * @return
     */
    MajiangVo getMUByKeyIDandUserPhone(@Param("majiangKeyID") String majiangKeyID, @Param("userPhone") String UserPhone,Integer OrderStatus);

    /**
     * 获取用户所有订单
     * @param token
     * @return
     */
    MajiangVo getAllMajiangUserBean(String token);

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


    /**
     * 根据订单号获取订单星系
     * @param OrderID 订单号(KeyID)
     * @return
     */
    MajiangVo getOrderByOrderID(String OrderID);

    void updateOrder(MajiangUserBean majiangUserBean) throws Exception;
}
