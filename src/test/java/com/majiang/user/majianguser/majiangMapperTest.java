package com.majiang.user.majianguser;

import com.majiang.user.majianguser.bean.MajiangUserBean;
import com.majiang.user.majianguser.bean.majiangBean;
import com.majiang.user.majianguser.mapper.majiangMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class majiangMapperTest {

    @Autowired
    majiangMapper majiangMapper;
    @Test
    public  void getAll(){
        List<majiangBean> allmajiang = majiangMapper.getAllmajiang();
        allmajiang.stream().forEach(System.out::println);
    }
    @Test
    public  void add(){
        Integer integer = majiangMapper.addMajiang((majiangBean) new majiangBean().setMajiangNum(5).setIsDelete(0).setAddTime(new Date()).setModifyTime(new Date()));
        System.out.println(integer);
    }

    @Test
    public  void getAllmajianguser(){
        List<MajiangUserBean> allMajiangUserBean = majiangMapper.getAllMajiangUserBean();
        allMajiangUserBean.stream().forEach(System.out::println);
    }

    @Test
    public  void addmajianguser(){
        Integer integer = majiangMapper.addAllMajiangUserBean((MajiangUserBean) new MajiangUserBean().setMajiangKeyID(1).setUserPhone("123").setIsDelete(0).setAddTime(new Date()).setModifyTime(new Date()));
        System.out.println(integer);
    }

    @Test
    public  void getMajiang(){
        majiangBean majiang = majiangMapper.getMajiang(7);
        System.out.println(majiang);
    }
    @Test
    public  void getMajiangUser(){
        List<MajiangUserBean> muByKeyIDandUserPhone = majiangMapper.getMUByKeyIDandUserPhone("1", "123");
        System.out.println(muByKeyIDandUserPhone);

    }
}
