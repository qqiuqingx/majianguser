package com.majiang.user.majianguser;

import com.majiang.user.majianguser.service.AlipayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class alipaySerbiceTest {

    @Autowired
    AlipayService alipayService;

    @Test
    public  void alipay(){
        try {
            String string = alipayService.webPagePay("1", 15, "什么");
            System.out.println(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
