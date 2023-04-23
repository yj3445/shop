package com.itshop.web.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 行为
 *
 * @author liufenglong
 * @date 2022/7/5
 */
@Data
public class Action implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;
}

