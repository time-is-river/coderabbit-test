package com.fly.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis 工具类
 * @author
 * @date 2021/5/24 8:50
 */
@Component
public class RedisUtils {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static RedisUtils redisUtils;

    /**
     * 使用 PostConstruct（在依赖注入完成后被自动调用） 注解  初始化
     */
    @PostConstruct
    public void init() {
        redisUtils = this;
        redisUtils.stringRedisTemplate = this.stringRedisTemplate;
    }

    /**
     * 查询key,支持模糊查询
     * @param key
     * @return
     */
    public static Set<String> keys(String key) {
        return redisUtils.stringRedisTemplate.keys(key);
    }

    /**
     * 根据key 获取值
     * @param key
     * @return
     */
    public static Object get(String key) {
        return redisUtils.stringRedisTemplate.keys(key);
    }

    /**
     * 根据key 获取值
     * @param key
     * @return
     */
    public static String getString(String key) {
        return redisUtils.stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 设置 key, value
     * @param key
     * @param value
     */
    public static void set(String key, String value) {
        redisUtils.stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置值，并设置过期时间
     *
     * @param key
     * @param value
     * @param expire 过期时间，单位秒
     */
    public static void set(String key, String value, Integer expire) {
        redisUtils.stringRedisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
    }

    /**
     * 删出key
     *
     * @param key
     */
    public static void delete(String key) {
        redisUtils.stringRedisTemplate.opsForValue().getOperations().delete(key);
    }

    /**
     * 设置对象
     *
     * @param key     key
     * @param hashKey hashKey
     * @param object  对象
     */
    public static void hset(String key, String hashKey, Object object) {
        redisUtils.stringRedisTemplate.opsForHash().put(key, hashKey, object);
    }

    /**
     * 设置对象
     *
     * @param key     key
     * @param hashKey hashKey
     * @param object  对象
     * @param expire  过期时间，单位秒
     */
    public static void hset(String key, String hashKey, Object object, Integer expire) {
        redisUtils.stringRedisTemplate.opsForHash().put(key, hashKey, object);
        redisUtils.stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * 设置HashMap
     *
     * @param key key
     * @param map map值
     */
    public static void hset(String key, HashMap<String, Object> map) {
        redisUtils.stringRedisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * key不存在时设置值
     *
     * @param key
     * @param hashKey
     * @param object
     */
    public static void hsetAbsent(String key, String hashKey, Object object) {
        redisUtils.stringRedisTemplate.opsForHash().putIfAbsent(key, hashKey, object);
    }

    /**
     * 获取Hash值
     *
     * @param key
     * @param hashKey
     * @return
     */
    public static Object hget(String key, String hashKey) {
        return redisUtils.stringRedisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 获取key的所有值
     *
     * @param key
     * @return
     */
    public static Object hget(String key) {
        return redisUtils.stringRedisTemplate.opsForHash().entries(key);
    }

    /**
     * 删除key的所有值
     *
     * @param key
     */
    public static void deleteKey(String key) {
        redisUtils.stringRedisTemplate.opsForHash().getOperations().delete(key);
    }

    /**
     * 判断key下是否有值
     *
     * @param key
     */
    public static Boolean hasKey(String key) {
        return redisUtils.stringRedisTemplate.opsForHash().getOperations().hasKey(key);
    }

    /**
     * 判断key和hasKey下是否有值
     *
     * @param key
     * @param hasKey
     */
    public static Boolean hasKey(String key, String hasKey) {
        return redisUtils.stringRedisTemplate.opsForHash().hasKey(key, hasKey);
    }

}
