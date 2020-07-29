package cn.minalz.config.shiro;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义Redis的缓存管理器实现类
 */
public class ShiroRedisCache<K, V> implements Cache<K, V> {
    private static Logger LOGGER = LogManager.getLogger(ShiroRedisCache.class);

    /**
     * key前缀
     */
    private static final String REDIS_SHIRO_CACHE_KEY_PREFIX = "shiro_cache_key_";

    /**
     * cache name
     */
    private String name;

    /**
     * jedis 连接工厂
     */
    private RedisTemplate redisTemplate;

    /**
     * 序列化工具
     */
    private RedisSerializer serializer = new JdkSerializationRedisSerializer();

    /**
     * 存储key的redis.list的key值
     */
    private String keyListKey;

    private RedisConnection getConnection(){
        return this.redisTemplate.getConnectionFactory().getConnection();
    }

    public ShiroRedisCache(String name, RedisTemplate redisTemplate) {
        this.name = name;
        this.redisTemplate = redisTemplate;
        this.keyListKey = REDIS_SHIRO_CACHE_KEY_PREFIX + name;
    }

    @Override
    public V get(K key) throws CacheException {
        LOGGER.debug("shiro redis cache get.{} K={}", name, key);
        RedisConnection redisConnection = null;
        V result = null;
        try {
            redisConnection = getConnection();
            result = (V) serializer.deserialize(redisConnection.get(serializer.serialize(generateKey(key))));
        } catch (Exception e) {
            LOGGER.error("shiro redis cache get exception. ", e);
        } finally {
            if (null != redisConnection) {
                redisConnection.close();
            }
        }
        return result;
    }

    @Override
    public V put(K key, V value) throws CacheException {
        LOGGER.debug("shiro redis cache put.{} K={} V={}", name, key, value);
        RedisConnection redisConnection = null;
        V result = null;
        try {
            redisConnection = getConnection();
            result = (V) serializer.deserialize(redisConnection.get(serializer.serialize(generateKey(key))));

            redisConnection.set(serializer.serialize(generateKey(key)), serializer.serialize(value));

            redisConnection.lPush(serializer.serialize(keyListKey), serializer.serialize(generateKey(key)));
        } catch (Exception e) {
            LOGGER.error("shiro redis cache put exception. ", e);
        } finally {
            if (null != redisConnection) {
                redisConnection.close();
            }
        }
        return result;
    }

    @Override
    public V remove(K key) throws CacheException {
        LOGGER.debug("shiro redis cache remove.{} K={}", name, key);
        RedisConnection redisConnection = null;
        V result = null;
        try {
            redisConnection = getConnection();
            result = (V) serializer.deserialize(redisConnection.get(serializer.serialize(generateKey(key))));

            redisConnection.expireAt(serializer.serialize(generateKey(key)), 0);

            redisConnection.lRem(serializer.serialize(keyListKey), 1, serializer.serialize(key));
        } catch (Exception e) {
            LOGGER.error("shiro redis cache remove exception. ", e);
        } finally {
            if (null != redisConnection) {
                redisConnection.close();
            }
        }
        return result;
    }

    @Override
    public void clear() throws CacheException {
        LOGGER.debug("shiro redis cache clear.{}", name);
        RedisConnection redisConnection = null;
        try {
            redisConnection = getConnection();

            Long length = redisConnection.lLen(serializer.serialize(keyListKey));
            if (0 == length) {
                return;
            }

            List<byte[]> keyList = redisConnection.lRange(serializer.serialize(keyListKey), 0, length - 1);
            for (byte[] key : keyList) {
                redisConnection.expireAt(key, 0);
            }

            redisConnection.expireAt(serializer.serialize(keyListKey), 0);
            keyList.clear();
        } catch (Exception e) {
            LOGGER.error("shiro redis cache clear exception.", e);
        } finally {
            if (null != redisConnection) {
                redisConnection.close();
            }
        }
    }

    @Override
    public int size() {
        LOGGER.debug("shiro redis cache size.{}", name);
        RedisConnection redisConnection = null;
        int length = 0;
        try {
            redisConnection = getConnection();
            length = Math.toIntExact(redisConnection.lLen(serializer.serialize(keyListKey)));
        } catch (Exception e) {
            LOGGER.error("shiro redis cache size exception.", e);
        } finally {
            if (null != redisConnection) {
                redisConnection.close();
            }
        }
        return length;
    }

    @Override
    public Set keys() {
        LOGGER.debug("shiro redis cache keys.{}", name);
        RedisConnection redisConnection = null;
        Set resultSet = null;
        try {
            redisConnection = getConnection();

            Long length = redisConnection.lLen(serializer.serialize(keyListKey));
            if (0 == length) {
                return resultSet;
            }

            List<byte[]> keyList = redisConnection.lRange(serializer.serialize(keyListKey), 0, length - 1);
            resultSet = keyList.stream().map(bytes -> serializer.deserialize(bytes)).collect(Collectors.toSet());
        } catch (Exception e) {
            LOGGER.error("shiro redis cache keys exception.", e);
        } finally {
            if (null != redisConnection) {
                redisConnection.close();
            }
        }
        return resultSet;
    }

    @Override
    public Collection values() {
        RedisConnection redisConnection = getConnection();
        Set keys = this.keys();

        List<Object> values = new ArrayList<Object>();
        for (Object key : keys) {
            byte[] bytes = redisConnection.get(serializer.serialize(key));
            values.add(serializer.deserialize(bytes));
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

    private byte[] getByteKey(K key) {
        if (key instanceof String) {
            String preKey = generateKey(key);
            return preKey.getBytes();
        }
        return serializer.serialize(key);
    }
}
