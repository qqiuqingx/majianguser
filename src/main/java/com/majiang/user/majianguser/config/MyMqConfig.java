package com.majiang.user.majianguser.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyMqConfig {

    @Bean
    public Queue queue(){
        return new Queue("majiangQueue",true);
    }
}
