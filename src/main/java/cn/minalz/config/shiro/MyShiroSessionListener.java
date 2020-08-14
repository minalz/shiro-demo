package cn.minalz.config.shiro;

import cn.minalz.config.redis.ShiroRedisSessionDao;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 自定义session的监听器
 * @author: minalz
 * @date: 2020-08-08 22:31
 **/
public class MyShiroSessionListener implements SessionListener {

    private static final Logger logger = LoggerFactory.getLogger(MyShiroSessionListener.class);

    @Autowired
    private ShiroRedisSessionDao sessionDao;

    @Override
    public void onStart(Session session) {
        // 会话创建时触发
        logger.info("ShiroSessionListener session {} 被创建", session.getId());
    }

    @Override
    public void onStop(Session session) {
        sessionDao.delete(session);
        // 会话被停止时触发
        logger.info("ShiroSessionListener session {} 被销毁", session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        sessionDao.delete(session);
        //会话过期时触发
        logger.info("ShiroSessionListener session {} 过期", session.getId());
    }
}
