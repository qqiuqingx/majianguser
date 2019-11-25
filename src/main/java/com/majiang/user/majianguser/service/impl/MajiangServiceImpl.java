package com.majiang.user.majianguser.service.impl;

import com.majiang.user.majianguser.bean.MajiangUserBean;
import com.majiang.user.majianguser.bean.majiangBean;
import com.majiang.user.majianguser.bean.vo.MajiangVo;
import com.majiang.user.majianguser.enums.UserEnum;
import com.majiang.user.majianguser.enums.majiangEnum;
import com.majiang.user.majianguser.mapper.majiangMapper;
import com.majiang.user.majianguser.service.MajiangService;
import com.majiang.user.majianguser.utils.RedisUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import javax.servlet.http.Cookie;
import java.util.List;


@Service
public class MajiangServiceImpl implements MajiangService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MajiangServiceImpl.class);
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private majiangMapper majiangMapper;
    @Value("${majiang.redis.majiangs}")
    private String majiangs;

    @Autowired
    AmqpTemplate amqpTemplate ;

    @Override
    public MajiangVo getAllmajiang() {
        List<majiangBean> majiangs=null;
        //查缓存
        try {
            majiangs = (List) redisUtils.get(this.majiangs);
            if (majiangs == null) {
                majiangs = majiangMapper.getAllmajiang();
                if (majiangs!=null){
                    redisUtils.set(this.majiangs,majiangs,60*60*24);
                }
            }
        }catch (Exception e){
            LOGGER.error("获取所有桌数异常:",e);
            return new MajiangVo(UserEnum.application);
        }finally {
            LOGGER.warn("获取所有桌数:"+majiangs);
        }
        return new MajiangVo(UserEnum.SUCSS,0L,majiangs);

    }

    @Override
    public Integer addMajiang(majiangBean majiang) {
        return null;
    }

    @Override
    public MajiangVo getAllMajiangUserBean() {
        return null;
    }

    @Override
    public Integer addAllMajiangUserBean(MajiangUserBean majiangUserBean) {
        return null;
    }

    @Override
    public MajiangVo buyMajiang(String majiangKeyID, @CookieValue(required = false, value = "token") Cookie cookie) {
        LOGGER.warn("MajiangServiceImpl.buyMajiang>>>>>>>>>>>>:majiang:"+majiangKeyID+"cookie:"+cookie);
        System.out.println("SecurityUtils.getSubject().getSession():"+SecurityUtils.getSubject().getSession());
        System.out.println("redisUtils.get(cookie.getValue()):"+redisUtils.get(cookie.getValue()));
        if (SecurityUtils.getSubject().getSession()==null || redisUtils.get(cookie.getValue())==null){
            System.out.println("进入判断");
            return new MajiangVo(UserEnum.application);
        }
        //这个地方的数据是在程序启动时yCommandLineRunner.run中添加的，进行预减
        Integer num = redisUtils.decr(majiangKeyID);
        System.out.println("MajiangServiceImpl.buyMajiang获取redis中麻将桌对应的人数:"+redisUtils.get(majiangKeyID));
        if (num<1){
          return  new MajiangVo(majiangEnum.MAJIANGNUM);
        }
         amqpTemplate.convertAndSend("","");







        LOGGER.warn("MajiangServiceImpl.buyMajiang>>>>>>>>>>>>返回值:");
        return new MajiangVo(UserEnum.SUCSS);
    }
}
