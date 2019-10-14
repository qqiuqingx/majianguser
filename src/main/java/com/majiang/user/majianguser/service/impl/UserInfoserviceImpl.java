package com.majiang.user.majianguser.service.impl;

import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.bean.vo.UserReqVO;
import com.majiang.user.majianguser.bean.vo.UserVO;
import com.majiang.user.majianguser.config.MyLogConfig;
import com.majiang.user.majianguser.enums.UserEnum;
import com.majiang.user.majianguser.enums.UserExceptionEnum;
import com.majiang.user.majianguser.exception.UserException;
import com.majiang.user.majianguser.exception.majiangRunTimeException;
import com.majiang.user.majianguser.mapper.UserInfoMapper;
import com.majiang.user.majianguser.service.UserInfoservice;
import com.majiang.user.majianguser.utils.DesUtil;
import com.majiang.user.majianguser.utils.MD5;
import com.majiang.user.majianguser.utils.RedisUtils;
import com.majiang.user.majianguser.utils.TokenUtil;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import jdk.nashorn.internal.parser.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserInfoserviceImpl implements UserInfoservice {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoserviceImpl.class);

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    RedisUtils redisUtils;

    /*添加员工*/
    @Override
    public UserVO insertUser(UserReqVO userReqVO) throws Exception {
        UserInfo userInfo = null;
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

        } catch (majiangRunTimeException exception) {
            LOGGER.info("错误:" + exception.getCode() + ",原因:" + exception.getMessage());
            throw new UserException(UserExceptionEnum.UserPhoneNotOnly);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            LOGGER.info("添加的用户为：" + userInfo);
        }

    }

    /**
     *
     */
    public UserVO userLogin(UserReqVO userInfo)  {
        String Phone = null;
        String PassWord = null;
        UserInfo userInfo2 = null;
        try {
            if (userInfo.getPhone() == null || "".equals(userInfo.getPhone())) {
                return new UserVO(UserEnum.UserPhoneNotNull);
            }
            if (userInfo.getPassWord() == null || "".equals(userInfo.getPassWord())) {
                return new UserVO(UserEnum.UserPassWordNotNull);
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
                return new UserVO(UserEnum.PhoneNotRegistered);
            }
            //密码错误
            if (!PassWord.equals(userInfo2.getPassWord())) {
                return new UserVO(UserEnum.PassWordNotright);
            }
            //获取token
            String userToken = TokenUtil.getNewToken();
            //token和用户相关信息
            redisUtils.set(userToken, userInfo2, 60 * 60 * 2);
            userInfo2.setPassWord("");
            System.out.println("userInfo:" + userInfo2);
        } catch (Exception e) {
            LOGGER.warn("错误:" + e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            LOGGER.warn("登录的用户为:" + userInfo2);
        }
        return new UserVO<UserInfo>(userInfo2, UserEnum.SUCSS);

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
