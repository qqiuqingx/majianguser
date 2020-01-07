package com.majiang.user.majianguser.config;

import org.springframework.beans.factory.annotation.Value;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {

    @Value("majiang.alipay.APPID")
    private static String ALI_APPID;

    @Value("majiang.alipay.ALI_MERCHANT_PRIVATE_KEY")
    private static String ALI_MERCHANT_PRIVATE_KEY;

    @Value("majiang.alipay.ALI_ALIPAY_PUBLIC_KEY")
    private static String ALI_ALIPAY_PUBLIC_KEY;

    @Value("majiang.alipay.ALI_NOTIFY_URL")
    private static String ALI_NOTIFY_URL;

    @Value("majiang.alipay.ALI_RETURN_URL")
    private static String ALI_RETURN_URL;

    @Value("majiang.alipay.ALI_SIGN_TYPE")
    private static String ALI_SIGN_TYPE;

    @Value("majiang.alipay.ALI_CHARSET")
    private static String ALI_CHARSET;

    @Value("majiang.alipay.ALI_GATEWAYURL")
    private static String ALI_GATEWAYURL;

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = ALI_APPID;

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = ALI_MERCHANT_PRIVATE_KEY;

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = ALI_ALIPAY_PUBLIC_KEY;

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = ALI_NOTIFY_URL;

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = ALI_RETURN_URL;

    // 签名方式
    public static String sign_type = ALI_SIGN_TYPE;

    // 字符编码格式
    public static String charset = ALI_CHARSET;

    // 支付宝网关
    public static String gatewayUrl = ALI_GATEWAYURL;
    //	https://openapi.alipay.com/gateway.do


    // 支付宝网关
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
