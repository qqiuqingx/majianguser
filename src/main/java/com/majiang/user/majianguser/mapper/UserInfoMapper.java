package com.majiang.user.majianguser.mapper;

import com.majiang.user.majianguser.bean.UserInfo;

import java.util.List;

public interface UserInfoMapper {

    /*添加用户*/
    public Integer inserUser(UserInfo user);

    /*根据手机号查询用户*/
    public UserInfo selectUser(String phone);

    /**
     * 查询所有用户
     */
    public List<UserInfo> selectAllUser();
}
