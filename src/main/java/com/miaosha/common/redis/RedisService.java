package com.miaosha.common.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by pc on 2018/3/5.
 */
@Service
public class RedisService {

    @Autowired
    private JedisPool jedisPool;

    public <T> T get(String key, Class<T> clazz){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = jedis.get(key);
            T result = strToBean(str, clazz);
            return result;
        }finally {
            returnToPool(jedis);
        }
    }

    public <T> boolean set(String key, T value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = beanToStr(value);
            if (null == str || str.length() == 0)return false;
            jedis.set(key, str);
            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * bean转换为字符串
     * @param value
     * @param <T>
     * @return
     */
    private <T> String beanToStr(T value) {
        if (null == value){
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) return value+"";
        else if (clazz == long.class || clazz == Long.class) return value+"";
        else if (clazz == String.class)return (String)value;
        else{
            return JSON.toJSONString(value);
        }
    }

    /**
     * 字符串转换为bean对象
     * @param str
     * @param <T>
     * @return
     */
    private <T> T strToBean(String str, Class<T> clazz) {
        if (null == str || null == clazz || str.length() == 0) return null;
        else if (clazz == int.class || clazz == Integer.class) return (T) Integer.valueOf(str);
        else if (clazz == long.class || clazz == Long.class) return (T) Long.valueOf(str);
        else if (clazz == String.class)return (T)str;
        else{
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    /**
     * 关闭redis连接
     * @param jedis
     */
    private void returnToPool(Jedis jedis) {
        if (null != jedis){
            jedis.close();
        }
    }
}
