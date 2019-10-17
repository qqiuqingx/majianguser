package com.majiang.user.majianguser;

import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.mapper.UserInfoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoMapperTest {

    @Autowired
    UserInfoMapper userInfoMapper;
    @Test
    public void selec(){
        List<UserInfo> userInfos = userInfoMapper.selectAllUser();
        for (UserInfo userInfo:userInfos){
            System.out.println(userInfo);
        }

        boolean mobileNO = isMobileNO("13312345");
        System.out.println(mobileNO);

    }
    public boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

}
