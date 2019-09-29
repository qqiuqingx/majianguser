package com.majiang.user.majianguser;

import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.bean.vo.UserVO;
import com.majiang.user.majianguser.mapper.UserInfoMapper;
import com.majiang.user.majianguser.service.UserInfoservice;
import com.majiang.user.majianguser.service.impl.UserInfoserviceImpl;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        UserVO userVO = userInfoservice.insertUser(userInfo);
        System.out.println(userVO);
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


}
