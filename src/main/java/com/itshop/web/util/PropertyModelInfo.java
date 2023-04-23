package com.itshop.web.util;

import lombok.Data;

@Data
public class PropertyModelInfo {

    /**
     * 属性名称
     */
    private String propertyName;
    /**
     * 属性注释
     */
    private String propertyComment;
    /**
     * 属性值
     */
    private Object value;
    /**
     * 返回值类型
     */
    private Class<?> returnType;
}
