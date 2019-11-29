package com.majiang.user.majianguser.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyMqConfig {

    public static final String QUEUE_NAME="majiangQueue";
    @Bean
    public Queue queue(){
        return new Queue(QUEUE_NAME,true);
    }
}
