package com.itshop.web.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis简单分布式锁
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Component
public class RedisSimpleLock implements Lock {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 加锁
     *
     * @param lockKey 唯一的key
     * @param val 值
     * @param seconds 秒
     * @return boolean
     */
    @Override
    public boolean tryLock(String lockKey, String val, long seconds) {
        return redisTemplate.opsForValue().setIfAbsent(lockKey, val, seconds, TimeUnit.SECONDS);
    }

    /**
     * 解锁
     *
     * @param lockKey  唯一的key
     * @param val 值
     * @return boolean
     */
    @Override
    public boolean unlock(String lockKey, String val) {
        Object obj = redisTemplate.opsForValue().get(lockKey);
        if (obj != null && val.equalsIgnoreCase(obj.toString())) {
            return redisTemplate.delete(lockKey);
        }
        return false;
    }
}

