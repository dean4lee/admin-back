package cn.inslee.adminback.shiro.config;

import cn.inslee.adminback.shiro.credential.Md5CredentialsMatcher;
import cn.inslee.adminback.shiro.filter.UserFilter;
import cn.inslee.adminback.shiro.filter.UserLogoutFilter;
import cn.inslee.adminback.shiro.realm.UserRealm;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author dean.lee
 */
@Configuration
public class ShiroConfig {

    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);

        //设置权限拦截器
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/", "anon");
        filterMap.put("/druid/**", "anon");
        filterMap.put("/code", "anon");
        filterMap.put("/login", "anon");
        filterMap.put("/logout", "logout");
        filterMap.put("/**", "user");
        //自定义过滤器
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("user", new UserFilter());
        filters.put("logout", new UserLogoutFilter());

        factoryBean.setFilters(filters);
        factoryBean.setFilterChainDefinitionMap(filterMap);

        return factoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean
    public SecurityManager securityManager(UserRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /**
     * 创建Realm
     */
    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(new Md5CredentialsMatcher());
        return userRealm;
    }

    /**
     * cookie对象
     *
     * @return
     */
    public SimpleCookie rememberMeCookie() {
        //参数是cookie的名称，对应UserLoginVo中的rememberMe字段
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //默认记住我的状态时间为7天，单位秒
        simpleCookie.setMaxAge(7 * 24 * 60 * 60);
        return simpleCookie;
    }

    /**
     * cookie管理对象;记住我功能
     *
     * @return
     */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        /*
         * rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
         *
         * KeyGenerator keygen = KeyGenerator.getInstance("AES");
         * SecretKey deskey = keygen.generateKey();
         * System.out.println(Base64.encodeToString(deskey.getEncoded()));
         */
        cookieRememberMeManager.setCipherKey(Base64.decode("66v1O8keKNV3TTcGPK1wzg=="));
        return cookieRememberMeManager;
    }

    /**
     * 开启shiro注解
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
