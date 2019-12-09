package com.majiang.user.majianguser;

import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.mapper.UserInfoMapper;
import io.lettuce.core.ScriptOutputType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
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

    @Test
    public void add() {
        List<Integer> asd = new ArrayList<>();
        asd.add(3);
        asd.add(4);
        asd.add(5);
        /*Integer integer = userInfoMapper.addUserRole("4EB61209FDE1D883CED65465F0C48295", asd);
        System.out.println(integer);*/
    }

    @Test
    public void upuser() {

        Integer integer = userInfoMapper.upUser(new UserInfo().setName("hah").
                setPhone("4EB61209FDE1D883D01BA5FC85C154AA").setPassWord("123456789").setIsDelete(0));
        /*4EB61209FDE1D8830C6A7D811CD07067*/
        System.out.println(integer);
    }

}
