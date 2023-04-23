package com.itshop.web.dto.auth;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liufenglong
 * @date 2022/7/5
 */
@Data
public class NameOrCodeVO implements Serializable {
    private String name;
    private String code;
}

