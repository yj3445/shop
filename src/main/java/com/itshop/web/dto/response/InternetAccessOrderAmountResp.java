package com.itshop.web.dto.response;

import lombok.Data;

/**
 * 互联网接入产品-订单金额-响应
 */
@Data
public class InternetAccessOrderAmountResp {

    /***
     * 服务提供商-订单金额
     */
    private InternetAccessOrderPriceResp serviceProviderOrderAmount;

    /**
     * 一级代理商-订单金额
     */
    private InternetAccessOrderPriceResp agentLevel1OrderAmount;

    /**
     * 二级代理商-订单金额
     */
    private InternetAccessOrderPriceResp agentLevel2OrderAmount;

    /**
     * 三级代理商-订单金额
     */
    private InternetAccessOrderPriceResp agentLevel3OrderAmount;

    /**
     * 客户公司-订单金额
     */
    private InternetAccessOrderPriceResp customerCompanyOrderAmount;

    /**
     * 订单金额
     */
    private InternetAccessOrderPriceResp orderAmount;

    public InternetAccessOrderAmountResp(){
        this.serviceProviderOrderAmount = new InternetAccessOrderPriceResp();
        this.agentLevel1OrderAmount = new InternetAccessOrderPriceResp();
        this.agentLevel2OrderAmount = new InternetAccessOrderPriceResp();
        this.agentLevel3OrderAmount = new InternetAccessOrderPriceResp();
        this.customerCompanyOrderAmount = new InternetAccessOrderPriceResp();
        this.orderAmount= new InternetAccessOrderPriceResp();
    }

}
