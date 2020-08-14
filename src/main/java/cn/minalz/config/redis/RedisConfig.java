package cn.minalz.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * 自定义restTemplate
 */
@Configuration
public class RedisConfig {

    /**
     * 配置RedisTemplate实例
     * @param
     * @return
     */
    @Bean
    public RedisTemplate<Serializable, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Serializable, Object> template = new RedisTemplate<Serializable, Object>();
        template.setConnectionFactory(connectionFactory);
        template.afterPropertiesSet();
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(new FastJsonRedisSerializer<>(Object.class));
//        template.setHashValueSerializer(new FastJsonRedisSerializer<>(Object.class));
        return template;
    }


}
