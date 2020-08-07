package cn.minalz.config;

import cn.minalz.utils.SerializeUtil;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
 
/**
 * 实现Redis对象的序列化接口
 * 参考：JdkSerializationRedisSerializer源码
 *
 */
public class KryoRedisSerializer implements RedisSerializer<Object> {
 
 
    static final byte[] EMPTY_ARRAY = new byte[0];
    private final Charset charset;
 
    public KryoRedisSerializer() {
        this(Charset.forName("UTF8"));
    }
 
    public KryoRedisSerializer(Charset charset) {
        Assert.notNull(charset);
        this.charset = charset;
    }
 
    @Override
    public byte[] serialize(Object object){  //序列化方法
        try {
 
            return SerializeUtil.serizlize(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
 
    }
 
    @Override
    public Object deserialize(byte[] bytes) throws SerializationException { //反序列化
 
        Object object = null;
        if (bytes == null) {
            return object;
        }
        try {
            object = SerializeUtil.deserialize(bytes);  //返回的是JSONObject类型  取数据时候需要再次转换一下
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
 
}