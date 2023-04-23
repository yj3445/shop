package com.itshop.web.dto.auth;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserPermissionActionVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *   行为编码
     */
    private String action;
    /**
     *   行为名称
     */
    private String actionName;
    /**
     *   true:有权限，false:无权限
     */
    private boolean selected;

}
