package com.majiang.user.majianguser.config;

import com.google.gson.Gson;
import com.majiang.user.majianguser.bean.MajiangUserBean;
import com.majiang.user.majianguser.bean.majiangBean;
import com.majiang.user.majianguser.bean.vo.MajiangVo;
import com.majiang.user.majianguser.mapper.majiangMapper;
import com.majiang.user.majianguser.service.MajiangService;
import com.majiang.user.majianguser.service.impl.UserInfoserviceImpl;
import com.majiang.user.majianguser.utils.DesUtil;
import com.majiang.user.majianguser.utils.RedisUtils;
import com.rabbitmq.client.Channel;
import io.lettuce.core.ScriptOutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
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
    //@Transactional  //为什么在这里加事务注解，只能入一条相同的数据
    public void majiangorder(String massage, Channel channel, Message messages) throws IOException {
        MajiangUserBean majiangUserBean = new Gson().fromJson(massage, MajiangUserBean.class);
        Integer majiangKeyID=majiangUserBean.getMajiangKeyID();
        String userPhone = majiangUserBean.getUserPhone();
        Integer newNum=null;
        System.out.println("majianguser:"+majiangUserBean);
        try {
            majiangBean majiang = majiangService.getMajiang(majiangKeyID);
            if (majiang.getNum() <= 0) {
                //数据库中桌数少于0了 说明桌数已满，直接结束方法
                return;
            }
            //更新麻将桌数
           // redisUtils.set(String.valueOf(majiangUserBean.getMajiangKeyID()), majiang.getNum());
            majiangUserBean.setUserPhone(DesUtil.encode(DesUtil.KEY, userPhone));
            //根据用户手机和麻将KeyID来获取订单
            MajiangVo majiangVo = majiangService.getMUByKeyIDandUserPhone(String.valueOf(majiangKeyID), majiangUserBean.getUserPhone());
            List<MajiangUserBean> muByKeyIDandUserPhone = (List)majiangVo.getDate();
            if (muByKeyIDandUserPhone != null && muByKeyIDandUserPhone.size() >= 1) {
                System.out.println("进入判断是否大于1》》》》》》》》》》》》》》》》》》》");
                redisUtils.set(userPhone + "_" + majiangKeyID, 1, 60 * 2 * 60);
                return;
            }
            //  更新najianguserbean订单表的数据
            Integer count = majiangService.addAllMajiangUserBean(majiangUserBean);
            if (count != null) {
                System.out.println("添加成功addAllMajiangUserBean:" + count);
                redisUtils.set(userPhone + "_" + majiangKeyID, 1, 60 * 60 * 2);
            }
            //  更新麻将表中的桌数
            newNum = (Integer) redisUtils.get(String.valueOf(majiangKeyID));
            System.out.println("redis中的num:"+newNum);
            majiang.setNum(newNum);
            majiangService.updateMajiang(majiang);
            //  更新redis中的数据
            redisUtils.set(String.valueOf(majiangKeyID), newNum);
            //手动确认消息，需要先配置:acknowledge-mode: manual
            channel.basicAck(messages.getMessageProperties().getDeliveryTag(),false);
        }catch (Exception e){
            LOGGER.error("消费者错误:",e);
           // TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            //未收到消息
            channel.basicNack(messages.getMessageProperties().getDeliveryTag(),false,false);
        }finally {
            LOGGER.warn("mq队列里获取到的数据:"+massage);
        }



    }
}
