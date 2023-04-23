package com.itshop.web.annotation;

import java.lang.annotation.*;

/**
 * @author liufenglong
 * @date 2022/8/4
 */
@Target({ElementType.METHOD , ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PropertyName {
    String name();
}
