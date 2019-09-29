package com.majiang.user.majianguser.service.impl;

import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.bean.vo.UserVO;
import com.majiang.user.majianguser.enums.UserExceptionEnum;
import com.majiang.user.majianguser.exception.UserException;
import com.majiang.user.majianguser.mapper.UserInfoMapper;
import com.majiang.user.majianguser.service.UserInfoservice;
import com.majiang.user.majianguser.utils.DesUtil;
import com.majiang.user.majianguser.utils.MD5;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserInfoserviceImpl implements UserInfoservice {


    @Autowired
    UserInfoMapper userInfoMapper;
    /*添加员工*/
    @Override
    public UserVO insertUser(UserInfo userInfo) throws UserException{

        UserVO userVO=new UserVO<UserInfo>();
        String desPhone=null;

            if (userInfo.getName()==null||"".equals(userInfo.getName())){
                throw  new UserException(UserExceptionEnum.UserNameNotNull);
            }
            if (userInfo.getPhone()==null||"".equals(userInfo.getPhone())){
                throw  new UserException(UserExceptionEnum.UserPhoneNotNull);
            }
            if(userInfo.getPassWord()==null||"".equals(userInfo.getPassWord())){
                throw  new UserException(UserExceptionEnum.UserPassWordNotNull);
            }
            //加密手机号
            desPhone= DesUtil.encode(DesUtil.KEY,userInfo.getPhone());
           //判断手机号是否已添加
            if (null!=selecUser(desPhone)) {
                throw  new UserException(UserExceptionEnum.UserPhoneNotOnly);
            }
            //加密密码
            userInfo.setPassWord(MD5.md5(userInfo.getPassWord()));
            System.out.println("加密后的密码："+userInfo.getPassWord());
            userInfo.setPhone(desPhone);
            inserUser(userInfo);
           userVO=new  UserVO<UserInfo>();
            userVO.setT(userInfo);
            userVO.setCode(1000);

        return userVO;
    }

    @Override
    public UserInfo selectUser(String phone) {
        return null;
    }

    private Integer inserUser(UserInfo userInfo) throws UserException {
        Integer integer=userInfoMapper.inserUser(userInfo);
        if (integer!=1){
            throw new UserException(UserExceptionEnum.UserInserException);
        }
        return  integer;
    }

    private UserInfo selecUser(String Phone){
        UserInfo userInfo = userInfoMapper.selectUser(Phone);
        return userInfo;
    }
}
