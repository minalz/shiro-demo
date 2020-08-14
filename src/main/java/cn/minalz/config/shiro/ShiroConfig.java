package cn.minalz.config.shiro;

import cn.minalz.config.filter.MyFormAuthenticationFilter;
import cn.minalz.config.filter.MyLogoutFilter;
import cn.minalz.config.redis.ShiroRedisSessionDao;
import cn.minalz.dao.UserRepository;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Autowired
    private UserRepository scmciwhUserRepository;

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
        //开启授权信息的缓存，默认开启
        myRealm.setAuthorizationCachingEnabled(true);
        myRealm.setAuthorizationCacheName("author");
        //开启认证信息的缓存，默认关闭，key是UserNamePasswordToken，value就是principle
        myRealm.setAuthenticationCachingEnabled(false);
        myRealm.setAuthenticationCacheName("authen");
        //设置缓存管理器
        myRealm.setCacheManager(cacheManager());
        return myRealm;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        // 创建 DefaultWebSecurityManager 对象
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 配置 rememberMeCookie 查看源码可以知道，这里的rememberMeManager就仅仅是一个赋值，所以先执行
//        securityManager.setRememberMeManager(rememberMeManager());
        // 设置其使用的 Realm
        securityManager.setRealm(this.realm());
        securityManager.setSessionManager(sessionManager());
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

        // 添加自定义的shiro注销过滤器
        filterFactoryBean.setFilters(myFilters());

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

    // 自定义shiroSessionFactroy
//    @Bean
    public SessionFactory sessionFactory(){
        MyShiroSessionFactory myShiroSessionFactory = new MyShiroSessionFactory();
        return myShiroSessionFactory;
    }

    // 自定义session监听器
    /*@Bean
    public SessionListener sessionListener(){
        MyShiroSessionListener myShiroSessionListener = new MyShiroSessionListener();
        return myShiroSessionListener;
    }*/

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
        sessionManager.setGlobalSessionTimeout(20*1000);
        // 默认自动调用SessionDAO的delete方法删除会话
        sessionManager.setDeleteInvalidSessions(true);
        // 删除在session过期时跳转页面时自动在URL中添加JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        //是否开启定时调度器进行检测过期session 默认为true
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionValidationScheduler(configSessionValidationScheduler());

        // 设置会话定时检查 默认一小时
        sessionManager.setSessionValidationInterval(5000);
        // 设置自定义的sessionFactory
//        sessionManager.setSessionFactory(sessionFactory());
        // 设置自定义的session监听器
//        LinkedList<SessionListener> listeners = new LinkedList<SessionListener>();
//        listeners.add(sessionListener());
//        sessionManager.setSessionListeners(listeners);
        return sessionManager;
    }

    /**
     * session会话验证调度器
     * @return session会话验证调度器
     */
    @Bean
    public ExecutorServiceSessionValidationScheduler configSessionValidationScheduler() {
        ExecutorServiceSessionValidationScheduler sessionValidationScheduler = new ExecutorServiceSessionValidationScheduler();
        //设置session的失效扫描间隔，单位为毫秒
        sessionValidationScheduler.setInterval(20*1000);
        return sessionValidationScheduler;
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
//        filterMap.put("/login", "anon"); // 允许匿名访问
//        filterMap.put("/", "anon"); // 允许匿名访问
        filterMap.put("/scmciwh/admin", "roles[CJGLY]"); // 超级管理员
        filterMap.put("/scmciwh/normal", "roles[GLDP]"); // 需要 NORMAL 角色
        filterMap.put("/logout", "logout"); // 退出
        filterMap.put("/**", "authc"); // 默认剩余的 URL ，需要经过认证
        return filterMap;
    }

    /**
     * 自定义配置拦截器
     * @return
     */
    private Map<String, Filter> myFilters(){
        Map<String, Filter> filtersMap = new LinkedHashMap<>();
        // 自定义authc权限验证的过滤器
        filtersMap.put("authc", new MyFormAuthenticationFilter());
        // 配置自定义的shiro注销过滤器
        filtersMap.put("logout", new MyLogoutFilter());
        return filtersMap;
    }

}