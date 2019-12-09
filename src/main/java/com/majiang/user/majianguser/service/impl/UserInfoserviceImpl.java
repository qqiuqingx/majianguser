package com.majiang.user.majianguser.service.impl;

import com.google.gson.Gson;
import com.majiang.user.majianguser.bean.Role;
import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.bean.vo.UserReqVO;
import com.majiang.user.majianguser.bean.vo.UserVO;
import com.majiang.user.majianguser.enums.UserEnum;
import com.majiang.user.majianguser.enums.UserExceptionEnum;
import com.majiang.user.majianguser.exception.UserException;
import com.majiang.user.majianguser.exception.majiangRunTimeException;
import com.majiang.user.majianguser.mapper.UserInfoMapper;
import com.majiang.user.majianguser.service.RoleService;
import com.majiang.user.majianguser.service.UserInfoservice;
import com.majiang.user.majianguser.utils.*;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Service
public class UserInfoserviceImpl implements UserInfoservice {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoserviceImpl.class);

    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    RoleService roleService;

    @Autowired
    RedisUtils redisUtils;

    /*添加员工*/
    @Transactional
    @Override
    public UserVO insertUser(UserReqVO userReqVO) throws Exception {
        UserInfo userInfo = null;
        UserVO userVO = null;
        List<Role> roles=null;
        try {
            Logger logger = LoggerFactory.getLogger(getClass());
            System.out.println("用户传进来的:" + userReqVO);
            userInfo = new UserInfo();
            String desPhone = null;
            //将参数一的内容复制到参数二
            BeanUtils.copyProperties(userReqVO, userInfo);
            System.out.println("复制后的INFO：" + userInfo);
            System.out.println("复制后的VO:" + userReqVO);
            if (userInfo.getName() == null || "".equals(userInfo.getName())) {
                return new UserVO(UserEnum.UserNameNotNull);
            }
            if (userInfo.getPhone() == null || "".equals(userInfo.getPhone())) {
                return new UserVO(UserEnum.UserPhoneNotNull);
            }
            if (userInfo.getPassWord() == null || "".equals(userInfo.getPassWord())) {
                return new UserVO(UserEnum.UserPassWordNotNull);
            }

            if (!BeanUtils.isMobileNO(userInfo.getPhone())) {
                return new UserVO(UserEnum.PhoneNotre);
            }
            //加密手机号
            desPhone = DesUtil.encode(DesUtil.KEY, userInfo.getPhone());
            //判断手机号是否已添加
            if (null != selecUser(desPhone)) {
                throw new UserException(UserExceptionEnum.UserPhoneNotOnly);
            }
            //加密密码
            userInfo.setPassWord(MD5.md5(userInfo.getPassWord())).setPhone(desPhone);
            System.out.println("加密后的密码：" + userInfo.getPassWord());
            //todo 增加权限逻辑
            roles.add(new Role().setName("user"));
            roleService.addUserRole(userInfo.getPhone(),roles);
            //userInfo.setPhone(desPhone);
            //设置公共属性的值
            BeanUtils.setXXX(userInfo);
            inserUser(userInfo);
            userInfo.setPassWord("");
            userVO = new UserVO<UserInfo>(userInfo, UserEnum.SUCSS);
            return userVO;

        } catch (majiangRunTimeException exception) {
            LOGGER.error("错误:" + exception.getCode() + ",原因:" + exception.getMessage());
            //事务手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new UserException(UserExceptionEnum.UserPhoneNotOnly);
        } catch (Exception e) {
            LOGGER.error("错误:", e);
            e.printStackTrace();
            //事务手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception(e);
        } finally {
            LOGGER.warn("添加的用户为：" + userInfo);
            LOGGER.warn("返回值：" + userVO);

        }

    }

    /**
     *
     */
    public UserVO userLogin(UserReqVO userInfo, HttpServletResponse response) {
        String newToken = TokenUtil.getNewToken();
        Cookie cookie = new Cookie("token", newToken);
        response.addCookie(cookie);
        userInfo.getPhone();
        UserInfo userInfo1 = new UserInfo();
        BeanUtils.copyProperties(userInfo, userInfo1);
        userInfo1.setPassWord("");
        redisUtils.set(newToken, userInfo1, 60 * 60 * 2);
        return null;

    }


    public UserVO selectAllUser() {
        LOGGER.warn("查询所有用户》》》》》》》》》》》》》》》》》》");
        List<UserInfo> userInfos = null;
        try {
            userInfos = selectAllU();
            if (null == userInfos) {
                return new UserVO(UserEnum.NoUser);
            }
            for (UserInfo userInfo : userInfos) {
                userInfo.setPassWord("");
                userInfo.setPhone(DesUtil.decode(DesUtil.KEY, userInfo.getPhone()));
            }
            new Gson().toJson(userInfos).length();
        } catch (Exception e) {
            LOGGER.error("错误:", e);
        } finally {
            LOGGER.warn("查询所有的用户:" + userInfos);
        }

        return new UserVO<List<UserInfo>>(userInfos, UserEnum.SUCSS).setCount((long) new Gson().toJson(userInfos).length());
    }


    /**
     * 退出
     */
    public boolean outApp(String token) {
        LOGGER.warn("UserInfoserviceImpl.outApp（）》》》》》》》");
        UserInfo userInfo = null;
        try {
            userInfo = (UserInfo) redisUtils.get(token);
            LOGGER.warn("用户登出service层获取redis中用户的数据:" + userInfo);
            if (null != userInfo)
                redisUtils.delete(token);
            //停止shiro的session
            SecurityUtils.getSubject().getSession().stop();
        } catch (Exception e) {
            LOGGER.warn("用户退出错误:", e);
        } finally {
            LOGGER.warn("删除缓存中的用户:" + userInfo);
        }
        return true;
    }

    /**
     * 修改用户信息(个人信息)
     */
    public UserVO upUser(UserReqVO user) {
        LOGGER.warn("UserInfoserviceImpl.upUser()");
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(DesUtil.encode(DesUtil.KEY, user.getPhone()));
        System.out.println("加密后的手机号:" + userInfo.getPhone());
        if (user.getPassWord() != null || !"".equals(user.getPassWord())) {
            userInfo.setPassWord(MD5.md5(user.getPassWord()));
        }
        userInfo.setName(user.getName());
        try {
            userInfoMapper.upUser(userInfo);
            redisUtils.set(userInfo.getPhone(), userInfo, 60 * 60 * 2);
        } catch (Exception e) {
            LOGGER.error("修改用户信息错误：", e);
            return new UserVO(UserEnum.application);
        } finally {
            LOGGER.warn("修改的用户为" + user);
        }
        return new UserVO<UserInfo>(userInfo, UserEnum.SUCSS);
    }

    @Override
    public UserInfo selectUser(String phone) {
        UserInfo userInfo = (UserInfo) redisUtils.get(phone);
        if (userInfo == null) {
            userInfo = selecUser(phone);
            redisUtils.set(phone, userInfo, 60 * 60 * 2);
        }
        return userInfo;
    }


    private Integer inserUser(UserInfo userInfo) throws UserException {
        Integer integer = userInfoMapper.inserUser(userInfo);
        if (integer != 1) {
            throw new UserException(UserExceptionEnum.UserInserException);
        }
        return integer;
    }

    private UserInfo selecUser(String Phone) {
        UserInfo userInfo = null;

        try {
            userInfo = userInfoMapper.selectUser(Phone);
        } catch (Exception e) {
            LOGGER.error("dao层错误：", e);
        } finally {
            LOGGER.warn("service.selecUser>>>>>>>>>>:" + userInfo);
        }
        return userInfo;
    }

    private List<UserInfo> selectAllU() {
        return userInfoMapper.selectAllUser();
    }
}
