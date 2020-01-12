package com.majiang.user.majianguser.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.majiang.user.majianguser.bean.MajiangUserBean;
import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.bean.vo.MajiangVo;
import com.majiang.user.majianguser.config.AlipayConfig;
import com.majiang.user.majianguser.enums.UserEnum;
import com.majiang.user.majianguser.enums.majiangEnum;
import com.majiang.user.majianguser.service.AlipayService;
import com.majiang.user.majianguser.service.MajiangService;
import com.majiang.user.majianguser.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.List;

@Service
public class AlipayServiceImpl implements AlipayService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlipayServiceImpl.class);
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private MajiangService majiangService;
    /**
     * 调取支付宝接口 web端支付
     */
   /* DefaultAlipayClient alipayClient = new DefaultAlipayClient(
            AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);*/

    AlipayClient alipayClient = new DefaultAlipayClient(
            AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

    /**
     * web端订单支付
     *
     */
    @Override
    public String webPagePay( Integer majiangKeyID, Cookie cookie) throws Exception {
        //MajiangVo majiangVo = null;
        if (cookie == null) {
            throw new Exception("支付未获取到对应cookie");
        }
        UserInfo user = redisUtils.getUser(cookie.getValue());
        MajiangVo muByKeyIDandUserPhone = majiangService.getMUByKeyIDandUserPhone(String.valueOf(majiangKeyID), user.getPhone(), 1);
       List<MajiangUserBean> lists= (List<MajiangUserBean>) muByKeyIDandUserPhone.getDate();
        MajiangUserBean majiangUserBean =lists.get(0);
      //  MajiangUserBean majiangUserBean = (MajiangUserBean) muByKeyIDandUserPhone.getDate();
        if (majiangUserBean == null) {
            throw new Exception("获取订单异常");
        }
        String response = webPay(majiangUserBean);
        System.out.println(response);
        //majiangVo = new MajiangVo<String>(UserEnum.SUCSS, (long) response.length(), response);
        return response;
    }

    @Override
    public String refund(String OrderID, String refundReason, Integer refundAmount, String outRequestNo) throws AlipayApiException {
        return null;
    }

    @Override
    public String query(String OrderID) throws AlipayApiException {
        return null;
    }

    @Override
    public String close(String OrderID) throws AlipayApiException {
        return null;
    }

    @Override
    public String refundQuery(String OrderID, String outRequestNo) throws AlipayApiException {
        return null;
    }

    private String webPay(MajiangUserBean majiangUserBean) throws AlipayApiException {
        LOGGER.warn(".webPay进入支付方法,入参:》》"+majiangUserBean);
        AlipayTradePagePayRequest payRequest = new AlipayTradePagePayRequest();
        /* 同步通知，支付完成后，支付成功页面*/
        payRequest.setReturnUrl(AlipayConfig.return_url);
        /* 异步通知，支付完成后，需要进行的异步处理*/
        payRequest.setNotifyUrl(AlipayConfig.notify_url);
        //总价
        Double totalAmount = majiangUserBean.getSumPrice().doubleValue();
        //KeyID：订单号
        //subject：商品名称
        String subject = "麻将桌";
        //商品详情
        String body = "订座人:" + majiangUserBean.getUserName() + ",共:" + majiangUserBean.getNum() + "位置";
        payRequest.setBizContent("{\"out_trade_no\":\"" + majiangUserBean.getKeyID() + "\","
                + "\"total_amount\":\"" + totalAmount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                  + "\"timeout_express\":\"15m\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");


        return alipayClient.pageExecute(payRequest).getBody();
    }
}
