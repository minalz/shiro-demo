package cn.minalz.config.redis;

import cn.minalz.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;

/**
 * 自定义redis中的session管理
 */
@Slf4j
public class ShiroRedisSessionDao extends CachingSessionDAO {

    public static final String SHIRO_SESSION_KEY = "shiro_session_key";

//    private log log = logFactory.getlog(this.getClass());

    @Autowired
    private RedisTemplate redisTemplate; //spring-data-redis
    
    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doUpdate(Session session) {
        this.saveSession(session);
    }

    @Override
    protected void doDelete(Session session) {
        if (session == null || session.getId() == null) {
            log.error("session or session id is null");
            return ;
        }
        //根据session id删除session
//        redisTemplate.boundHashOps(SHIRO_SESSION_KEY).delete(session.getId());
        redisUtil.hdel(SHIRO_SESSION_KEY,session.getId());
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
            log.error("传入的 session id is null");
            return null;
        }
        return (Session)redisUtil.hget(SHIRO_SESSION_KEY, sessionId);
//        return (Session)redisTemplate.boundHashOps(SHIRO_SESSION_KEY).get(sessionId);
    }

    /**
     * 将session 保存进redis 中
     * @param session 要保存的session
     */
    private void saveSession(Session session) {
        if (session == null || (session != null && session.getId() == null)) {
            log.error("session or session id is null");
            return ;
        }
        redisUtil.hset(SHIRO_SESSION_KEY,session.getId(),session);
//        redisTemplate.boundHashOps(SHIRO_SESSION_KEY).put(session.getId(),session);
    }
}
