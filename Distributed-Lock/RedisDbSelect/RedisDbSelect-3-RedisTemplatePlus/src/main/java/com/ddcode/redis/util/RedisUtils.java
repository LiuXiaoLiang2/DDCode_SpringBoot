package com.ddcode.redis.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ddcode.redis.config.RedisConfig;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 单机版redis工具类
 */
@Component
public class RedisUtils {


    @Resource
    private RedisConfig redisConfig;


    /**
     * 根据db获取对应的redisTemplate实例
     *
     * @param db
     * @return redisTemplate实例
     */
    public RedisTemplate<Serializable, Object> getRedisTemplateByDb(final int db) {
        return redisConfig.redisTemplateMap.get(db);
    }


    /**
     * 设置缓存
     *
     * @param key   缓存key
     * @param value 缓存value
     */
    public void setString(String key, String value, int db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置缓存，并且自己指定过期时间
     *
     * @param key
     * @param value
     * @param expireTime 过期时间
     */
    public void setString(String key, String value, int expireTime, int db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
    }


    /**
     * 设置缓存对象，可指定DB
     *
     * @param key 缓存key
     * @param obj 缓存value
     */
    public <T> void setObject(String key, T obj, int db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        redisTemplate.opsForValue().set(key, obj);
    }

    /**
     * 新增hashMap值
     *
     * @param key
     * @param hashKey
     * @param hashValue
     * @param db
     * @return void
     * @author WangJing
     * @date 2019年10月26日 9:22
     */
    public <T> void hashPutString(Serializable key, Serializable hashKey, String hashValue, int db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        HashOperations<Serializable, Serializable, Serializable> operations = redisTemplate.opsForHash();
        operations.put(key, hashKey, hashValue);
    }

    /**
     * 以map集合的形式添加键值对
     *
     * @param key
     * @param maps
     * @param db
     * @return void
     * @author Sunhj
     * @date 2019年10月26日 9:56
     */
    public void hashPutAll(String key, Map<Serializable, Serializable> maps, int db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        HashOperations<Serializable, Serializable, Serializable> operations = redisTemplate.opsForHash();
        operations.putAll(key, maps);
    }

    /**
     * 获取变量中的键值对
     * {key3=value3, key1=value1, key5=value5, key4=value4, key2=value2}
     *
     * @param db
     * @param key
     * @return java.util.Map<String, String>
     * @author wangj
     * @date 2019年10月26日 8:47
     */
    public <T> Map<Object, Object> hashGetAll(int db, Serializable key) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @param db
     * @return java.util.Map<String, String>
     * @author wangj
     * @date 2019年10月26日 8:47
     */
    public <T> Boolean hashHasKey(Serializable key, Serializable hahsKey, int db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        return redisTemplate.opsForHash().hasKey(key, hahsKey);
    }

    /**
     * 获取hash表中存在的所有的键
     *
     * @param key
     * @param db
     * @return java.util.List<java.lang.String>
     * @author Sunhj
     * @date 2019年10月26日 10:58
     */
    public Set<Object> hashGetAllHashKeys(Serializable key, int db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取hash表中存在的所有的值
     *
     * @param key
     * @param db
     * @return java.util.List<java.lang.String>
     * @author Sunhj
     * @date 2019年10月26日 10:58
     */
    public List<Object> hashGetAllHashValues(Serializable key, int db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * 根据key，hashKey
     * 获取hash表中存在的单个值
     *
     * @param key
     * @param db
     * @return java.util.List<java.lang.String>
     * @author Sunhj
     * @date 2019年10月26日 10:58
     */
    public Object hashGetObject(Serializable key, Serializable hashKey, int db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 删除一个或者多个hash表字段
     *
     * @param key
     * @param db
     * @param fields
     * @return java.lang.Long
     * @author Sunhj
     * @date 2019年10月26日 10:15
     */
    public Long hashDelete(Serializable key, int db, Serializable... fields) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        HashOperations<Serializable, Serializable, Serializable> operations = redisTemplate.opsForHash();
        return operations.delete(key, (Object) fields);
    }

    /**
     * 删除一个hash表字段
     *
     * @param key
     * @param db
     * @param fields
     * @return java.lang.Long
     * @author Sunhj
     * @date 2019年10月26日 10:15
     */
    public boolean hashDeleteOne(Serializable key, String fields, int db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        HashOperations<Serializable, Serializable, Serializable> operations = redisTemplate.opsForHash();
        return operations.delete(key, fields) == 1;
    }

    /**
     * 设置缓存对象
     *
     * @param key 缓存key
     * @param obj 缓存value
     */
    public <T> void setObject(String key, T obj, int expireTime, int db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        redisTemplate.opsForValue().set(key, obj, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 获取指定key的缓存
     *
     * @param key---JSON.parseObject(value, User.class);
     */
    public String getObject(String key, int db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        Object o = redisTemplate.opsForValue().get(key);
        return o == null ? null : toJson(o);
    }

    /**
     * 判断当前key值 是否存在
     *
     * @param key
     */
    public boolean hasKey(String key, int db) {

        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        return redisTemplate.hasKey(key);
    }


    /**
     * 获取指定key的缓存
     *
     * @param key
     */
    public String getString(String key, int db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        Object o = redisTemplate.opsForValue().get(key);
        String s = o == null ? null : toJson(o);
        return s;
    }


    /**
     * 删除指定key的缓存
     *
     * @param key
     */
    public void delete(String from, String key, int db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        redisTemplate.delete(key);
    }

    /**
     * @param key
     * @throws
     * @Title: expire
     * @Description: 更新key的失效时间
     */
    public Boolean expire(String key, int seconds, int db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    /**
     * 移除并获取列表中第一个元素
     *
     * @param key
     * @param db
     * @return void
     * @author sunhj
     * @date 2019年10月26日 14:35
     */
    public String listLeftPop(Serializable key, int db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        Object leftPop = redisTemplate.opsForList().leftPop(key);
        if (leftPop == null) {
            return null;
        }
        return JSON.toJSONString(leftPop);
    }

    /**
     * 移除并获取列表最后一个元素
     *
     * @param key
     * @param db
     * @return java.lang.Object
     * @author sunhj
     * @date 2019年10月26日 14:40
     */
    public String listRightPop(Serializable key, int db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        ListOperations<Serializable, Object> operations = redisTemplate.opsForList();
        Object x = operations.rightPop(key);
        if (x == null) {
            return null;
        }
        return JSON.toJSONString(x);
    }

    /**
     * 获取变量中的指定map键是否有值,如果存在该map键则获取值，没有则返回null。
     *
     * @param key
     * @param field
     * @param db
     * @return T
     * @author Sunhj
     * @date 2019年10月26日 8:41
     */
    public <T> T hashGet(Serializable key, Serializable field, Class<T> t, int db) {
        try {
            RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
            Object o = redisTemplate.opsForHash().get(key, field);
            String s = o == null ? null : toJson(o);
            return s == null ? null : fromJson(s, t);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 获取变量中的指定map键是否有值,如果存在该map键则获取值(String格式)，没有则返回null。
     *
     * @param key
     * @param field
     * @param db
     * @return T
     * @author Sunhj
     * @date 2019年10月26日 8:41
     */
    public String hashGetString(Serializable key, Serializable field, int db) {

        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        HashOperations<Serializable, Serializable, Serializable> operations = redisTemplate.opsForHash();
        try {
            Serializable serializable = operations.get(key, field);
            if (serializable != null) {
                return serializable.toString();
            }
            return null;
        } catch (Exception e) {
            return null;
        }


    }

    /**
     * 获取变量中的键值对 ??
     *
     * @param key
     * @param db
     * @return java.util.Map<String, String>
     * @author Sunhj
     * @date 2019年10月26日 8:47
     */
    public <T> Map<String, T> hashGetAll(Serializable key, Class<T> t, int db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        HashOperations<Serializable, Serializable, Serializable> operations = redisTemplate.opsForHash();

        Map<Serializable, Serializable> x = operations.entries(key);
        Map<String, T> map = new HashMap<>();

        try {
            for (Serializable xa : x.keySet()) {
                String keyValue = x.get(xa).toString();
                map.put(xa.toString(), JSON.parseObject(keyValue, t));
            }
            return map;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 新增hashMap值
     *
     * @param key
     * @param hashKey
     * @param hashValue
     * @param db
     * @return void
     * @author Sunhj
     * @date 2019年10月26日 9:22
     */
    public <T> boolean hashPut(Serializable key, Serializable hashKey, T hashValue, int db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        HashOperations<Serializable, Serializable, Serializable> operations = redisTemplate.opsForHash();
        operations.put(key, hashKey, JSON.toJSONString(hashValue, SerializerFeature.WriteMapNullValue));
        return true;
    }

    /**
     * 查看hash表中指定字段是否存在
     *
     * @param key
     * @param field
     * @param db
     * @return boolean
     * @author Sunhj
     * @date 2019年10月26日 10:32
     */
    public boolean hashExists(Serializable key, Serializable field, int db) {

        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        HashOperations<Serializable, Serializable, Serializable> operations = redisTemplate.opsForHash();
        return operations.hasKey(key, field);

    }

    /**
     * 存储在list的头部，即添加一个就把它放在最前面的索引处
     *
     * @param key
     * @param value
     * @param db
     * @return java.lang.Long
     * @author sunhj
     * @date 2019年10月26日 14:03
     */
    public Long listLeftPush(Serializable key, Serializable value, int db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        ListOperations<Serializable, Object> operations = redisTemplate.opsForList();
        return operations.leftPush(key, value);
    }

    /**
     * 获取所有的KEY
     *
     * @param key
     */
    public List<Object> getHashKeys(String key, int db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        List<Object> values = redisTemplate.opsForHash().values(key);
        return values;
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String) {
            return String.valueOf(object);
        }
        return JSON.toJSONString(object);
    }

    /**
     * JSON数据，转成Object
     */
    public <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }
}