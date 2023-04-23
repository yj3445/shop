package com.itshop.web.annotation;

import java.lang.annotation.*;

/**
 * 忽略身份授权(权限)注解
 *
 * 注解在 controller 方法
 * 添加此注解后对应的方法/请求将不验证授权信息
 * @author liufenglong
 * @date 2020/6/16
 */
@Target({ElementType.METHOD , ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AuthorizationIgnore {
}