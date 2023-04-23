package com.itshop.web.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author liufenglong
 * @date 2022/8/24
 */
@Data
public class InternetAccessOrderBeforeChangeQueryParam {
    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

}
