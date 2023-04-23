package com.itshop.web.service;

import com.itshop.web.dao.price.InternetAccessUpdateServiceProductPriceConfigRepository;
import com.itshop.web.dto.price.InternetAccessProductPriceConfig;
import com.itshop.web.dto.price.InternetAccessUpdateServiceProductPriceConfig;
import com.itshop.web.dto.request.InternetAccessUpdateServiceOrderSaveParam;
import com.itshop.web.dto.response.InternetAccessUpdateServiceOrderPriceResp;
import com.itshop.web.manager.InternetAccessUpdateServiceOrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 互联网接入-升级服务-订单服务
 */
@Service("internetAccessUpdateServiceOrderService")
public class InternetAccessUpdateServiceOrderService {

    @Autowired
    InternetAccessUpdateServiceProductPriceConfigRepository productPriceConfigRepository;

    @Autowired
    InternetAccessUpdateServiceOrderManager internetAccessUpdateServiceOrderManager;

    /**
     * 计算总价和折扣金额
     *
     * @param orderSaveParam
     * @return
     */
    public InternetAccessUpdateServiceOrderPriceResp calcTotalPriceAndDiscount(InternetAccessUpdateServiceOrderSaveParam orderSaveParam) {
        InternetAccessUpdateServiceOrderPriceResp resp = new InternetAccessUpdateServiceOrderPriceResp();
        resp.setBasicPrice(BigDecimal.valueOf(1000));
        InternetAccessUpdateServiceProductPriceConfig productPrice = productPriceConfigRepository.getInternetAccessProductPrice(orderSaveParam.getProductId());
        InternetAccessProductPriceConfig.DiscountRateConfig noDiscount = new InternetAccessProductPriceConfig.DiscountRateConfig();
        noDiscount.setDiscountRate(100);
        //缴费周期-折扣
        InternetAccessProductPriceConfig.DiscountRateConfig discountConfig = productPrice.getPaymentCycle().stream()
                .filter(p -> p.getValue().equals(orderSaveParam.getPaymentCycle()))
                .findFirst()
                .orElseGet(() -> noDiscount);
        resp.setPaymentCycleDiscountPrice(resp.getBasicPrice()
                .multiply(BigDecimal.valueOf(-1))
                .multiply(BigDecimal.valueOf(100 - discountConfig.getDiscountRate()))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
        //付费方式-折扣
        discountConfig = productPrice.getPaymentMethod().stream()
                .filter(p -> p.getValue().equals(orderSaveParam.getPaymentMethod()))
                .findFirst()
                .orElseGet(() -> noDiscount);
        resp.setPaymentMethodDiscountPrice(resp.getBasicPrice()
                .multiply(BigDecimal.valueOf(-1))
                .multiply(BigDecimal.valueOf(100 - discountConfig.getDiscountRate()))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
        resp.setTotalPrice(resp.getBasicPrice()
                .add(resp.getPaymentCycleDiscountPrice())
                .add(resp.getPaymentMethodDiscountPrice()));
        return resp;
    }

    /**
     * 保存互联网接入-升级服务订单
     *
     * @param orderSaveParam
     * @return
     */
    public int save(InternetAccessUpdateServiceOrderSaveParam orderSaveParam) {
        InternetAccessUpdateServiceOrderPriceResp priceResp = calcTotalPriceAndDiscount(orderSaveParam);
        return internetAccessUpdateServiceOrderManager.save(orderSaveParam, priceResp);
    }
}
