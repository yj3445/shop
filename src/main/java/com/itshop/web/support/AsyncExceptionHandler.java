package com.itshop.web.support;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 异步异常处理器
 *
 * @author liufenglong
 * @date 2022/5/7
 */
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(AsyncExceptionHandler.class);

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        String msg = String.format("Async method has uncaught exception, method: %s,  params:%s ,exception :%s ", method.getName(), Arrays.toString(params), ex.getMessage());
        log.error(msg, ex);
    }
}
