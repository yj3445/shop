package com.itshop.web.service;

import com.google.common.collect.Lists;
import com.itshop.web.bo.ApplicationSpeedUpOrderSaveResult;
import com.itshop.web.bo.DeviceControlParam;
import com.itshop.web.dao.price.ApplicationSpeedUpProductPriceRepository;
import com.itshop.web.dao.mysql.TUrlSpeedUpConfigDAO;
import com.itshop.web.dto.price.ApplicationSpeedUpPriceItem;
import com.itshop.web.dto.price.ApplicationSpeedUpProductPriceConfig;
import com.itshop.web.dto.price.InternetAccessProductPriceConfig;
import com.itshop.web.dto.deviceControl.DeviceControlResponseResult;
import com.itshop.web.dto.deviceControl.UrlSpeedUpAddRequest;
import com.itshop.web.dto.deviceControl.UrlSpeedUpQueryResponse;
import com.itshop.web.dao.mysql.TDeviceControlTransDAO;
import com.itshop.web.dao.po.TDeviceControlTrans;
import com.itshop.web.dto.request.ApplicationSpeedUpOrderSaveParam;
import com.itshop.web.dto.response.ApplicationSpeedUpOrderPriceResp;
import com.itshop.web.enums.DeviceControlTransCode;
import com.itshop.web.enums.DeviceControlTransStatus;
import com.itshop.web.exception.BusinessException;
import com.itshop.web.manager.ApplicationSpeedUpOrderManager;
import com.itshop.web.manager.DeviceControlManager;
import com.itshop.web.support.ApplicationContextProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;

@Service("applicationSpeedUpOrderService")
public class ApplicationSpeedUpOrderService {
    @Autowired
    ApplicationSpeedUpOrderManager applicationSpeedUpOrderManager;

    @Autowired
    DeviceControlManager deviceControlManager;

    @Autowired
    TDeviceControlTransDAO deviceControlTransDAO;

    @Autowired
    ApplicationSpeedUpProductPriceRepository applicationSpeedUpProductPriceRepository;

    @Autowired
    TUrlSpeedUpConfigDAO tUrlSpeedUpConfigDAO;

    /**
     * 计算总价和折扣金额
     * @param speedUpOrderSaveParam
     * @return
     */
    public ApplicationSpeedUpOrderPriceResp calcTotalPriceAndDiscount(ApplicationSpeedUpOrderSaveParam speedUpOrderSaveParam) {
        ApplicationSpeedUpOrderPriceResp resp = new ApplicationSpeedUpOrderPriceResp();
        resp.setPriceItemList(Lists.newArrayList());
        resp.setBasicPrice(BigDecimal.ZERO);
        speedUpOrderSaveParam.getUrlConfigs().forEach(urlConfig -> {
            ApplicationSpeedUpPriceItem priceItem = new ApplicationSpeedUpPriceItem();
            BeanUtils.copyProperties(urlConfig,priceItem);
            priceItem.setTotalPrice(BigDecimal.ZERO);
            if(Objects.equals(101,speedUpOrderSaveParam.getProductId())) {
                //月
                if (Objects.equals(1, priceItem.getUnit())) {
                    priceItem.setTotalPrice(priceItem.getPrice()
                            .multiply(BigDecimal.valueOf(priceItem.getSpeed()))
                            .multiply(BigDecimal.valueOf(priceItem.getDuration())));
                }
                //季
                if (Objects.equals(2, priceItem.getUnit())) {
                    priceItem.setTotalPrice(priceItem.getPrice()
                            .multiply(BigDecimal.valueOf(3))
                            .multiply(BigDecimal.valueOf(priceItem.getSpeed()))
                            .multiply(BigDecimal.valueOf(priceItem.getDuration())));
                }
                //年
                if (Objects.equals(3, priceItem.getUnit())) {
                    priceItem.setTotalPrice(priceItem.getPrice()
                            .multiply(BigDecimal.valueOf(12))
                            .multiply(BigDecimal.valueOf(priceItem.getSpeed()))
                            .multiply(BigDecimal.valueOf(priceItem.getDuration())));
                }
            }
            if(Objects.equals(102,speedUpOrderSaveParam.getProductId())) {
                //小时
                if (Objects.equals(5, priceItem.getUnit())) {
                    priceItem.setTotalPrice(priceItem.getPrice()
                            .multiply(BigDecimal.valueOf(priceItem.getSpeed()))
                            .multiply(BigDecimal.valueOf(priceItem.getDuration())));
                }
            }
            resp.getPriceItemList().add(priceItem);
            resp.setBasicPrice(resp.getBasicPrice().add(priceItem.getTotalPrice()));
        });
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

    public int save(ApplicationSpeedUpOrderSaveParam speedUpOrderSaveParam) {
        ApplicationSpeedUpOrderPriceResp orderPriceResp = calcTotalPriceAndDiscount(speedUpOrderSaveParam);
        ApplicationSpeedUpOrderSaveResult saveResult = applicationSpeedUpOrderManager.save(speedUpOrderSaveParam,orderPriceResp);
        if (saveResult.getTransId() != 0) {
            ApplicationSpeedUpOrderService innerService = (ApplicationSpeedUpOrderService) ApplicationContextProvider.getBean("applicationSpeedUpOrderService");
            innerService.callDeviceControl(saveResult, speedUpOrderSaveParam.getCreaterBy());
        }
        return saveResult.getOrderId();
    }

    @Async
    public void callDeviceControl(ApplicationSpeedUpOrderSaveResult saveResult, Integer currentUserId) {
        LinkedList<Integer> afterStatusList = saveResult.getAfterStatusList(saveResult.getTransCurrent());
        for (int i = 0; i < afterStatusList.size(); i++) {
            Integer currentStatus = afterStatusList.get(i);
            int nextStatus = saveResult.getTransEnd();
            if (i < afterStatusList.size() - 1) {
                nextStatus = afterStatusList.get(i + 1);
            }
            if (Objects.equals(DeviceControlTransCode.URL_SPEED_UP_ADD.getCode(), currentStatus)) {
                urlSpeedUpAdd(saveResult, currentUserId,currentStatus, nextStatus);
            }
            if (Objects.equals(DeviceControlTransCode.URL_SPEED_UP_REMOVE.getCode(), currentStatus)) {
                urlSpeedUpRemove(saveResult, currentUserId,currentStatus, nextStatus);
            }
        }
    }

    void urlSpeedUpAdd(ApplicationSpeedUpOrderSaveResult saveResult, Integer currentUserId,
            Integer currentStatus, Integer nextStatus) {
        UrlSpeedUpAddRequest request = new UrlSpeedUpAddRequest();
        request.setBusiness_id(saveResult.getBusiness_id());
        request.setTotal(saveResult.getNewUrls().size());
        request.setUrllist(saveResult.getNewUrls());
        DeviceControlParam<UrlSpeedUpAddRequest> param = new DeviceControlParam<>();
        param.setRequest(request);
        param.setCurrentUserId(currentUserId);
        DeviceControlResponseResult<UrlSpeedUpQueryResponse> responseResult = deviceControlManager.urlSpeedUpAdd(param);
        updateTrans(saveResult, currentUserId, currentStatus, nextStatus,responseResult);
    }

    void urlSpeedUpRemove(ApplicationSpeedUpOrderSaveResult saveResult, Integer currentUserId,
            Integer currentStatus, Integer nextStatus) {
        DeviceControlResponseResult responseResult = new DeviceControlResponseResult<>();
        responseResult.setCode(DeviceControlResponseResult.SUCCEED);
        updateTrans(saveResult, currentUserId, currentStatus, nextStatus, responseResult);
    }

    private void updateTrans(ApplicationSpeedUpOrderSaveResult saveResult, Integer currentUserId,
                             Integer currentStatus, Integer nextStatus,
                             DeviceControlResponseResult responseResult) {
        TDeviceControlTrans tDeviceControlTrans = new TDeviceControlTrans();
        tDeviceControlTrans.setTransId(saveResult.getTransId());
        tDeviceControlTrans.setModifiedBy(currentUserId);
        tDeviceControlTrans.setModifiedTime(new Date());
        if (DeviceControlResponseResult.SUCCEED.equalsIgnoreCase(responseResult.getCode())) {
            tDeviceControlTrans.setProcess(currentStatus);
            if (Objects.equals(nextStatus, saveResult.getTransEnd())) {
                tDeviceControlTrans.setProcess(nextStatus);
                tDeviceControlTrans.setStatus(DeviceControlTransStatus.SUCCEED.getCode());
            }
        }
        deviceControlTransDAO.updateByPrimaryKeySelective(tDeviceControlTrans);
    }
}
