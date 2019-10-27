package com.majiang.user.majianguser.config;

import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.service.UserInfoservice;
import com.majiang.user.majianguser.service.impl.UserInfoserviceImpl;
import com.majiang.user.majianguser.utils.DesUtil;
import com.majiang.user.majianguser.utils.MD5;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sound.midi.Soundbank;


public class MyShiroRealm extends AuthorizingRealm {
    private static final Logger log = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    UserInfoservice userInfoservice;
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken user =(UsernamePasswordToken)token;
        UserInfo userInfo = userInfoservice.selectUser(DesUtil.encode(DesUtil.KEY,user.getUsername()));
        if (userInfo!=null){

            return new SimpleAuthenticationInfo(userInfo,userInfo.getPassWord(),getName());
        }
        return null;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.debug("权限配置");

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();


        return authorizationInfo;
    }

    /**
     * 重写缓存key，否则集群下session共享时，会重复执行doGetAuthorizationInfo权限配置
     */
    @Override
    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
        SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) principals;
        Object object = principalCollection.getPrimaryPrincipal();

        if (object instanceof UserInfo) {
            UserInfo user = (UserInfo) object;

            return "authorization:cache:key:users:" + user.getKeyID();
        }

        return super.getAuthorizationCacheKey(principals);
    }
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher){
        System.out.println("自定义加密");
        super.setCredentialsMatcher(new MyCredentialsMatcher());
    }
}
