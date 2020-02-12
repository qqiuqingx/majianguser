package com.majiang.user.majianguser.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class MyShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();


        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/userLogin", "anon");
        filterChainDefinitionMap.put("/pub/**", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/layui/**", "anon");
        filterChainDefinitionMap.put("/majiang/**", "anon");
        filterChainDefinitionMap.put("/page/**", "anon");
        filterChainDefinitionMap.put("/addUser", "anon");
        filterChainDefinitionMap.put("/public/**", "anon");
        filterChainDefinitionMap.put("/Sing", "anon");
        //支付宝支付成功同步返回接口
        filterChainDefinitionMap.put("/ali/**", "anon");

        //对swagger放行
        filterChainDefinitionMap.put("/swagger-ui.html","anon");
        filterChainDefinitionMap.put("/swagger/**","anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**","anon");
        filterChainDefinitionMap.put("/v2/**","anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        //authc
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/login.html");
        //登陆成功后要跳转的界面
       // shiroFilterFactoryBean.setSuccessUrl("/index.html");
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/login.html");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
       // securityManager.setCacheManager(cacheManager);

        return securityManager;
    }

    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        return myShiroRealm;
    }
    /**
     * advisorAutoProxyCreator()和authorizationAttributeSourceAdvisor(SecurityManager securityManager)
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),
     * 需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     *
     * @return
     */
    @Bean
    //@DependsOn({ "lifecycleBeanPostProcessor" })
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    /*
    * 配置ShiroDialect 用于thymeleaf和shiro标签配合使用
    * */
    @Bean
    public ShiroDialect ShiroDialect(){
        return  new ShiroDialect();
    }

}
