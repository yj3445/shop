package com.itshop.web.manager;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.itshop.web.bo.ApplicationSpeedUpOrderSaveResult;
import com.itshop.web.dao.SequenceNumberRepository;
import com.itshop.web.dao.mysql.*;
import com.itshop.web.dao.po.*;
import com.itshop.web.dto.PageParam;
import com.itshop.web.dto.request.ApplicationSpeedUpOrderQueryParam;
import com.itshop.web.dto.request.ApplicationSpeedUpOrderSaveParam;
import com.itshop.web.dto.response.ApplicationSpeedUpOrderPriceResp;
import com.itshop.web.dto.response.ApplicationSpeedUpOrderResp;
import com.itshop.web.enums.DeviceControlFinalTransCode;
import com.itshop.web.enums.DeviceControlServiceType;
import com.itshop.web.enums.DeviceControlTransCode;
import com.itshop.web.util.ListUtil;
import com.itshop.web.util.PageInfoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 应用加速服务
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Service
public class ApplicationSpeedUpOrderManager {
    @Autowired
    TApplicationSpeedUpOrderDAO tApplicationSpeedUpOrderDAO;

    @Autowired
    TApplicationSpeedUpOrderDetailDAO tApplicationSpeedUpOrderDetailDAO;

    @Autowired
    TApplicationSpeedUpOrderBeforeChangeDAO tApplicationSpeedUpOrderBeforeChangeDAO;

    @Autowired
    TApplicationSpeedUpOrderDetailBeforeChangeDAO tApplicationSpeedUpOrderDetailBeforeChangeDAO;

    @Autowired
    DeviceControlTransManager deviceControlTransManager;

    @Autowired
    SequenceNumberRepository sequenceNumberRepository;

    @Autowired
    TProductDAO tProductDAO;

    @Autowired
    TCategoryDAO tCategoryDAO;

    /**
     * 保存应用加速订单
     *
     * @param speedUpOrderSaveParam 应用加速订单保存参数
     * @param orderPriceResp 订单价格
     * @return int
     */
    @Transactional(value = "mysqlTransactionManager",rollbackFor = Exception.class)
    public ApplicationSpeedUpOrderSaveResult save(ApplicationSpeedUpOrderSaveParam speedUpOrderSaveParam, ApplicationSpeedUpOrderPriceResp orderPriceResp) {
        Integer changeId = 0;
        TApplicationSpeedUpOrder speedUpOrder = null;
        boolean isNewAdd = false;
        List<String> oldUrls = Lists.newArrayList();
        if (speedUpOrderSaveParam.getOrderId() != null && speedUpOrderSaveParam.getOrderId() > 0) {
            speedUpOrder = tApplicationSpeedUpOrderDAO.selectByPrimaryKey(speedUpOrderSaveParam.getOrderId());
            if (speedUpOrder != null) {
                //保存更新前记录
                TApplicationSpeedUpOrderBeforeChange speedUpOrderBeforeChange = new TApplicationSpeedUpOrderBeforeChange();
                BeanUtils.copyProperties(speedUpOrder, speedUpOrderBeforeChange);
                speedUpOrderBeforeChange.setCreateTime(new Date());
                speedUpOrderBeforeChange.setCreaterBy(speedUpOrderSaveParam.getCreaterBy());
                speedUpOrderBeforeChange.setModifiedBy(speedUpOrderSaveParam.getCreaterBy());
                speedUpOrderBeforeChange.setModifiedTime(new Date());
                speedUpOrderBeforeChange.setIsDeleted(false);
                tApplicationSpeedUpOrderBeforeChangeDAO.insert(speedUpOrderBeforeChange);
                changeId = speedUpOrderBeforeChange.getChangeId();
                List<TApplicationSpeedUpOrderDetail> orderDetails = tApplicationSpeedUpOrderDetailDAO.selectByOrderId(ImmutableList.of(speedUpOrder.getOrderId()));
                if (!CollectionUtils.isEmpty(orderDetails)) {
                    oldUrls = orderDetails.stream().map(p -> p.getUrl()).distinct().collect(Collectors.toList());
                    List<TApplicationSpeedUpOrderDetailBeforeChange> list = Lists.newArrayList();
                    orderDetails.forEach(tApplicationSpeedUpOrderDetail -> {
                        TApplicationSpeedUpOrderDetailBeforeChange detailBeforeChange = new TApplicationSpeedUpOrderDetailBeforeChange();
                        detailBeforeChange.setChangeId(speedUpOrderBeforeChange.getChangeId());
                        BeanUtils.copyProperties(tApplicationSpeedUpOrderDetail, detailBeforeChange);
                        detailBeforeChange.setCreaterBy(speedUpOrderSaveParam.getCreaterBy());
                        detailBeforeChange.setCreateTime(new Date());
                        detailBeforeChange.setModifiedBy(detailBeforeChange.getCreaterBy());
                        detailBeforeChange.setModifiedTime(new Date());
                        detailBeforeChange.setIsDeleted(false);
                        list.add(detailBeforeChange);
                    });
                    tApplicationSpeedUpOrderDetailBeforeChangeDAO.insertBatch(list);
                }
            }
        }
        Integer orderId = 0;
        if (speedUpOrder == null) {
            isNewAdd = true;
            speedUpOrder = new TApplicationSpeedUpOrder();
            BeanUtils.copyProperties(speedUpOrderSaveParam, speedUpOrder);
            BeanUtils.copyProperties(orderPriceResp, speedUpOrder);
            speedUpOrder.setOrderNo(sequenceNumberRepository.getApplicationSpeedUpOrderNo());
            speedUpOrder.setCreateTime(new Date());
            speedUpOrder.setModifiedBy(speedUpOrder.getCreaterBy());
            speedUpOrder.setModifiedTime(new Date());
            speedUpOrder.setIsDeleted(false);
            tApplicationSpeedUpOrderDAO.insertSelective(speedUpOrder);
        } else {
            BeanUtils.copyProperties(speedUpOrderSaveParam, speedUpOrder);
            BeanUtils.copyProperties(orderPriceResp, speedUpOrder);
            speedUpOrder.setCreaterBy(null);
            speedUpOrder.setCreateTime(null);
            speedUpOrder.setModifiedBy(speedUpOrder.getCreaterBy());
            speedUpOrder.setModifiedTime(new Date());
            speedUpOrder.setIsDeleted(false);
            tApplicationSpeedUpOrderDAO.updateByPrimaryKeySelective(speedUpOrder);
            tApplicationSpeedUpOrderDetailDAO.deleteByOrderId(speedUpOrder.getOrderId());
        }
        List<String> newUrls = Lists.newArrayList();
        orderId = speedUpOrder.getOrderId();
        if (!CollectionUtils.isEmpty(speedUpOrderSaveParam.getUrlConfigs())) {
            newUrls = speedUpOrderSaveParam.getUrlConfigs().stream()
                    .map(ApplicationSpeedUpOrderSaveParam.UrlConfig::getUrl)
                    .distinct().collect(Collectors.toList());
            List<TApplicationSpeedUpOrderDetail> list = Lists.newArrayList();
            Integer finalOrderId = orderId;
            speedUpOrderSaveParam.getUrlConfigs().forEach(urlConfig -> {
                TApplicationSpeedUpOrderDetail orderDetail = new TApplicationSpeedUpOrderDetail();
                orderDetail.setOrderId(finalOrderId);
                BeanUtils.copyProperties(urlConfig, orderDetail);
                orderDetail.setStatus(-1);
                orderDetail.setCreaterBy(speedUpOrderSaveParam.getCreaterBy());
                orderDetail.setCreateTime(new Date());
                orderDetail.setModifiedBy(orderDetail.getCreaterBy());
                orderDetail.setModifiedTime(new Date());
                orderDetail.setIsDeleted(false);
                orderPriceResp.getPriceItemList().stream().filter(p -> p.getUrlSpeedUpId().equals(urlConfig.getUrlSpeedUpId()))
                        .findFirst()
                        .ifPresent(p -> {
                            orderDetail.setPriceUnit(p.getPriceUnit());
                            orderDetail.setTotalPrice(p.getTotalPrice());
                        });
                list.add(orderDetail);
            });
            tApplicationSpeedUpOrderDetailDAO.insertBatch(list);
        }
        //中间状态
        List<Integer> middleStatus = Lists.newLinkedList();
        middleStatus.add(DeviceControlTransCode.INITIAL.getCode());
        if (isNewAdd) {
            if (!CollectionUtils.isEmpty(newUrls)) {
                middleStatus.add(DeviceControlFinalTransCode.URL_SPEED_UP_ADD.getStatus());
            }
        } else {
            if (!ListUtil.equalsStr(oldUrls, newUrls)) {
                if (!CollectionUtils.isEmpty(oldUrls)) {
                    middleStatus.add(DeviceControlFinalTransCode.URL_SPEED_UP_REMOVE.getStatus());
                }
                if (!CollectionUtils.isEmpty(newUrls)) {
                    middleStatus.add(DeviceControlTransCode.URL_SPEED_UP_ADD.getCode());
                }
            }
        }
        TProduct product= tProductDAO.selectByPrimaryKey(speedUpOrder.getProductId());
        ApplicationSpeedUpOrderSaveResult result = new ApplicationSpeedUpOrderSaveResult();
        result.setServiceType(DeviceControlServiceType.URL_SPEED_UP.getCode());
        result.setOrderId(orderId);
        if(product !=null) {
            result.setProductCode(product.getProductCode());
        }
        result.setOrderNo(speedUpOrder.getOrderNo());
        result.setChangeId(changeId);
        result.setTransId(0);
        result.setMiddleStatus(middleStatus);
        result.setTransCurrent(DeviceControlTransCode.INITIAL.getCode());
        result.setOldUrls(oldUrls);
        result.setNewUrls(newUrls);
        if (middleStatus.size() > 1) {
            result.setTransId(deviceControlTransManager.insertSelective(result, speedUpOrderSaveParam.getCreaterBy()));
        }
        return result;
    }

    /**
     * 得到 应用加速-订单详情
     *
     * @param orderId 订单id
     * @return int
     */
    public ApplicationSpeedUpOrderResp getOrderDetail(Integer orderId) {
        ApplicationSpeedUpOrderResp productDOrderDetailResp = new ApplicationSpeedUpOrderResp();
        TApplicationSpeedUpOrder dOrder = tApplicationSpeedUpOrderDAO.selectByPrimaryKey(orderId);
        if (dOrder != null) {
            TProduct product= tProductDAO.selectByPrimaryKey(dOrder.getProductId());
            if(product !=null){
                dOrder.setProductCode(product.getProductCode());
                dOrder.setProductName(product.getProductName());
            }
            List<TApplicationSpeedUpOrderDetail> orderDetails = tApplicationSpeedUpOrderDetailDAO.selectByOrderId(ImmutableList.of(orderId));
            BeanUtils.copyProperties(dOrder, productDOrderDetailResp);
            if(product !=null){
                TCategory category= tCategoryDAO.selectByPrimaryKey(product.getCategoryId());
                if(category !=null){
                    productDOrderDetailResp.setCategoryId(category.getCategoryId());
                    productDOrderDetailResp.setCategoryCode(category.getCategoryCode());
                    productDOrderDetailResp.setCategoryName(category.getCategoryName());
                    productDOrderDetailResp.setCategoryLevel(category.getCategoryLevel());
                }
            }
            productDOrderDetailResp.setOrderDetails(Lists.newArrayList());
            if (!CollectionUtils.isEmpty(orderDetails)) {
                orderDetails.forEach(tInternetAccessProductDOrderDetail -> {
                    ApplicationSpeedUpOrderResp.OrderDetail orderDetail = new ApplicationSpeedUpOrderResp.OrderDetail();
                    BeanUtils.copyProperties(tInternetAccessProductDOrderDetail, orderDetail);
                    productDOrderDetailResp.getOrderDetails().add(orderDetail);
                });
            }
        }
        return productDOrderDetailResp;
    }

    /**
     * 得到应用加速-订单列表
     *
     * @param pageParam 分页参数
     * @return PageInfo
     */
    public PageInfo<ApplicationSpeedUpOrderResp> selectByQueryParam(PageParam<ApplicationSpeedUpOrderQueryParam> pageParam) {
        String orderBy;
        if (StringUtils.isEmpty(pageParam.getSortName())) {
            orderBy = "create_time desc ";
        } else {
            orderBy = pageParam.getSortName() + (pageParam.getReverse() ? " desc" : " asc");
        }
        PageInfo<TApplicationSpeedUpOrder> pageInfo = PageHelper.offsetPage(pageParam.getOffset(), pageParam.getPageSize())
                .setOrderBy(orderBy)
                .doSelectPageInfo(
                        new ISelect() {
                            @Override
                            public void doSelect() {
                                tApplicationSpeedUpOrderDAO.selectByQueryParam(pageParam.getSearchParam());
                            }
                        }
                );
        return PageInfoUtil.convert(pageInfo, ApplicationSpeedUpOrderResp.class);
    }
}
