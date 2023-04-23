package com.itshop.web.support;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContextProvider implements ApplicationContextAware {
    private static ApplicationContext ctx;

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        ApplicationContextProvider.ctx = ctx;
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static Object getBean(String name) {
        checkApplicationContext();
        return ctx.getBean(name);
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     * 如果有多个Bean符合Class, 取出第一个.
     */
    public static Object getBean(Class requiredType) {
        checkApplicationContext();
        return ctx.getBean(requiredType);
    }

    private static void checkApplicationContext() {
        if (ctx == null) {
            throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义ApplicationContextProvider");
        }
    }

}


