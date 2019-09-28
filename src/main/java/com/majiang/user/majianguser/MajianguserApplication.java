package com.majiang.user.majianguser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.majiang.user.majianguser.mapper")
@SpringBootApplication
public class MajianguserApplication {

    public static void main(String[] args) {
        SpringApplication.run(MajianguserApplication.class, args);
    }

}
