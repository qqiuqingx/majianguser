package com.majiang.user.majianguser.config;

import com.google.gson.Gson;
import com.majiang.user.majianguser.bean.MajiangUserBean;
import com.majiang.user.majianguser.bean.majiangBean;
import com.majiang.user.majianguser.mapper.majiangMapper;
import com.majiang.user.majianguser.service.MajiangService;
import com.majiang.user.majianguser.service.impl.UserInfoserviceImpl;
import com.majiang.user.majianguser.utils.DesUtil;
import com.majiang.user.majianguser.utils.RedisUtils;
import io.lettuce.core.ScriptOutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Random;

@Component
public class MQReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(MQReceiver.class);
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    MajiangService majiangService;
    @RabbitHandler
    @RabbitListener(queues = MyMqConfig.QUEUE_NAME)
    @Transactional  //为什么在这里加事务注解，只能入一条相同的数据
    public void majiangorder(String massage){
        MajiangUserBean majiangUserBean = new Gson().fromJson(massage, MajiangUserBean.class);
        String userPhone = majiangUserBean.getUserPhone();
        System.out.println("majianguser:"+majiangUserBean);
        try {
            majiangBean majiang = majiangService.getMajiang(majiangUserBean.getMajiangKeyID());
            if (majiang.getNum() <= 0) {
                //数据库中桌数少于0了 说明桌数已满，直接结束方法
                return;
            }
            //更新麻将桌数
            redisUtils.set(String.valueOf(majiangUserBean.getMajiangKeyID()), majiang.getNum());
            majiangUserBean.setUserPhone(DesUtil.encode(DesUtil.KEY, majiangUserBean.getUserPhone()));
            //根据用户手机和麻将KeyID来获取订单
            List<MajiangUserBean> muByKeyIDandUserPhone = majiangService.getMUByKeyIDandUserPhone(String.valueOf(majiangUserBean.getMajiangKeyID()), majiangUserBean.getUserPhone());
            if (muByKeyIDandUserPhone != null && muByKeyIDandUserPhone.size() >= 1) {
                System.out.println("进入判断是否大于1》》》》》》》》》》》》》》》》》》》");
                redisUtils.set(userPhone + "_" + majiangUserBean.getMajiangKeyID(), 1, 60 * 2 * 60);
                return;
            }
            // TODO 更新najianguserbean订单表的数据
            Integer integer = majiangService.addAllMajiangUserBean(majiangUserBean);
            if (integer != null) {
                System.out.println("添加成功addAllMajiangUserBean:" + integer);
                redisUtils.set(userPhone + "_" + majiangUserBean.getMajiangKeyID(), 1, 60 * 60 * 2);
            }
            // TODO 更新麻将表中的桌数
            Integer newNum = (Integer) redisUtils.get(String.valueOf(majiangUserBean.getMajiangKeyID()));
            majiang.setNum(newNum);
            majiangService.updateMajiang(majiang);
            // TODO 更新redis中的数据
            // TODO 返回？
        }catch (Exception e){
            LOGGER.error("消费者错误:",e);
           // TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }finally {
            System.out.println("mq队列里获取到的数据:"+massage);
        }



    }
}
