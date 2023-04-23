package com.itshop.web.dto.auth;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liufenglong
 * @date 2022/7/5
 */
@Data
@Builder
public class UserAuthRequestVO implements Serializable {
    protected String apiToken;
    protected String userDomain;
    protected String userId;
}
