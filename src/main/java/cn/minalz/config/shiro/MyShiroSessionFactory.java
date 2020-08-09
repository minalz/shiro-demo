package cn.minalz.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;

/**
 * @description: 自定义ShiroSessionFactory
 * @author: minalz
 * @date: 2020-08-08 17:54
 **/
public class MyShiroSessionFactory implements SessionFactory {

    @Override
    public Session createSession(SessionContext initData) {
        MyShiroSession session = new MyShiroSession();
        return session;
    }
}
