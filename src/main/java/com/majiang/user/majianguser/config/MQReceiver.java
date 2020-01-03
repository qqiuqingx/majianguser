package com.majiang.user.majianguser.config;

import com.google.gson.Gson;
import com.majiang.user.majianguser.bean.MajiangUserBean;
import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.bean.majiangBean;
import com.majiang.user.majianguser.bean.vo.MajiangVo;
import com.majiang.user.majianguser.enums.MajiangUserOrderEnum;
import com.majiang.user.majianguser.enums.majiangEnum;
import com.majiang.user.majianguser.mapper.majiangMapper;
import com.majiang.user.majianguser.service.MajiangService;
import com.majiang.user.majianguser.service.impl.UserInfoserviceImpl;
import com.majiang.user.majianguser.utils.DesUtil;
import com.majiang.user.majianguser.utils.RedisUtils;
import com.rabbitmq.client.Channel;
import com.sun.xml.internal.bind.v2.TODO;
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
import java.util.stream.LongStream;

@Component
public class MQReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(MQReceiver.class);
    @Autowired
    private RedisUtils redisUtils;
    @Value("${majiang.redis.ORDERKEY}")
    private String ORDERKEY;
    @Value("${majiang.redis.ORDER_OUT_TIME}")
    private Long ORDER_OUT_TIME;
    @Autowired
    MajiangService majiangService;
    @RabbitHandler
    @RabbitListener(queues = MyMqConfig.QUEUE_NAME)
    //@Transactional  //为什么在这里加事务注解，只能入一条相同的数据
    public void majiangorder(String massage, Channel channel, Message messages) throws IOException {
        long start = System.currentTimeMillis();
        MajiangUserBean majiangUserBean = new Gson().fromJson(massage, MajiangUserBean.class);
        Integer majiangKeyID=majiangUserBean.getMajiangKeyID();
        String userPhone = majiangUserBean.getUserPhone();
        Integer newNum=null;
        System.out.println("majianguser:"+majiangUserBean);
        try {
            //这个地方的数据是在程序启动时CommandLineRunner.run中添加的，进行预减
            //num为自减1之后的结果
            if (redisUtils.decr(majiangKeyID.toString()) < 0) {
                redisUtils.set(majiangKeyID.toString(), 0);
                throw new  Exception("redis中麻将自减错误");

            }
            majiangBean majiang = majiangService.getMajiang(majiangKeyID);
            if (majiang.getNum() <= 0) {
                //数据库中桌数少于0了 说明桌数已满，直接结束方法
                return;
            }
            if (redisUtils.get(ORDERKEY+"_"+DesUtil.encode(DesUtil.KEY,userPhone)+majiangKeyID )!=null){
                System.out.println("已经存在了订单，直接结束方法");
                return;
            }
            System.out.println("要入库的majianguserBean:"+majiangUserBean);
            //  更新najianguserbean订单表的数据
            Integer count = majiangService.addAllMajiangUserBean(majiangUserBean);
            if (count != null) {
                redisUtils.set(userPhone + "_" + majiangKeyID, 1, 60 * 60 * 2);
                //将订单信息添加到redis中
                redisUtils.set(ORDERKEY+"_"+DesUtil.encode(DesUtil.KEY,userPhone) ,majiangUserBean,ORDER_OUT_TIME+new Random().nextInt(120)+60);
            }

            //  更新麻将表中的桌数
            newNum = (Integer) redisUtils.get(String.valueOf(majiangKeyID));
            System.out.println("redis中的num:"+newNum);
            if (null==newNum){throw new Exception("根据majiangkeyID获取到redis中麻将的桌数:"+newNum);}
            majiang.setNum(newNum);
            Integer integer = majiangService.updateMajiang(majiang);
            System.out.println("majiangService.updateMajiang的返回值:"+integer);
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
            long end = System.currentTimeMillis();
            System.out.println("消费者消费数据共："+(end-start)+"毫秒");
        }



    }
}
