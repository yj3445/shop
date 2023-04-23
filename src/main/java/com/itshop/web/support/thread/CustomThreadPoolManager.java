package com.itshop.web.support.thread;


import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义线程池管理器
 *
 * @author liufenglong
 * @date 2022/3/11
 */
public class CustomThreadPoolManager {

    /**
     * 存储线程池对象，Key:名称 Value:对象
     */
    private Map<String, CustomThreadPoolExecutor> threadPoolExecutorMap = new HashMap<>();

    private static CustomThreadPoolManager instance = new CustomThreadPoolManager();

    private CustomThreadPoolManager(){

    }

    public static CustomThreadPoolManager getInstance(){
        return instance;
    }

    /**
     * 创建自定义线程池
     *
     * @param threadPoolProperties 线程池属性
     * @param threadPoolName 线程池名称
     * @return CustomThreadPoolExecutor
     */
    public CustomThreadPoolExecutor createThreadPoolExecutor(ThreadPoolProperties threadPoolProperties, String threadPoolName) {
        if (!threadPoolExecutorMap.containsKey(threadPoolName)) {
            CustomThreadPoolExecutor executor = new CustomThreadPoolExecutor(threadPoolProperties.getCorePoolSize(),
                    threadPoolProperties.getMaximumPoolSize(),
                    threadPoolProperties.getKeepAliveTime(),
                    threadPoolProperties.getUnit(),
                    new ArrayBlockingQueue<>(threadPoolProperties.getWorkQueueSize()),
                    new ThreadFactoryBuilder().setNameFormat(threadPoolName + "-%d").build(),
                    new ThreadPoolExecutor.AbortPolicy(),
                    threadPoolName);
            threadPoolExecutorMap.put(threadPoolName, executor);
        }
        return threadPoolExecutorMap.get(threadPoolName);
    }
}
