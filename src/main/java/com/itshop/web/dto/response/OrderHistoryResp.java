package com.itshop.web.dto.response;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * 订单历史
 *
 * @author liufenglong
 * @date 2022/8/4
 */
@Data
public class OrderHistoryResp  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单编码
     */
    private String orderNo;

    /**
     * 产品ID
     */
    private Integer productId;
    
    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 历史详情
     */
    private List<OrderHistoryDetailResp> details;

    public OrderHistoryResp() {
        details = Lists.newArrayList();
    }

    /**
     * 订单历史详情
     */
    @Data
    public static class OrderHistoryDetailResp implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 版本号
         */
        private String version;

        /**
         * 订单状态
         */
        private String state;

        /**
         * 创建人
         */
        private Integer createrBy;

        /**
         * 创建人(用户名)
         */
        private String createrUserName;

        /**
         * 创建时间
         */
        private Date createTime;

        /**
         * 操作人
         */
        private Integer modifiedBy;

        /**
         * 操作人(用户名)
         */
        private String modifiedUserName;

        /**
         * 更新时间
         */
        private Date modifiedTime;

        /**
         * 开始时间
         */
        private Date startTime;

        /**
         * 结束时间
         */
        private Date endTime;

        /**
         * 审核通过时间
         */
        private Date approvedTime;

        /**
         * 交付时间
         */
        private Date deliveryTime;

        /**
         * 审核人
         */
        private Integer auditor;

        /**
         * 审核人(用户名)
         */
        private String auditorUserName;

        /**
         * 订单摘要
         */
        private List<String> orderDigest;
    }
}
