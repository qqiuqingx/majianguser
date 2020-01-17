package com.majiang.user.majianguser.service.impl;

import com.google.gson.Gson;
import com.majiang.user.majianguser.bean.MajiangUserBean;
import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.bean.majiangBean;
import com.majiang.user.majianguser.bean.vo.MajiangVo;
import com.majiang.user.majianguser.config.MyMqConfig;
import com.majiang.user.majianguser.enums.MajiangUserOrderEnum;
import com.majiang.user.majianguser.enums.UserEnum;
import com.majiang.user.majianguser.enums.majiangEnum;
import com.majiang.user.majianguser.mapper.majiangMapper;
import com.majiang.user.majianguser.service.MajiangService;
import com.majiang.user.majianguser.service.UserInfoservice;
import com.majiang.user.majianguser.utils.BeanUtils;
import com.majiang.user.majianguser.utils.DesUtil;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class MajiangServiceImpl implements MajiangService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MajiangServiceImpl.class);
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    UserInfoservice userInfoservice;
    @Autowired
    private majiangMapper majiangMapper;

    @Value("${majiang.redis.majiangs}")
    private String majiangs;
    @Value("${majiang.redis.ORDERKEY}")
    private String ORDERKEY;
    @Value("${majiang.redis.ORDER_OUT_TIME}")
    private Long ORDER_OUT_TIME;
    @Autowired
    AmqpTemplate amqpTemplate;

    @Override
    public MajiangVo getAllmajiang() {
        List<majiangBean> majiangs = null;
        //查缓存
        try {
            majiangs = (List) redisUtils.get(this.majiangs);
            if (majiangs == null) {
                majiangs = majiangMapper.getAllmajiang();
                if (majiangs != null) {
                    redisUtils.set(this.majiangs, majiangs, 60 * 60 * 24);
                }
            }
        } catch (Exception e) {
            LOGGER.error("获取所有桌数异常:", e);
            return new MajiangVo(UserEnum.application);
        } finally {
            LOGGER.warn("获取所有桌数:" + majiangs);
        }
        return new MajiangVo(UserEnum.SUCSS, 0L, majiangs);

    }

    @Override
    public Integer addMajiang(majiangBean majiang) {
        return null;
    }

    @Override
    public majiangBean getMajiang(Integer KeyID) {
        return majiangMapper.getMajiang(KeyID);
    }



    @Override
    public MajiangVo getMUByKeyIDandUserPhone(String majiangKeyID, String UserPhone, Integer OrderStatus) {
        List<MajiangUserBean> muByKeyIDandUserPhone = new ArrayList<>();
        MajiangVo majiangVo=null;
        long length = 0;
        try {
            MajiangUserBean m = (MajiangUserBean) redisUtils.get(ORDERKEY + "_" + UserPhone + "_" + majiangKeyID);
            if (m != null) {
                muByKeyIDandUserPhone.add(m);
                length = (long) new Gson().toJson(muByKeyIDandUserPhone).length();
                System.out.println("从redis中获取到订单:"+m);
            } else {
                muByKeyIDandUserPhone = majiangMapper.getMUByKeyIDandUserPhone(majiangKeyID, DesUtil.decode(DesUtil.KEY,UserPhone));
            /*    if (muByKeyIDandUserPhone.size()<=0){
                    majiangVo=new MajiangVo(majiangEnum.MAJIANGNUM);
                    return majiangVo;
                }*/
                length= (long) new Gson().toJson(muByKeyIDandUserPhone).length();

                System.out.println("从数据库中获取到订单:"+muByKeyIDandUserPhone);
            }

                // 过滤状态不为orderStatus的逻辑，也可以在sql中添加条件判断
            if (OrderStatus != null&&OrderStatus>=0&&OrderStatus<=4) {
               muByKeyIDandUserPhone = muByKeyIDandUserPhone.stream().filter(mu -> mu.getStatus() == OrderStatus).collect(Collectors.toList());
               if (muByKeyIDandUserPhone.size()<=0){
                   majiangVo=new MajiangVo(majiangEnum.DEFEATED);
                   return majiangVo ;
               }
            }
            redisUtils.set(ORDERKEY + "_" + UserPhone + "_" + majiangKeyID,muByKeyIDandUserPhone.get(0),ORDER_OUT_TIME+new Random().nextInt(120)+60);
            majiangVo=new MajiangVo(UserEnum.SUCSS,length,muByKeyIDandUserPhone);
        }catch (Exception e){
            LOGGER.error("获取订单异常:",e);
            e.printStackTrace();
        }finally {
            LOGGER.warn("获取"+UserPhone+"对应订单,桌数为:"+majiangKeyID+",返回值:"+muByKeyIDandUserPhone);
        }
        return majiangVo;
    }

    @Override
    public MajiangVo getAllMajiangUserBean(String token) {
        MajiangVo majiangVo=null;
        List<MajiangUserBean> allOrder=null;
        UserInfo userInfo=null;
        try{
            userInfo=redisUtils.getUser(token);

            allOrder = majiangMapper.getAllMajiangUserBean(DesUtil.decode(DesUtil.KEY,userInfo.getPhone()));
            if(allOrder.size()<=0){
                majiangVo=new MajiangVo(majiangEnum.NO_ORDER);
                return majiangVo;
            }
            //排序，按照订单状态进行排，升序
            allOrder=allOrder.stream().sorted(Comparator.comparing(MajiangUserBean::getStatus)).collect(Collectors.toList());
            majiangVo=new MajiangVo(UserEnum.SUCSS,100L,allOrder);
        }catch (Exception e){
            LOGGER.error("系统错误",e);
            majiangVo=new MajiangVo(UserEnum.application);
        }finally {
            LOGGER.warn(userInfo.getName()+"获取所有订单:"+allOrder);
        }
        return majiangVo;
    }

    @Override
    public Integer addAllMajiangUserBean(MajiangUserBean majiangUserBean) {
        Integer integer = majiangMapper.addAllMajiangUserBean(majiangUserBean);
        System.out.println("addAllMajiangUserBeanf返回值》》》》》》》》》" + integer);
        return integer;
    }

    @Override
    public Integer updateMajiang(majiangBean majiangBean) {
        LOGGER.warn("MajiangServiceImpl.updateMajiang" + majiangBean);
        return majiangMapper.updateMajiang(majiangBean);
    }


    /**
     * 点击订之后
     *
     * @param majiangKeyID
     * @param cookie
     * @return 返回是否成功
     */
    @Override
    //@Transactional(rollbackFor = {Exception.class})
    public MajiangVo buyMajiang(String majiangKeyID, @CookieValue(required = false, value = "token") Cookie cookie,Integer num) {
        LOGGER.warn("MajiangServiceImpl.buyMajiang>>>>>>>>>>>>:majiang:" + majiangKeyID + "cookie:" + cookie);
        MajiangUserBean majiangUserBean = null;
        try {
            UserInfo userInfo = (UserInfo) redisUtils.get(cookie.getValue());
            if (SecurityUtils.getSubject().getSession() == null || userInfo == null) {
                System.out.println("进入判断");
                return new MajiangVo(majiangEnum.LOGINFORNOW);
            }
            majiangUserBean = new MajiangUserBean().setMajiangKeyID(Integer.valueOf(majiangKeyID)).setUserPhone(userInfo.getPhone());
            //判断是否重复预定
            Object o = redisUtils.get(userInfo.getPhone() + "_" + majiangKeyID);
            if (o != null) {
                return new MajiangVo(majiangEnum.REPEAT);
            }
            UserInfo userInfo1=(UserInfo)redisUtils.get(DesUtil.encode(DesUtil.KEY,majiangUserBean.getUserPhone()));
            if (null ==userInfo1 ) {
                userInfo1=userInfoservice.selectUser(DesUtil.encode(DesUtil.KEY,majiangUserBean.getUserPhone()));
            }
            majiangUserBean.setUserName(userInfo1.getName());
            //设置订单状态
            majiangUserBean.setStatusandName(MajiangUserOrderEnum.ORDER_STAY_PAY);
            //设置数量
            majiangUserBean.setNum(num);
            //设置单价
            majiangUserBean.setPrice(new BigDecimal(15.00D).setScale(2));
            //设置总价
            double sumprice=15.00D*num;
            majiangUserBean.setSumPrice(new BigDecimal(sumprice).setScale(2));
            BeanUtils.notNull(majiangUserBean, true);
            amqpTemplate.convertAndSend(MyMqConfig.QUEUE_NAME, new Gson().toJson(majiangUserBean));
        } catch (Exception e) {
            LOGGER.error("点击定桌时异常:", e);
            return new MajiangVo(UserEnum.application);
        } finally {
            LOGGER.warn("要订桌的用户:" + majiangUserBean);
        }
        return new MajiangVo(UserEnum.SUCSS);
    }
}
