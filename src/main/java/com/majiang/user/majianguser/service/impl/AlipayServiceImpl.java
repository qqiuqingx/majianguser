package com.majiang.user.majianguser.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.majiang.user.majianguser.bean.vo.MajiangVo;
import com.majiang.user.majianguser.config.AlipayConfig;
import com.majiang.user.majianguser.enums.UserEnum;
import com.majiang.user.majianguser.service.AlipayService;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

@Service
public class AlipayServiceImpl implements AlipayService {

    /** 调取支付宝接口 web端支付*/
   /* DefaultAlipayClient alipayClient = new DefaultAlipayClient(
            AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);*/

    AlipayClient alipayClient = new DefaultAlipayClient(
            AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

    /**
     * web端订单支付
     * @param OrderID    订单编号（唯一）
     * @param totalAmount   订单价格
     * @param subject       商品名称
     */
    @Override
    public MajiangVo webPagePay(String OrderID, Integer totalAmount, String subject,String majiangKeyID, Cookie cookie) throws Exception {
        MajiangVo majiangVo=null;
        if (cookie == null) {
            return null;
        }

        System.out.println("--------------------------"+AlipayConfig.gatewayUrl);
        AlipayTradePagePayRequest payRequest =new AlipayTradePagePayRequest();
        /* 同步通知，支付完成后，支付成功页面*/
        payRequest.setReturnUrl(AlipayConfig.return_url);
        /* 异步通知，支付完成后，需要进行的异步处理*/
        payRequest.setNotifyUrl(AlipayConfig.notify_url);
        payRequest.setBizContent("{\"out_trade_no\":\""+ OrderID +"\","
                + "\"total_amount\":\""+ totalAmount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ "商品详细-------" +"\","
             /*   + "\"timeout_express\":\"90m\","*/
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //装换格式
        String response = alipayClient.pageExecute(payRequest).getBody();
        System.out.println(response);

        majiangVo=new MajiangVo<>(UserEnum.SUCSS,(long)response.length(),response);
        return majiangVo;
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
}
