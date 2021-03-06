package com.majiang.user.majianguser.cong;

import com.alipay.api.internal.util.AlipaySignature;
import com.majiang.user.majianguser.bean.vo.OrderVO;
import com.majiang.user.majianguser.config.AlipayConfig;
import com.majiang.user.majianguser.service.AlipayService;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/ali")
public class AlipayController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlipayController.class);
    @Autowired
    private AlipayService alipayService;
    @Autowired
    private RedisUtils redisUtils;

    //https://gitee.com/javen205/IJPay
    @RequestMapping(value = "/goPay", method = RequestMethod.POST)
    @ResponseBody
    public String goPay(@CookieValue(value = "token") Cookie cookie, OrderVO orderVO, HttpServletResponse response) {
        System.out.println("表单中的数据:" + orderVO);
        String returnHtml = "";
        if (cookie != null) {
            returnHtml = alipayService.webPagePay(orderVO.getMajiangKeyID(), cookie.getValue());
        } else {
            LOGGER.warn("MajiangController.getMU未进入service层");
            returnHtml = "1";
        }
        return returnHtml;
    }

    /**

     * @Description: 支付宝同步通知页面
     */
    @RequestMapping(value = "/alipayReturnNotice",method = RequestMethod.GET)
    public String alipayReturnNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {

        LOGGER.warn("支付成功, 进入同步通知接口controller层...");

        alipayService.alipayReturnNotice(request, response);

        return "page/UserAllOrder";
    }


    /**
     * @Description: 支付宝异步 通知页面
     */
    @RequestMapping(value = "/alipayNotifyNotice", method = RequestMethod.POST,produces = "text/plain;charset=gbk")
    @ResponseBody
    public String alipayNotifyNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {

        LOGGER.warn("支付成功, 进入异步通知接口...");
        System.out.println("异步通知》》》》》》》》》》》》》》》》》》》。");
        //获取支付宝POST过来反馈信息
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
            System.out.println("转码前的name:" + name + ",转码前的value：" + valueStr);
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            //LOGGER.warn("转码后的name:" + name + ",转码后的value：" + valueStr);
            params.put(name, valueStr);
        }
        System.out.println("异步验签的params");
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key_rsa, AlipayConfig.charset, AlipayConfig.sign_type_rsa); //调用SDK验证签名
        System.out.println("验签返回值:" + signVerified);
        //——请在这里编写您的程序（以下代码仅作参考）——

		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
        if (signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            if (trade_status.equals("TRADE_FINISHED")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意： 尚自习的订单没有退款功能, 这个条件判断是进不来的, 所以此处不必写代码
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知

                // 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水
                /*orderService.updateOrderStatus(out_trade_no, trade_no, total_amount);

                Orders order = orderService.getOrderById(out_trade_no);
                Product product = productService.getProductById(order.getProductId());

                log.info("********************** 支付成功(支付宝异步通知) **********************");
                log.info("* 订单号: {}", out_trade_no);
                log.info("* 支付宝交易号: {}", trade_no);
                log.info("* 实付金额: {}", total_amount);
                log.info("* 购买产品: {}", product.getName());
                log.info("***************************************************************");*/
            }
            LOGGER.warn("支付成功...");

        } else {//验证失败
            LOGGER.warn("支付, 验签失败...");
        }

        return "success";
    }

}
