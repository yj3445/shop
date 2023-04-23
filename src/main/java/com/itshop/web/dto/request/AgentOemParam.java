package com.itshop.web.dto.request;

import lombok.Data;

/**
 * @author liufenglong
 * @date 2022/7/19
 */
@Data
public class AgentOemParam {

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
