package com.majiang.user.majianguser.config;

import com.majiang.user.majianguser.bean.Permission;
import com.majiang.user.majianguser.bean.Role;
import com.majiang.user.majianguser.bean.UserInfo;
import com.majiang.user.majianguser.mapper.PermissionMapper;
import com.majiang.user.majianguser.mapper.RoleMapper;
import com.majiang.user.majianguser.service.UserInfoservice;
import com.majiang.user.majianguser.service.impl.UserInfoserviceImpl;
import com.majiang.user.majianguser.utils.DesUtil;
import com.majiang.user.majianguser.utils.MD5;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.sound.midi.Soundbank;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class MyShiroRealm extends AuthorizingRealm {
    private static final Logger log = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    UserInfoservice userInfoservice;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    PermissionMapper permissionMapper;
    @Value("majiang.shiro.userLoginSession")
    private String user;
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken user =(UsernamePasswordToken)token;
        UserInfo userInfo = userInfoservice.selectUser(DesUtil.encode(DesUtil.KEY,user.getUsername()));
        if (userInfo!=null){
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userInfo, userInfo.getPassWord(), getName());
            Session session = SecurityUtils.getSubject().getSession();
            session.setTimeout(60 * 60 * 2*1000);
            session.setAttribute(this.user,userInfo);
            return simpleAuthenticationInfo;
        }
        return null;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("进入MyShiroRealm.doGetAuthorizationInfo方法》》》》》》》》》》》》。");
        log.warn("权限配置MyShiroRealm.doGetAuthorizationInfo方法》》》》》》》》》》》》");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //获取在session中的用户数据
        UserInfo user = (UserInfo)SecurityUtils.getSubject().getSession().getAttribute(this.user);
        if(user!=null) {
            System.out.println("shiro.session.user:" + user);
            List<Role> roles = roleMapper.findByUserPhone(user.getPhone());
            System.out.println("根据用户手机号获取到的roles:" + roles);
            Set<String> collect = roles.stream().map(Role::getName).collect(Collectors.toSet());
            System.out.println("获取到的角色名称" + collect);
            authorizationInfo.setRoles(collect);
            List<Permission> permission = permissionMapper.findByUserPhone(user.getPhone());
            System.out.println("根据用户手机号获取到的权限:" + permission);
            Set<String> collect1 = permission.stream().map(Permission::getPermission).collect(Collectors.toSet());
            System.out.println("获取到的权限Permission" + collect1);
            authorizationInfo.setStringPermissions(collect1);
        }
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
