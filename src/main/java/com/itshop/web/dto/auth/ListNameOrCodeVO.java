package com.itshop.web.dto.auth;

import lombok.Data;

import java.io.Serializable;

@Data
public class ListNameOrCodeVO implements Serializable {
    private Integer id;
    private String name;
    private String code;
}
