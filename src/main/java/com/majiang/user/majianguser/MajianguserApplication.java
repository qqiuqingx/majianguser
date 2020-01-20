package com.majiang.user.majianguser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.majiang.user.majianguser.mapper")
@SpringBootApplication
@EnableRabbit
@EnableTransactionManagement//开启事务
public class MajianguserApplication {

    public static void main(String[] args) {
        SpringApplication.run(MajianguserApplication.class, args);
    }

}
