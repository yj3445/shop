package com.itshop.web.dto.response;

import com.google.common.collect.Lists;
import com.itshop.web.dto.auth.OrganizationalDetailVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author liufenglong
 * @date 2022/7/23
 */
@Data
public class AgentPriceOrgResp  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 代理-定价序列id
     */
    private Integer agentPriceId;

    /**
     * 待选组织集合
     */
    List<OrganizationalDetailVO> unSelectList;

    /**
     * 已选组织集合
     */
    List<OrganizationalDetailVO> selectedList;

    public AgentPriceOrgResp(){
        unSelectList = Lists.newArrayList();
        selectedList = Lists.newArrayList();
    }
}
