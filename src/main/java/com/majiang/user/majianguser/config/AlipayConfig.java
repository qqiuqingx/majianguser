package com.majiang.user.majianguser.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
/**
 *给静态属性赋值一定要在类上加@Component并且使用set方法设置
 */
@Component
public class AlipayConfig {


    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id;
    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key;
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key;
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url;
    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url;
    // 签名方式
    public static String sign_type;
    // 字符编码格式
    public static  String charset;
    // 支付宝网关
    //	https://openapi.alipay.com/gateway.do
    public  static String gatewayUrl;

    @Value("${majiang.alipay.ALI_APPID}")
    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }
    @Value("${majiang.alipay.ALI_MERCHANT_PRIVATE_KEY}")
    public void setMerchant_private_key(String merchant_private_key) {
        this.merchant_private_key = merchant_private_key;
    }
    @Value("${majiang.alipay.ALI_ALIPAY_PUBLIC_KEY}")
    public void setAlipay_public_key(String alipay_public_key) {
        this.alipay_public_key = alipay_public_key;
    }
    @Value("${majiang.alipay.ALI_NOTIFY_URL}")
    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }
    @Value("${majiang.alipay.ALI_RETURN_URL}")
    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }
    @Value("${majiang.alipay.ALI_SIGN_TYPE}")
    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }
    @Value("${majiang.alipay.ALI_CHARSET}")
    public void setCharset(String charset) {
        this.charset = charset;
    }
    @Value("majiang.alipay.ALI_GATEWAYURL")
    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }







    public static String log_path = "C:\\";



    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
