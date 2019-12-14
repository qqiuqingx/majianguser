package com.majiang.user.majianguser;

import com.google.gson.Gson;
import com.majiang.user.majianguser.bean.MajiangUserBean;
import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.bean.vo.UserReqVO;
import com.majiang.user.majianguser.bean.vo.UserVO;
import com.majiang.user.majianguser.mapper.UserInfoMapper;
import com.majiang.user.majianguser.service.UserInfoservice;
import com.majiang.user.majianguser.service.impl.UserInfoserviceImpl;
import com.majiang.user.majianguser.utils.BeanUtils;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.LocaleResolver;

import javax.sound.midi.Soundbank;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static javafx.scene.input.KeyCode.L;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MajianguserApplicationTests {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    UserInfoservice userInfoservice;

    @Autowired
    RedisTemplate redisTemplate;//K-V都是对象
    @Autowired
    StringRedisTemplate stringRedisTemplate;//K-V都是字符串
    @Test
    public void contextLoads() {
        UserInfo userInfo=new UserInfo();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
        userInfo.setKeyID(simpleDateFormat.format(new Date()));
        userInfo.setName("你回忆");
        userInfo.setPassWord("1234567");
        userInfo.setPhone("18881014683");
        userInfo.setAddTime(new Date());
        userInfo.setModifTime(new Date());
        System.out.println(new Date());
        userInfo.setIsDelete(0);
       // UserVO userVO = userInfoservice.insertUser(userInfo);
       // System.out.println(userVO);
    }
    @Test
    public  void ssa(){
        //System.out.println( userInfoMapper.selectUser("13111019844"));
        //操作字符串
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("123","123456", 10000,TimeUnit.SECONDS);
        System.out.println(valueOperations.get("123"));

        //redisTemplate.opsForValue().append("msg","呵呵大");
        redisTemplate.setValueSerializer(new GenericToStringSerializer<String>(String.class));
        Object msg = redisTemplate.opsForValue().get("msg");
        System.out.println(msg);
    }
    //测试Gson
    @Test
    public void json(){
       /* UserInfo userInfo=new UserInfo();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
        userInfo.setKeyID(simpleDateFormat.format(new Date()));
        userInfo.setName("你回忆");
        userInfo.setPassWord("1234567");
        userInfo.setPhone("18881014683");
        userInfo.setAddTime(new Date());
        userInfo.setModifTime(new Date());
        System.out.println(new Date());
        userInfo.setIsDelete(0);
        Gson gson = new Gson();
        String s = gson.toJson(userInfo);
        System.out.println(s);
        UserInfo userInfo1 = gson.fromJson(s, UserInfo.class);
        System.out.println(userInfo);*/
        String s1 = UUID.randomUUID().toString();
        System.out.println(s1);

    }
    @Test
    public  void testSelectAll(){
        List<UserInfo> userInfos = userInfoMapper.selectAllUser();
        for(UserInfo userInfo:userInfos){
            System.out.println(userInfo);
        }
    }


    @Test
    public  void upuser(){
        UserVO userVO = userInfoservice.upUser(new UserReqVO().setName("测试二号").setPassWord("11111").setPhone("18881014682"));
        System.out.println(userVO);
    }

    @Test
    public void testUtils(){
        MajiangUserBean majiangUserBean = new MajiangUserBean();
        majiangUserBean.setMajiangKeyID(123);
        System.out.println("属性处理前:"+majiangUserBean);
        BeanUtils.notNull(majiangUserBean,true);
        System.out.println("属性处理后:"+majiangUserBean);
    }


    @Test
    public  void updateUser(){
        List<UserReqVO> users =new ArrayList<>();
        users.add(new UserReqVO().setPhone("18881014680").setName("张三").setPassWord("123456"));
        users.add(new UserReqVO().setPhone("18881014681").setName("李四").setPassWord("123456"));
        users.add(new UserReqVO().setPhone("18881014684").setName("希尔瓦娜斯").setPassWord("123456"));
       try {

        for (UserReqVO userReqVO:users){
            UserVO userVO = userInfoservice.insertUser(userReqVO);
            System.out.println(userVO);
        }

       }catch (Exception e){
           System.out.println("异常"+e);
       }

    }
}
