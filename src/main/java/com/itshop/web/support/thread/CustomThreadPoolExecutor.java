package com.itshop.web.support.thread;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 自定义线程池
 *
 * @author liufenglong
 * @date 2022/3/11
 */
public class CustomThreadPoolExecutor extends ThreadPoolExecutor {

    /**
     * 线程池名称
     */
    private String threadPoolName;

    private String defaultTaskName = "defaultTask";

    /**
     * The default rejected execution handler
     */
    private static final RejectedExecutionHandler defaultHandler = new AbortPolicy();

    private Map<String, String> runnableNameMap = new ConcurrentHashMap<>();

    public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                    BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, defaultHandler);
    }

    public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                    BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler, String threadPoolName) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        this.threadPoolName = threadPoolName;
    }

    @Override
    public void execute(Runnable command) {
        runnableNameMap.putIfAbsent(command.getClass().getSimpleName(), defaultTaskName);
        super.execute(command);
    }

    public void execute(Runnable command, String taskName) {
        runnableNameMap.putIfAbsent(command.getClass().getSimpleName(), taskName);
        super.execute(command);
    }

    public Future<?> submit(Runnable task, String taskName) {
        runnableNameMap.putIfAbsent(task.getClass().getSimpleName(), taskName);
        return super.submit(task);
    }

    public <T> Future<T> submit(Callable<T> task, String taskName) {
        runnableNameMap.putIfAbsent(task.getClass().getSimpleName(), taskName);
        return super.submit(task);
    }

    public <T> Future<T> submit(Runnable task, T result, String taskName) {
        runnableNameMap.putIfAbsent(task.getClass().getSimpleName(), taskName);
        return super.submit(task, result);
    }

    public Future<?> submit(Runnable task) {
        runnableNameMap.putIfAbsent(task.getClass().getSimpleName(), defaultTaskName);
        return super.submit(task);
    }

    public <T> Future<T> submit(Callable<T> task) {
        runnableNameMap.putIfAbsent(task.getClass().getSimpleName(), defaultTaskName);
        return super.submit(task);
    }

    public <T> Future<T> submit(Runnable task, T result) {
        runnableNameMap.putIfAbsent(task.getClass().getSimpleName(), defaultTaskName);
        return super.submit(task, result);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        String threadName = Thread.currentThread().getName();
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        String threadName = Thread.currentThread().getName();
    }

    public String getThreadPoolName() {
        return threadPoolName;
    }
}

