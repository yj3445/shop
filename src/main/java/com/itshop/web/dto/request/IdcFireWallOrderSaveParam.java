package com.itshop.web.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * idc-防火墙-订单-保存-请求参数
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Data
public class IdcFireWallOrderSaveParam  implements Serializable {

    /**
     * 端口信息
     */
    @Data
    public static class PortInfo {
        /**
         * 端口
         */
        private Integer port;

        /**
         *
         * 1-in-外网放行端口
         * 0-out-内网禁用端口
         */
        private Integer inOrOut;

        /**
         * 协议类型
         * (Any,TCP,UDP,PPP)
         */
        private String protocol;

    }

    /**
     * 防火墙订单id
     */
    private Integer orderId;

    /**
     * 启用标识(0-关闭,1-启用)
     */
    private Boolean open;

    /**
     * 互联网接入-订单id
     */
    private Integer internetAccessOrderId;

    /**
     * 端口信息集合
     */
    private List<PortInfo> portInfos;


    /**
     * 创建人
     */
    private Integer createrBy;

    /**
     * 互联网接入-订单类型（本地固定传 “CUSTOM”）
     */
    private String internetAccessOrderType;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 总价
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private BigDecimal totalPrice;
}
