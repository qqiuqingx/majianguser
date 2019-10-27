package com.majiang.user.majianguser.config;

import com.majiang.user.majianguser.utils.MD5;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;


/**
 * 自定义shiro密码比较规则
 */
public class MyCredentialsMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken;
        Object pass = MD5.md5(String.valueOf(token.getPassword()));
        System.out.println("用于输入的密码加密后："+pass);
        Object credentials = authenticationInfo.getCredentials();
        System.out.println("credentials"+credentials+">>>>>>>>>>>>>>>>>>>>>>>>>>");
        if (credentials.equals(pass)){
            return true;
        }
        return false;
    }
}
