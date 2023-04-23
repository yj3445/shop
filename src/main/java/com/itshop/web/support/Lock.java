package com.itshop.web.support;

/**
 * 锁
 *
 * @author liufenglong
 * @date 2022/5/7
 */
public interface Lock {

    /**
     * 加锁
     *
     * @param lockKey 唯一的key
     * @param val 值
     * @param seconds 秒
     * @return boolean
     */
    boolean tryLock(String lockKey, String val, long seconds);

    /**
     * 解锁
     *
     * @param lockKey  唯一的key
     * @param val 值
     * @return boolean
     */
    boolean unlock(String lockKey, String val);
}
