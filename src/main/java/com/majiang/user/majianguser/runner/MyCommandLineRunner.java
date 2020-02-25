package com.majiang.user.majianguser.runner;

import com.majiang.user.majianguser.bean.majiangBean;
import com.majiang.user.majianguser.mapper.majiangMapper;
import com.majiang.user.majianguser.service.impl.MajiangServiceImpl;
import com.majiang.user.majianguser.utils.RedisUtils;
import org.omg.CORBA.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.stream.Collectors;
import java.net.InetAddress;


@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private com.majiang.user.majianguser.mapper.majiangMapper majiangMapper;
    @Value("${majiang.redis.majiangs}")
    private String majiangs;
    @Value("${majiang.redis.MAJIANG_TIME_OUT}")
    private long MAJIANG_TIME_OUT;
    private static final Logger LOGGER = LoggerFactory.getLogger(MyCommandLineRunner.class);
    @Override
    public void run(String... args) throws Exception {
        LOGGER.warn("MyCommandLineRunner.run>>>>>>>>>>>>>>>>>>>.");
        List<majiangBean> allmajiang = majiangMapper.getAllmajiang();
        System.out.println("过期时间"+MAJIANG_TIME_OUT);
        //将整个麻将信息更新到redis
        redisUtils.set(majiangs,allmajiang,Long.valueOf(MAJIANG_TIME_OUT));
        //以MajiangNum为k   majiangBean为value 改为map
        Map<Integer,majiangBean> maps=allmajiang.stream().collect(Collectors.toMap(majiangBean::getMajiangNum,(p)->p));
        LOGGER.warn("MyCommandLineRunner.run:maps:"+maps);
        LOGGER.warn("MyCommandLineRunner.run:allmajiang:"+allmajiang);

        //只遍历value  将桌数更新到redis
        for(majiangBean ma:maps.values()){
            redisUtils.set(String.valueOf(ma.getKeyID()),ma.getNum(),MAJIANG_TIME_OUT);
        }


    }
}
