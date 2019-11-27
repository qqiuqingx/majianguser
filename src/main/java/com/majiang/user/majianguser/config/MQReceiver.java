package com.majiang.user.majianguser.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class MQReceiver {
    @Value("${majiang.mq.mqQueueName}")
    public String mqQueueName;

    @RabbitListener(queues = mqQueueName)
    public void majiangorder(String massage){

    }
}
