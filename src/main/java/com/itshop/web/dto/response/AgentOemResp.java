package com.itshop.web.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liufenglong
 * @date 2022/7/19
 */
@Data
public class AgentOemResp implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 代理设置
     */
    private Integer agentOemId;

    /**
     * 组织id
     */
    private Integer orgId;

    /**
     * 站点名称
     */
    private String siteName;

    /**
     * 自备域名
     */
    private String customDomainName;

    /**
     * 自备域名是否启用(0-未启用,1-启用)
     */
    private Boolean customDomainEnable;

    /**
     * 首页背景图
     */
    private String homePageBackgroundImage;

    /**
     * 创建人
     */
    private Integer createrBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 操作人
     */
    private Integer modifiedBy;

    /**
     * 更新时间
     */
    private Date modifiedTime;


    /**
     * 主标题
     */
    private String mainTitle;
    /**
     * 副标题
     */
    private String subTitle;
    /**
     * 背景色
     */
    private String backGroundColor;

}
