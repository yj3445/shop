package com.itshop.web.dto.auth;

import lombok.Data;

import java.util.List;

@Data
public class ListUserInfoVO extends  ListUserVO {

    /**
     *   关联角色IDs
     */
    private List<Integer> roleIds;

    /**
     *   关联用户组IDs
     */
    private List<Integer>  groupIds;


}
