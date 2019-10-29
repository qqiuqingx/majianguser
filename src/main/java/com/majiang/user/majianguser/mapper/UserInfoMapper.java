package com.majiang.user.majianguser.mapper;

import com.majiang.user.majianguser.bean.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoMapper {

    /*添加用户*/
     Integer inserUser(UserInfo user);

    /*根据手机号查询用户*/
     UserInfo selectUser(String phone);

    /**
     * 查询所有用户
     */
     List<UserInfo> selectAllUser();

    /**
     * 添加用户角色关联表
     */
    Integer addUserRole(@Param("Phone") String Phone,@Param("roleNo")List<Integer> roleNo);
}
