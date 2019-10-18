package com.majiang.user.majianguser.service.impl;

import com.google.gson.Gson;
import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.bean.vo.UserReqVO;
import com.majiang.user.majianguser.bean.vo.UserVO;
import com.majiang.user.majianguser.enums.UserEnum;
import com.majiang.user.majianguser.enums.UserExceptionEnum;
import com.majiang.user.majianguser.exception.UserException;
import com.majiang.user.majianguser.exception.majiangRunTimeException;
import com.majiang.user.majianguser.mapper.UserInfoMapper;
import com.majiang.user.majianguser.service.UserInfoservice;
import com.majiang.user.majianguser.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@Service
public class UserInfoserviceImpl implements UserInfoservice {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoserviceImpl.class);

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    RedisUtils redisUtils;

    /*添加员工*/
    @ExceptionHandler
    @Override
    public UserVO insertUser(UserReqVO userReqVO) throws Exception {
        UserInfo userInfo = null;
        UserVO userVO=null;
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

            if (!BeanUtils.isMobileNO(userInfo.getPhone())){
                return new UserVO(UserEnum.PhoneNotre);
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
            userInfo.setPhone(desPhone);
            //设置公共属性的值
            BeanUtils.setXXX(userInfo);
            inserUser(userInfo);
            userInfo.setPassWord("");
            userVO=new UserVO<UserInfo>(userInfo, UserEnum.SUCSS);
            return userVO;

        } catch (majiangRunTimeException exception) {

            LOGGER.error("错误:" + exception.getCode() + ",原因:"+userInfo.getPhone()+"," + exception.getMessage());
            throw new UserException(UserExceptionEnum.UserPhoneNotOnly);
        } catch (Exception e) {
            LOGGER.error("错误: "+e.getMessage());
            LOGGER.error("错误:",e );
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            LOGGER.warn("添加的用户为：" + userInfo);
            LOGGER.warn("返回值：" + userVO);

        }

    }

    /**
     *
     */
    public UserVO userLogin(UserReqVO userInfo, HttpServletResponse response)  {
        String Phone = null;
        String PassWord = null;
        UserInfo userInfo2 = null;
        UserVO userVO = null;
        Gson gson = new Gson();
        System.out.println(userInfo);
        try {
            if (userInfo.getPhone() == null || "".equals(userInfo.getPhone())) {
                return new UserVO(UserEnum.UserPhoneNotNull);
            }
            if (userInfo.getPassWord() == null || "".equals(userInfo.getPassWord())) {
                return new UserVO(UserEnum.UserPassWordNotNull);
            }
            if (!BeanUtils.isMobileNO(userInfo.getPhone())){
                return new UserVO(UserEnum.PhoneNotre);
            }
            Phone = userInfo.getPhone();
            PassWord = userInfo.getPassWord();
            //加密密码和手机号
            Phone = DesUtil.encode(DesUtil.KEY, Phone);
            PassWord = MD5.md5(PassWord);
            //查数据库
            userInfo2 = selecUser(Phone);
            System.out.println("从数据库中取到的数据:" + userInfo2);
            //未查到此人
            if (userInfo2 == null) {
                userVO=new UserVO(UserEnum.PhoneNotRegistered);
                return userVO;
            }
            //密码错误
            if (!PassWord.equals(userInfo2.getPassWord())) {
                userVO= new UserVO(UserEnum.PassWordNotright);
                return userVO;
            }
            //获取token
            String userToken = TokenUtil.getNewToken();
            Cookie cookie1 = new Cookie("token",userToken);
            response.addCookie(cookie1);
            //token和用户相关信息
            redisUtils.set(userToken, userInfo2, 60 * 60 * 2);
            userInfo2.setPassWord("");
            userVO=new UserVO(userInfo2, UserEnum.SUCSS);
        } catch (Exception e) {
            LOGGER.error("错误:",e );
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            LOGGER.warn("登录的用户为:" + userInfo2);
            LOGGER.warn("返回值:" + userVO);
        }
        return userVO;
    }

    public  UserVO selectAllUser(){
        List<UserInfo> userInfos=null;
        try {
            userInfos = selectAllU();
            if (null==userInfos){
                return new UserVO(UserEnum.NoUser);
            }
            for (UserInfo userInfo:userInfos){
                userInfo.setPassWord("");
            }
        }catch (Exception e){
            LOGGER.error("错误:",e );
            LOGGER.error(e.getMessage());
        }finally {
            LOGGER.warn("查询所有的用户:",userInfos);
        }

        return new UserVO<List<UserInfo>>(userInfos,UserEnum.SUCSS);
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

    private   List<UserInfo> selectAllU(){
        return userInfoMapper.selectAllUser();
    }
}
