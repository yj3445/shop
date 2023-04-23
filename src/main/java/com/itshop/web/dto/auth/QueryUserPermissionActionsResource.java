package com.itshop.web.dto.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QueryUserPermissionActionsResource extends BaseUserAuth implements Serializable {
    @NotNull
    private Integer permissionTargetId;//资源主键ID
}
