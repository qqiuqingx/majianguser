package com.majiang.user.majianguser.mapper;

import com.majiang.user.majianguser.bean.UserInfo;

public interface UserInfoMapper {

    /*添加用户*/
    public Integer inserUser(UserInfo user);

    /*根据手机号查询用户*/
    public UserInfo selectUser(String phone);

}
