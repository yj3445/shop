package com.itshop.web.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 公司账单查询参数
 *
 * @Auther: LiuFL
 * @Date: 2023/1/14
 * @Description: 公司账单查询参数
 */
@Data
public class CompanyBillQueryParam implements Serializable {
    /**
     * 结算单ID
     */
    private Integer statementId;

    /**
     * 账单-开始年月(yyyyMM)
     */
    private String startBillYearMonth;

    /**
     * 账单-开始年月(yyyyMM)
     */
    private String endBillYearMonth;

    /**
     * 产品分类
     *
     * 编码  名称
     * ISP	互联网接入
     * ACC	应用加速
     * IDC	网络安全
     * EXC	专享云
     * IOT	网联网
     * AI	人工智能
     */
    private List<String> categoryCode;

    /**
     * 组织ID
     */
    private Integer orgId;

    /**
     * 业务编码
     */
    private String businessId;

}
