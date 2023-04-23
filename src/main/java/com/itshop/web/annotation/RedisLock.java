package com.itshop.web.annotation;

import java.lang.annotation.*;


/**
 * RedisLock 注解
 *
 * 注解在 public 方法
 * 添加此注解后对应的方法 将会尝试获得一个redis锁
 *
 * @author liufenglong
 * @date 2020/11/11
 * */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RedisLock {

    /**
     * 过期秒数,默认为60秒
     *
     * @return 轮询锁的时间
     */
    int expire() default 60;

    /**
     * 缓存值
     *
     * @return
     */
    String key() default "";
}
