package cn.minalz.config;

import cn.minalz.config.shiro.MyCredentialsMatcher;
import cn.minalz.config.shiro.MyRealm;
import cn.minalz.config.shiro.MyRedisCacheManager;
import cn.minalz.config.shiro.ShiroRedisSessionDao;
import cn.minalz.dao.ScmciwhUserRepository;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Autowired
    private ScmciwhUserRepository scmciwhUserRepository;

    @Bean
    public Realm realm() {
        // 创建 SimpleAccountRealm 对象
//        SimpleAccountRealm realm = new SimpleAccountRealm();
//        // 添加两个用户。参数分别是 username、password、roles 。
//        realm.addAccount("admin", "admin", "CJGLY");
//        realm.addAccount("normal", "normal", "CJGLY");
//        return realm;
        MyRealm myRealm = new MyRealm();
        //告诉realm密码匹配方式
        myRealm.setCredentialsMatcher(myCredentialsMatcher());
        myRealm.setAuthorizationCacheName("perms");
        myRealm.setAuthorizationCachingEnabled(true);

        myRealm.setAuthenticationCachingEnabled(false);
        //设置缓存管理器
        myRealm.setCacheManager(cacheManager());
        return myRealm;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        // 创建 DefaultWebSecurityManager 对象
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager());
        // 设置其使用的 Realm
        securityManager.setRealm(this.realm());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        // <1> 创建 ShiroFilterFactoryBean 对象，用于创建 ShiroFilter 过滤器
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();

        // <2> 设置 SecurityManager
        filterFactoryBean.setSecurityManager(this.securityManager());

        // <3> 设置 URL 们
        filterFactoryBean.setLoginUrl("/login"); // 登陆 URL
        filterFactoryBean.setSuccessUrl("/login_success"); // 登陆成功 URL
        filterFactoryBean.setUnauthorizedUrl("/unauthorized"); // 无权限 URL

        // <4> 设置 URL 的权限配置
        filterFactoryBean.setFilterChainDefinitionMap(this.filterChainDefinitionMap());

        return filterFactoryBean;
    }

    /**
     * 配置自定义的加密方式
     * @return
     */
    @Bean
    public MyCredentialsMatcher myCredentialsMatcher() {
        return new MyCredentialsMatcher();
    }

    //缓存管理
    @Bean
    public CacheManager cacheManager(){
        MyRedisCacheManager cacheManager = new MyRedisCacheManager();
        return cacheManager;
    }

    /**
     * 自定义会话管理器
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());

        // 设置会话过期时间
        // 默认半小时
        sessionManager.setGlobalSessionTimeout(3*60*1000);
        // 默认自动调用SessionDAO的delete方法删除会话
        sessionManager.setDeleteInvalidSessions(true);
        // 删除在session过期时跳转页面时自动在URL中添加JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        // 设置会话定时检查
        //        sessionManager.setSessionValidationInterval(180000); //默认一小时
        //        sessionManager.setSessionValidationSchedulerEnabled(true);
        return sessionManager;
    }

    @Bean
    public SessionDAO redisSessionDAO(){
        ShiroRedisSessionDao redisDAO = new ShiroRedisSessionDao();
        return redisDAO;
    }

    /**
     * 设置访问权限  访问xx资源 需要xx权限
     * @return
     */
    private Map<String, String> filterChainDefinitionMap() {
        Map<String, String> filterMap = new LinkedHashMap<>(); // 注意要使用有序的 LinkedHashMap ，顺序匹配
        filterMap.put("/scmciwh/echo", "anon"); // 允许匿名访问
        filterMap.put("/scmciwh/admin", "roles[CJGLY]"); // 超级管理员
        filterMap.put("/scmciwh/normal", "roles[GLDP]"); // 需要 NORMAL 角色
        filterMap.put("/logout", "logout"); // 退出
        filterMap.put("/**", "authc"); // 默认剩余的 URL ，需要经过认证
        return filterMap;
    }

}