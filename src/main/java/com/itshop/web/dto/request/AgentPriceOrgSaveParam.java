package com.itshop.web.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 定价序列-组织成员 保存参数
 *
 * @author liufenglong
 * @date 2022/7/23
 */
@Data
public class AgentPriceOrgSaveParam  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 代理-定价序列id
     */
    private Integer agentPriceId;

    /**
     * 组织id
     */
    private Integer targetOrgId;

    /**
     * 组织名称
     */
//    private String targetOrgName;
}
