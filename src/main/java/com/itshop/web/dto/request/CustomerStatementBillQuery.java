package com.itshop.web.dto.request;

import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 客户账目查询
 *
 * @Auther LiuFL
 * @Date 2023/1/14
 * @Description 客户账目查询
 */
@Data
public class CustomerStatementBillQuery  implements Serializable {
    /**
     * 账单-开始年月(yyyyMM)
     */
    private String startStatementMonth;

    /**
     * 账单-结束年月(yyyyMM)
     */
    private String endStatementMonth;

    /**
     * 出账日期-开始时间(yyyy-MM-dd HH:mm:ss)
     */
    private String startOutAccountDay;

    /**
     * 出账日期-结束时间(yyyy-MM-dd HH:mm:ss)
     */
    private String endOutAccountDay;

    /**
     * 公司类型(10-服务提供商；20-代理商；30-客户公司)
     */
    private List<Integer> orgType;

    /**
     * 付款方组织-公司名称
     */
    private String payerOrgCompanyName;

    /**
     * 收款组织ID (下钻的时候需要传)
     */
    private Integer payeeOrgId;

    /**
     * 账期年月 (yyyyMM格式 下钻的时候需要传)
     */
    private String statementMonth;

    /**
     * 结算单 (NO_PAY-未结算,PAY-已结算)
     */
    private String paymentStatus;
}
