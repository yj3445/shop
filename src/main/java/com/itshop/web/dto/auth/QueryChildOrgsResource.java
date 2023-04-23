package com.itshop.web.dto.auth;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryChildOrgsResource implements Serializable {
    protected String apiToken;
    protected String userDomain;
    protected String userId;
    /**
     * 组织id
     */
    private Integer orgId;
}
