package com.itshop.web.dto.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserEnableResource extends BaseUserAuth implements Serializable {

    private List<UpdateUserIdStatusResource> updateUserIdStatusResources;
}
