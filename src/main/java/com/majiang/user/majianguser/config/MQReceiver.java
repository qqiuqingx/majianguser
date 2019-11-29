package com.majiang.user.majianguser.config;

import com.google.gson.Gson;
import com.majiang.user.majianguser.bean.MajiangUserBean;
import io.lettuce.core.ScriptOutputType;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MQReceiver {
    //public Integer num=0;
    @RabbitHandler
    @RabbitListener(queues = MyMqConfig.QUEUE_NAME)
    public void majiangorder(String massage){
        MajiangUserBean majiangUserBean = new Gson().fromJson(massage, MajiangUserBean.class);
        System.out.println("majianguser:"+majiangUserBean);
   /*     if (majiangUserBean.getUserPhone().equals("18881014683")){

            throw new RuntimeException("测试异常");
        }*/

        System.out.println("mq队列里获取到的数据:"+massage);
    }
}
