package com.majiang.user.majianguser.service;

import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.bean.vo.UserReqVO;
import com.majiang.user.majianguser.bean.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserInfoservice {
    /*添加用户*/
    public UserVO insertUser(UserReqVO userInfo) throws Exception;

    /*根据手机号查询用户信息*/
    public UserInfo selectUser(String phone);

    /**
     * 用户手机号登录登录
     */
    UserVO userLogin(UserReqVO userInfo, HttpServletResponse response) ;


    /**
     * 查询所有用户
     */
    UserVO selectAllUser();
}
