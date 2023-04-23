package com.itshop.web.dto.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QueryOrgDetailResource extends BaseUserAuth implements Serializable {
    /**
     * 应用ID
     */
    @NotEmpty
    private String appId;

    /**
     * 组织ID
     */
    @NotNull
    private Integer orgId;
}
