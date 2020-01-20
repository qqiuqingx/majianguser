package com.majiang.user.majianguser;

import com.majiang.user.majianguser.bean.MajiangUserBean;
import com.majiang.user.majianguser.config.AlipayConfig;
import com.majiang.user.majianguser.service.AlipayService;
import com.majiang.user.majianguser.service.impl.AlipayServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class alipaySerbiceTest {

    @Autowired
    AlipayService alipayService;

    @Test
    public  void alipay(){
        try {
           /* String string = alipayService.webPagePay("1", 15, "什么");
            System.out.println(string);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void al(){
        try {
            String property = System.getProperty("user.dir");
            System.out.println(property);
            System.out.println("设置应用公钥证书路径app_cert_path:"+ AlipayConfig.app_cert_path);
           String s = alipayService.webPagePay(3, "86fa07ef-9cb9-4e71-a359-a060670e7e1b");
          System.out.println("测试调："+s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void  tetup(){


        AlipayServiceImpl alipayService = new AlipayServiceImpl();
        try {

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
