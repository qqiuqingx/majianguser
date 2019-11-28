package com.majiang.user.majianguser.config;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MQReceiver {
    @Value("${majiang.mq.mqQueueName}")
    public String mqQueueName;
    public static final String Name="majiangQueue";
    @RabbitHandler
    @RabbitListener(queues = Name)
    public void majiangorder(String massage){
        System.out.println("mq队列里获取到的数据:"+massage);
        System.out.println("mq:"+mqQueueName);
        System.out.println("mq:"+mqQueueName.getClass());
    }
}
