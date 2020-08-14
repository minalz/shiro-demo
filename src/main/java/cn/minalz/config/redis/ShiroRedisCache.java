package cn.minalz.config.redis;

import cn.minalz.utils.RedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义shiro的Redis缓存
 */
public class ShiroRedisCache<K, V> implements Cache<K, V> {
    private static Logger LOGGER = LoggerFactory.getLogger(ShiroRedisCache.class);

    /**
     * key前缀
     */
    private static final String REDIS_SHIRO_CACHE_KEY_PREFIX = "shiro_cache_key_";

    /**
     * cache name
     */
    private String name;

    /**
     *  redis工具类
     */
    private RedisUtil redisUtil;



    /**
     * 存储key的redis.list的key值
     */
    private String keyListKey;


    public ShiroRedisCache(String name, RedisTemplate redisTemplate, RedisUtil redisUtil) {
        this.name = name;
        this.redisUtil = redisUtil;
        this.keyListKey = REDIS_SHIRO_CACHE_KEY_PREFIX + name;
    }

    @Override
    public V get(K key) throws CacheException {
        LOGGER.info("shiro redis cache get.{} K={}", name, key);
        V result = null;
            result = (V)redisUtil.get(generateKey(key));
        return result;
    }

    @Override
    public V put(K key, V value) throws CacheException {
        LOGGER.info("shiro redis cache put.{} K={} V={}", name, key, value);
        V result = null;
            result = (V)redisUtil.get(generateKey(key));
            redisUtil.set(generateKey(key),value);
            redisUtil.lSet(keyListKey,generateKey(key));
        return result;
    }

    @Override
    public V remove(K key) throws CacheException {
        LOGGER.info("shiro redis cache remove.{} K={}", name, key);
        V result = null;
            result = (V)redisUtil.get(generateKey(key));
            redisUtil.expire(generateKey(key), 0);
            redisUtil.lRemove(keyListKey, 1, generateKey(key));
        return result;
    }

    @Override
    public void clear() throws CacheException {
        LOGGER.info("shiro redis cache clear.{}", name);
            Long length = redisUtil.lGetListSize(keyListKey);
            if (0 == length) {
                return;
            }
            List<Object> keyList = redisUtil.lGet(keyListKey, 0, length - 1);
            for(Object key : keyList){
                redisUtil.expire(key.toString(), 0);
            }
            redisUtil.expire(keyListKey, 0);
            keyList.clear();
    }

    @Override
    public int size() {
        LOGGER.info("shiro redis cache size.{}", name);
        int length = 0;
            length = Math.toIntExact(redisUtil.lGetListSize(keyListKey));
        return length;
    }

    @Override
    public Set keys() {
        LOGGER.info("shiro redis cache keys.{}", name);
        Set resultSet = null;
            Long length = redisUtil.lGetListSize(keyListKey);
            if (0 == length) {
                return resultSet;
            }
            List<Object> keyList = redisUtil.lGet(keyListKey, 0, length - 1);
            resultSet = keyList.stream().map(x -> x).collect(Collectors.toSet());
        return resultSet;
    }

    @Override
    public Collection values() {
        Set keys = this.keys();

        List<Object> values = new ArrayList<Object>();
        for (Object key : keys) {
            Object value = redisUtil.get(key.toString());
            values.add(value);
        }
        return values;
    }

    /**
     * 重组key
     * 区别其他使用环境的key
     *
     * @param key
     * @return
     */
    private String generateKey(K key) {
        return REDIS_SHIRO_CACHE_KEY_PREFIX + name + "_" + key;
    }

}
