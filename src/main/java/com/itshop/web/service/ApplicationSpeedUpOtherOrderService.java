package com.itshop.web.service;

import com.google.common.collect.Lists;
import com.itshop.web.dao.price.ApplicationSpeedUpProductPriceRepository;
import com.itshop.web.dto.price.ApplicationSpeedUpProductPriceConfig;
import com.itshop.web.dto.price.InternetAccessProductPriceConfig;
import com.itshop.web.dto.request.ApplicationSpeedUpOtherOrderSaveParam;
import com.itshop.web.dto.response.ApplicationSpeedUpOrderPriceResp;
import com.itshop.web.manager.ApplicationSpeedUpOtherOrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;


@Service
public class ApplicationSpeedUpOtherOrderService {

    @Autowired
    ApplicationSpeedUpOtherOrderManager applicationSpeedUpOtherOrderManager;

    @Autowired
    ApplicationSpeedUpProductPriceRepository applicationSpeedUpProductPriceRepository;

    /**
     * 计算总价和折扣金额
     * @param speedUpOrderSaveParam
     * @return
     */
    public ApplicationSpeedUpOrderPriceResp calcTotalPriceAndDiscount(ApplicationSpeedUpOtherOrderSaveParam speedUpOrderSaveParam) {
        ApplicationSpeedUpOrderPriceResp resp = new ApplicationSpeedUpOrderPriceResp();
        resp.setPriceItemList(Lists.newArrayList());
        resp.setBasicPrice(BigDecimal.ZERO);
        ApplicationSpeedUpProductPriceConfig productPrice = applicationSpeedUpProductPriceRepository.getApplicationSpeedUpProductPrice(speedUpOrderSaveParam.getProductId());
        InternetAccessProductPriceConfig.DiscountRateConfig noDiscount = new InternetAccessProductPriceConfig.DiscountRateConfig();
        noDiscount.setDiscountRate(100);
        //缴费周期-折扣
        InternetAccessProductPriceConfig.DiscountRateConfig discountConfig = productPrice.getPaymentCycle().stream()
                .filter(p -> p.getValue().equals(speedUpOrderSaveParam.getPaymentCycle()))
                .findFirst()
                .orElseGet(() -> noDiscount);
        resp.setPaymentCycleDiscountRateConfig(discountConfig);
        resp.setPaymentCycleDiscountPrice(resp.getBasicPrice()
                .multiply(BigDecimal.valueOf(-1))
                .multiply(BigDecimal.valueOf(100 - discountConfig.getDiscountRate()))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
        //付费方式-折扣
        discountConfig = productPrice.getPaymentMethod().stream()
                .filter(p -> p.getValue().equals(speedUpOrderSaveParam.getPaymentMethod()))
                .findFirst()
                .orElseGet(() -> noDiscount);
        resp.setPaymentMethodDiscountRateConfig(discountConfig);
        resp.setPaymentMethodDiscountPrice(resp.getBasicPrice()
                .multiply(BigDecimal.valueOf(-1))
                .multiply(BigDecimal.valueOf(100 - discountConfig.getDiscountRate()))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
        resp.setTotalPrice(resp.getBasicPrice()
                .add(resp.getPaymentCycleDiscountPrice())
                .add(resp.getPaymentMethodDiscountPrice()));
        return resp;
    }

    public int save(ApplicationSpeedUpOtherOrderSaveParam orderSaveParam) {
        ApplicationSpeedUpOrderPriceResp priceResp = calcTotalPriceAndDiscount(orderSaveParam);
        return applicationSpeedUpOtherOrderManager.save(orderSaveParam, priceResp);
    }
}
