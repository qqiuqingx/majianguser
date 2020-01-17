package com.majiang.user.majianguser.service.impl;

import com.alipay.api.*;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.majiang.user.majianguser.bean.MajiangUserBean;
import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.bean.vo.MajiangVo;
import com.majiang.user.majianguser.config.AlipayConfig;
import com.majiang.user.majianguser.enums.UserEnum;
import com.majiang.user.majianguser.enums.majiangEnum;
import com.majiang.user.majianguser.service.AlipayService;
import com.majiang.user.majianguser.service.MajiangService;
import com.majiang.user.majianguser.utils.DesUtil;
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

    AlipayClient alipayClient = new DefaultAlipayClient(
            AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);


    /**
     * web端订单支付
     */
    @Override
    public String webPagePay(Integer majiangKeyID, String token) throws Exception {

        UserInfo user = redisUtils.getUser(token);
        MajiangVo muByKeyIDandUserPhone = majiangService.getMUByKeyIDandUserPhone(String.valueOf(majiangKeyID), DesUtil.encode(DesUtil.KEY, user.getPhone()), 0);
        List<MajiangUserBean> lists = (List<MajiangUserBean>) muByKeyIDandUserPhone.getDate();
        MajiangUserBean majiangUserBean = lists.get(0);
        if (majiangUserBean == null) {
            throw new Exception("获取订单异常");
        }
        String response = careWebPay(majiangUserBean);
        System.out.println(response);
        new MajiangVo<String>(UserEnum.SUCSS,(long)response.length(),response);
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
        LOGGER.warn(".webPay进入支付方法,入参:》》" + majiangUserBean);


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

    private String careWebPay(MajiangUserBean majiangUserBean) {
        LOGGER.warn("公钥证书支付》》》》》");
        String restul=null;
        try {
            //总价
            Double totalAmount = majiangUserBean.getSumPrice().doubleValue();
            //KeyID：订单号
            //subject：商品名称
            String subject = "麻将桌";
            //商品详情
            String body = "订座人:" + majiangUserBean.getUserName() + ",共:" + majiangUserBean.getNum() + "位置";
            body="123123";
            subject="321321";


            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

            model.setBody(body);
            model.setSubject(subject);
            model.setOutTradeNo(majiangUserBean.getKeyID().toString());
            model.setTimeoutExpress("30m");
            model.setTotalAmount(totalAmount.toString());
            model.setProductCode("FAST_INSTANT_TRADE_PAY");
            request.setBizModel(model);
            request.setNotifyUrl(AlipayConfig.notify_url);
            request.setReturnUrl(AlipayConfig.return_url);

            /**
             * 支付宝公钥证书使用的Client
             */

            CertAlipayRequest certAlipayRequest;
            certAlipayRequest = new CertAlipayRequest();
            certAlipayRequest.setServerUrl(AlipayConfig.gatewayUrl);
            certAlipayRequest.setAppId(AlipayConfig.app_id);
            certAlipayRequest.setPrivateKey(AlipayConfig.merchant_private_key);
            certAlipayRequest.setFormat("json");
            certAlipayRequest.setCharset(AlipayConfig.charset);
            certAlipayRequest.setSignType(AlipayConfig.sign_type);
            //设置应用公钥证书路径
            certAlipayRequest.setCertPath(AlipayConfig.app_cert_path);
            //设置支付宝公钥证书路径
            certAlipayRequest.setAlipayPublicCertPath(AlipayConfig.alipay_cert_path);
            //设置支付宝根证书路径
            certAlipayRequest.setRootCertPath(AlipayConfig.alipay_root_cert_path);


            AlipayClient defaultAlipayClient = new DefaultAlipayClient(certAlipayRequest);
           // AlipayTradeAppPayResponse response = defaultAlipayClient.sdkExecute(request);
            AlipayTradeAppPayResponse response1 = defaultAlipayClient.pageExecute(request);
            restul = response1.getBody();
        } catch (Exception e) {
            LOGGER.error("支付错误：",e);
        } finally {
            LOGGER.warn("返回值:"+restul);
        }

        return restul;
    }

    {

    }
}
