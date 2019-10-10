package com.majiang.user.majianguser.service;

import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.bean.vo.UserReqVO;
import com.majiang.user.majianguser.bean.vo.UserVO;

public interface UserInfoservice {
    /*添加用户*/
    public UserVO insertUser(UserReqVO userInfo) throws Exception;

    /*根据手机号查询用户信息*/
    public UserInfo selectUser(String phone);

    /**
     * 用户手机号登录登录
     */
    UserVO userLogin(UserReqVO userInfo);
}
