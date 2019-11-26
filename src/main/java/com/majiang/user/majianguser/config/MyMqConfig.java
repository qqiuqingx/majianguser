package com.majiang.user.majianguser.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyMqConfig {

    @Value("${majiang.mq.mqQueueName}")
    private String mqQueueName;
    @Bean
    public Queue queue(){
        return new Queue("mqQueueName",true);
    }
}
