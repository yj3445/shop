package com.itshop.web.support.thread;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * 线程属性
 *
 * @author liufenglong
 * @date 2022/3/11
 */
@Data
public class ThreadPoolProperties {
    /**
     * 核心线程数大小
     */
    private int corePoolSize = 4;
    /**
     * 最大线程数数
     */
    private int maximumPoolSize = corePoolSize + 1;
    /**
     * 线程存活时间
     */
    private long keepAliveTime = 5L;
    /**
     * 线程存活时间单位
     */
    private TimeUnit unit = TimeUnit.MINUTES;
    /**
     * 工作队列大小
     */
    private int workQueueSize = 1024;
}
