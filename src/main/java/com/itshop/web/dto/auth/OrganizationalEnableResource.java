package com.itshop.web.dto.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrganizationalEnableResource extends BaseUserAuth implements Serializable {
    /**
     * 组织ID
     */
    @NotNull
    private Integer orgId;

    /**
     * 状态
     */
    @NotNull

    private Integer status;
}
