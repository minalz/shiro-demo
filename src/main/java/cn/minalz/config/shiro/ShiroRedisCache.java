package cn.minalz.config.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
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
     * jedis 连接工厂
     */

    private RedisTemplate redisTemplate;

    /**
     * 序列化工具
     */
    private RedisSerializer serializer = new JdkSerializationRedisSerializer();
    private RedisSerializer serializer_key = new StringRedisSerializer();

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
        LOGGER.info("shiro redis cache get.{} K={}", name, key);
        RedisConnection redisConnection = null;
        V result = null;
        try {
            redisConnection = getConnection();
            result = (V) serializer.deserialize(redisConnection.get(serializer_key.serialize(generateKey(key))));
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
        LOGGER.info("shiro redis cache put.{} K={} V={}", name, key, value);
        RedisConnection redisConnection = null;
        V result = null;
        try {
            redisConnection = getConnection();
            result = (V) serializer.deserialize(redisConnection.get(serializer_key.serialize(generateKey(key))));

            redisConnection.set(serializer_key.serialize(generateKey(key)), serializer.serialize(value));

            redisConnection.lPush(serializer_key.serialize(keyListKey), serializer_key.serialize(generateKey(key)));
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
        LOGGER.info("shiro redis cache remove.{} K={}", name, key);
        RedisConnection redisConnection = null;
        V result = null;
        try {
            redisConnection = getConnection();
            result = (V) serializer.deserialize(redisConnection.get(serializer_key.serialize(generateKey(key))));

            redisConnection.expireAt(serializer_key.serialize(generateKey(key)), 0);

            redisConnection.lRem(serializer_key.serialize(keyListKey), 1, serializer_key.serialize(generateKey(key)));
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
        LOGGER.info("shiro redis cache clear.{}", name);
        RedisConnection redisConnection = null;
        try {
            redisConnection = getConnection();

            Long length = redisConnection.lLen(serializer_key.serialize(keyListKey));
            if (0 == length) {
                return;
            }

            List<byte[]> keyList = redisConnection.lRange(serializer_key.serialize(keyListKey), 0, length - 1);
            for (byte[] key : keyList) {
                redisConnection.expireAt(key, 0);
            }

            redisConnection.expireAt(serializer_key.serialize(keyListKey), 0);
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
        LOGGER.info("shiro redis cache size.{}", name);
        RedisConnection redisConnection = null;
        int length = 0;
        try {
            redisConnection = getConnection();
            length = Math.toIntExact(redisConnection.lLen(serializer_key.serialize(keyListKey)));
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
        LOGGER.info("shiro redis cache keys.{}", name);
        RedisConnection redisConnection = null;
        Set resultSet = null;
        try {
            redisConnection = getConnection();

            Long length = redisConnection.lLen(serializer_key.serialize(keyListKey));
            if (0 == length) {
                return resultSet;
            }

            List<byte[]> keyList = redisConnection.lRange(serializer_key.serialize(keyListKey), 0, length - 1);
            resultSet = keyList.stream().map(bytes -> serializer_key.deserialize(bytes)).collect(Collectors.toSet());
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
            byte[] bytes = redisConnection.get(serializer_key.serialize(key));
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
//        return getRedisCacheKey(key);
    }

    private byte[] getByteKey(K key) {
        if (key instanceof String) {
            String preKey = generateKey(key);
            return preKey.getBytes();
        }
        return serializer_key.serialize(key);
    }


    private String getRedisCacheKey(K key) {
        Object redisKey = this.getStringRedisKey(key);
        if (redisKey instanceof String) {
            return this.REDIS_SHIRO_CACHE_KEY_PREFIX + redisKey;
        } else {
            return String.valueOf(redisKey);
        }
    }

    private Object getStringRedisKey(K key) {
        Object redisKey;
        if (key instanceof PrincipalCollection) {
            redisKey = this.getRedisKeyFromPrincipalCollection((PrincipalCollection) key);
        } else {
            redisKey = key.toString();
        }
        return redisKey;
    }

    private Object getRedisKeyFromPrincipalCollection(PrincipalCollection key) {
        List realmNames = this.getRealmNames(key);
        Collections.sort(realmNames);
        Object redisKey = this.joinRealmNames(realmNames);
        return redisKey;
    }

    private List<String> getRealmNames(PrincipalCollection key) {
        ArrayList realmArr = new ArrayList();
        Set realmNames = key.getRealmNames();
        Iterator i$ = realmNames.iterator();
        while (i$.hasNext()) {
            String realmName = (String) i$.next();
            realmArr.add(realmName);
        }
        return realmArr;
    }

    private Object joinRealmNames(List<String> realmArr) {
        StringBuilder redisKeyBuilder = new StringBuilder();
        for (int i = 0; i < realmArr.size(); ++i) {
            String s = realmArr.get(i);
            redisKeyBuilder.append(s);
        }
        String redisKey = redisKeyBuilder.toString();
        return redisKey;
    }
}
