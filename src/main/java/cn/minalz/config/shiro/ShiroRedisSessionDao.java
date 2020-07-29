package cn.minalz.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义redis中的session管理
 */
public class ShiroRedisSessionDao extends CachingSessionDAO {

    public static final String SHIRO_SESSION_KEY = "shiro_session_key";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate redisTemplate; //spring-data-redis

    @Override
    protected void doUpdate(Session session) {
        this.saveSession(session);
    }

    @Override
    protected void doDelete(Session session) {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return ;
        }
        System.out.println("session时间过期了，需要进行删除---" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        //根据session id删除session
        redisTemplate.boundHashOps(SHIRO_SESSION_KEY).delete(session.getId());
    }


    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }


    @Override
    protected Session doReadSession(Serializable sessionId) {
        if(sessionId == null){
            logger.error("传入的 session id is null");
            return null;
        }
        return (Session)redisTemplate.boundHashOps(SHIRO_SESSION_KEY).get(sessionId);
    }

    /**
     * 将session 保存进redis 中
     * @param session 要保存的session
     */
    private void saveSession(Session session) {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return ;
        }
        redisTemplate.boundHashOps(SHIRO_SESSION_KEY).put(session.getId(),session);
    }
}
