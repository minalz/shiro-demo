package cn.minalz.config.shiro;

import cn.minalz.config.redis.ShiroRedisCache;
import cn.minalz.utils.RedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 自定义Redis的缓存管理器
 */
public class MyRedisCacheManager implements CacheManager {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new ShiroRedisCache(name,redisTemplate,redisUtil);
    }
}