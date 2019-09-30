package com.majiang.user.majianguser.service.impl;

import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.bean.vo.UserVO;
import com.majiang.user.majianguser.enums.UserEnum;
import com.majiang.user.majianguser.enums.UserExceptionEnum;
import com.majiang.user.majianguser.exception.UserException;
import com.majiang.user.majianguser.mapper.UserInfoMapper;
import com.majiang.user.majianguser.service.UserInfoservice;
import com.majiang.user.majianguser.utils.DesUtil;
import com.majiang.user.majianguser.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserInfoserviceImpl implements UserInfoservice {


    @Autowired
    UserInfoMapper userInfoMapper;

    /*添加员工*/
    @Override
    public UserVO insertUser(UserInfo userInfo) throws UserException {
        System.out.println(userInfo);
        String desPhone = null;

        if (userInfo.getName() == null || "".equals(userInfo.getName())) {
            return new UserVO(UserEnum.UserNameNotNull);
        }
        if (userInfo.getPhone() == null || "".equals(userInfo.getPhone())) {
            return new UserVO(UserEnum.UserPhoneNotNull);
        }
        if (userInfo.getPassWord() == null || "".equals(userInfo.getPassWord())) {
            return new UserVO(UserEnum.UserPassWordNotNull);
        }
        //加密手机号
        desPhone = DesUtil.encode(DesUtil.KEY, userInfo.getPhone());
        //判断手机号是否已添加
        if (null != selecUser(desPhone)) {
            throw new UserException(UserExceptionEnum.UserPhoneNotOnly);
        }
        //加密密码
        userInfo.setPassWord(MD5.md5(userInfo.getPassWord()));
        System.out.println("加密后的密码：" + userInfo.getPassWord());
        //设置KeyID
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
        userInfo.setKeyID(simpleDateFormat.format(new Date()));
        userInfo.setPhone(desPhone);
        userInfo.setAddTime(new Date());
        userInfo.setModifTime(new Date());
        userInfo.setIsDelete(0);
        inserUser(userInfo);
        userInfo.setPassWord("");
        return new UserVO<UserInfo>(userInfo, UserEnum.SUCSS);
    }

    @Override
    public UserInfo selectUser(String phone) {
        return null;
    }

    private Integer inserUser(UserInfo userInfo) throws UserException {
        Integer integer = userInfoMapper.inserUser(userInfo);
        if (integer != 1) {
            throw new UserException(UserExceptionEnum.UserInserException);
        }
        return integer;
    }

    private UserInfo selecUser(String Phone) {
        UserInfo userInfo = userInfoMapper.selectUser(Phone);
        return userInfo;
    }
}
