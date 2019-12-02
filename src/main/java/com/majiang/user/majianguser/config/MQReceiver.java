package com.majiang.user.majianguser.config;

import com.google.gson.Gson;
import com.majiang.user.majianguser.bean.MajiangUserBean;
import com.majiang.user.majianguser.bean.majiangBean;
import com.majiang.user.majianguser.mapper.majiangMapper;
import com.majiang.user.majianguser.service.MajiangService;
import com.majiang.user.majianguser.utils.DesUtil;
import com.majiang.user.majianguser.utils.RedisUtils;
import io.lettuce.core.ScriptOutputType;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class MQReceiver {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    com.majiang.user.majianguser.mapper.majiangMapper majiangMapper;
    @Autowired
    MajiangService majiangService;
    @RabbitHandler
    @RabbitListener(queues = MyMqConfig.QUEUE_NAME)
   // @Transactional  为什么在这里加事务注解，只能入一条相同的数据
    public void majiangorder(String massage){
        MajiangUserBean majiangUserBean = new Gson().fromJson(massage, MajiangUserBean.class);
        String userPhone = majiangUserBean.getUserPhone();
        System.out.println("majianguser:"+majiangUserBean);
   /*     if (majiangUserBean.getUserPhone().equals("18881014683")){

            throw new RuntimeException("测试异常");
        }*/

        majiangBean majiang = majiangMapper.getMajiang(majiangUserBean.getMajiangKeyID());
        if (majiang.getNum()<=0){
            //数据库中桌数少于0了 说明桌数已满，直接结束方法
            return;
        }
        redisUtils.set(String.valueOf(majiangUserBean.getMajiangKeyID()),majiang.getNum());
        majiangUserBean.setUserPhone(DesUtil.encode(DesUtil.KEY,majiangUserBean.getUserPhone()));
        List<MajiangUserBean> muByKeyIDandUserPhone = majiangMapper.getMUByKeyIDandUserPhone(String.valueOf(majiangUserBean.getMajiangKeyID()), majiangUserBean.getUserPhone());
        if (muByKeyIDandUserPhone!=null && muByKeyIDandUserPhone.size()>=1){
            System.out.println("进入判断是否大于1》》》》》》》》》》》》》》》》》》》");
            redisUtils.set(userPhone+"_"+majiangUserBean.getMajiangKeyID(),1);
            return;
        }

        // TODO 查询redis确定
        // TODO 更新najianguserbean表的数据
        majiangService.addAllMajiangUserBean(majiangUserBean);
        // TODO 更新麻将表中的桌数

        // TODO 更新redis中的数据
        // TODO 返回？

        System.out.println("mq队列里获取到的数据:"+massage);
    }
}
