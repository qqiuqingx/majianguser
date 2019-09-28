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
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static javafx.scene.input.KeyCode.L;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MajianguserApplicationTests {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    UserInfoservice userInfoservice;
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
        System.out.println( userInfoMapper.selectUser("13111019844"));
    }


}
