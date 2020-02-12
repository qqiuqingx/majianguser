package com.majiang.user.majianguser.service.impl;

import com.alipay.api.*;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.majiang.user.majianguser.bean.MajiangUserBean;
import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.bean.vo.MajiangVo;
import com.majiang.user.majianguser.config.AlipayConfig;
import com.majiang.user.majianguser.config.MyServerConfig;
import com.majiang.user.majianguser.enums.MajiangUserOrderEnum;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class AlipayServiceImpl implements AlipayService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlipayServiceImpl.class);
    @Autowired
    MyServerConfig myServerConfig;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private MajiangService majiangService;
    @Value("${majiang.redis.ORDERKEY}")
    private String ORDERKEY;
    @Value("${majiang.redis.ORDER_OUT_TIME}")
    private Long ORDER_OUT_TIME;
    /**
     * 调取支付宝接口 web端支付
     * RSA2只能用公钥证书方式，现改为RSA公钥方式
     */
    AlipayClient alipayClient = new DefaultAlipayClient(
            AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key_rsa, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key_rsa, AlipayConfig.sign_type_rsa);


    /**
     * web端订单支付
     */
    @Override
    public String webPagePay(Integer majiangKeyID, String token) {
        String response = null;
        try {
            UserInfo user = redisUtils.getUser(token);
            MajiangVo muByKeyIDandUserPhone = majiangService.getMUByKeyIDandUserPhone(String.valueOf(majiangKeyID), DesUtil.encode(DesUtil.KEY, user.getPhone()), 0);
            List<MajiangUserBean> lists = (List<MajiangUserBean>) muByKeyIDandUserPhone.getDate();
            MajiangUserBean majiangUserBean = lists.get(0);
            if (majiangUserBean == null) {
                throw new Exception("获取订单异常");
            }
            response = webPay(majiangUserBean);
            //String response = this.careWebPay(majiangUserBean);
            new MajiangVo<String>(UserEnum.SUCSS, (long) response.length(), response);
        } catch (Exception e) {
            LOGGER.error("系统错误", e);
            response = "1";
        }

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

    @Override
    public String alipayReturnNotice(HttpServletRequest request, HttpServletRequest response) {
        LOGGER.warn("支付成功, 进入同步通知接口service层...");
        try {
            //获取支付宝GET过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }

            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key_rsa, AlipayConfig.charset, AlipayConfig.sign_type_rsa); //调用SDK验证签名

            ModelAndView mv = new ModelAndView("alipaySuccess");
            //——请在这里编写您的程序（以下代码仅作参考）——
            if (signVerified) {
                LOGGER.warn("支付成功, 同步验证签名返回值:" + signVerified);
             /*   Map<String, String[]> parameterMap = request.getParameterMap();
                for (Map.Entry<String, String[]> map : parameterMap.entrySet()) {
                    System.out.println(map.getKey() + ":" + new String(request.getParameter(map.getKey()).getBytes("ISO-8859-1"), "UTF-8"));
                }*/
                //商户订单号
                String OrderID = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
                System.out.println("majianguserKeyID-订单号:" + OrderID);
                //支付宝交易号
                String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
                System.out.println("trade_no" + trade_no);
                //付款金额
                String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
                System.out.println("total_amount" + total_amount);
                // todo 新增支付流水  更新订单状态
                //根据订单号(KeyID)获取到对应 订单信息
                MajiangVo orderByOrderID = majiangService.getOrderByOrderID(OrderID);
                System.out.println("获取到的订单信息："+orderByOrderID);
                if (orderByOrderID.getCode() == 0) {
                    MajiangUserBean majiangUserBean = (MajiangUserBean) orderByOrderID.getDate();
                    MajiangUserBean majiangUserBean1 = (MajiangUserBean) redisUtils.get(ORDERKEY + "_" + DesUtil.encode(DesUtil.KEY, majiangUserBean.getUserPhone()) + "_" + majiangUserBean.getMajiangKeyID());
                    System.out.println("从redis中获取到的订单那信息："+majiangUserBean1);
                    if (majiangUserBean1 != null && majiangUserBean1.getKeyID().equals(majiangUserBean.getKeyID())) {
                        majiangUserBean1.setStatusandName(MajiangUserOrderEnum.Order_PAY);
                        majiangUserBean1.setAddTime(null);
                        majiangUserBean1.setModifyTime(null);
                        updateUserOrder(majiangUserBean1, majiangUserBean1.getUserPhone());
                    }
                }
            } else {
                LOGGER.warn("支付, 验签失败...");
            }
        } catch (Exception e) {
            LOGGER.error("系统异常：",e);
        } finally {
            LOGGER.warn("wanbi");
        }
        return null;
    }

    private String webPay(MajiangUserBean majiangUserBean) throws AlipayApiException {
        LOGGER.warn(".webPay进入支付方法,入参:》》" + majiangUserBean);
        AlipayTradePagePayRequest payRequest = new AlipayTradePagePayRequest();
        /* 同步通知，支付完成后，支付成功页面*/
        payRequest.setReturnUrl(myServerConfig.getUrl()+AlipayConfig.return_url);
        /* 异步通知，支付完成后，需要进行的异步处理*/
        payRequest.setNotifyUrl(myServerConfig.getUrl()+AlipayConfig.notify_url);
        System.out.println("设置的完整的NotifyUrl:"+payRequest.getNotifyUrl());
        System.out.println("ReturnUrl:"+payRequest.getReturnUrl());
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
                + "\"timeout_express\":\"2m\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        return alipayClient.pageExecute(payRequest).getBody();
    }

    private String careWebPay(MajiangUserBean majiangUserBean) {
        LOGGER.warn("公钥证书支付》》》》》");
        String restul = null;
        try {
            /**
             * 支付宝公钥证书使用的Client
             */


            CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
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


            //总价
            Double totalAmount = majiangUserBean.getSumPrice().doubleValue();
            //KeyID：订单号
            //subject：商品名称
            String subject = "麻将桌";
            //商品详情
            String body = "订座人:" + majiangUserBean.getUserName() + ",共:" + majiangUserBean.getNum() + "位置";
            body = "123123";
            subject = "321321";


            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setBody(body);
            model.setSubject(subject);
            model.setOutTradeNo("12343");
            model.setTimeoutExpress("30m");
            model.setTotalAmount("1.0");
            model.setProductCode("FAST_INSTANT_TRADE_PAY");
            // request.setBizModel(model);
            request.setNotifyUrl(AlipayConfig.notify_url);
            request.setReturnUrl(AlipayConfig.return_url);
            request.setBizContent("{\"out_trade_no\":\"" + 123 + "\","
                    + "\"total_amount\":\"" + 1.1 + "\","
                    + "\"subject\":\"" + subject + "\","
                    + "\"body\":\"" + body + "\","
                    + "\"timeout_express\":\"15m\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
//            AlipayRequest request2 = new AlipayTradeWapPayRequest();
//            AlipayObject o;


            System.out.println(request.getTextParams());
            AlipayClient defaultAlipayClient = new DefaultAlipayClient(certAlipayRequest);
            // AlipayTradeAppPayResponse response = defaultAlipayClient.sdkExecute(request);
            AlipayResponse alipayResponse = defaultAlipayClient.pageExecute(request);
            restul = alipayResponse.getBody();
        } catch (Exception e) {
            LOGGER.error("支付错误：", e);
        } finally {
            LOGGER.warn("返回值:" + restul);
        }

        return restul;
    }

    /**
     * 更新Order 包括redis和数据库
     *
     * @param majiangUserBean
     */
    public void updateUserOrder(MajiangUserBean majiangUserBean, String userPhone) throws Exception {
        LOGGER.warn("AlipayServiceImpl.updateUserOrder..........");
        //更新数据库
        System.out.println("将要更新的majianguserBean:"+majiangUserBean);
        majiangService.updateOrder(majiangUserBean);
        //先更新数据库再更新redis（redis不会会滚）
        redisUtils.set(ORDERKEY + "_" + DesUtil.encode(DesUtil.KEY, userPhone) + "_" + majiangUserBean.getMajiangKeyID(), majiangUserBean, ORDER_OUT_TIME + new Random().nextInt(120) + 60);
        redisUtils.set(ORDERKEY + "_" + DesUtil.encode(DesUtil.KEY, userPhone), majiangUserBean, ORDER_OUT_TIME + new Random().nextInt(120) + 60);
    }
}
