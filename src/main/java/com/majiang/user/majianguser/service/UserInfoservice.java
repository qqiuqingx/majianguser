package com.majiang.user.majianguser.service;

import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.bean.vo.UserVO;

public interface UserInfoservice {
    /*添加用户*/
    public UserVO insertUser(UserInfo userInfo);

    /*根据手机号查询用户信息*/
    public UserInfo selectUser(String phone);
}
