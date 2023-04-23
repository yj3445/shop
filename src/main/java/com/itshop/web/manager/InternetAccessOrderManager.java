package com.itshop.web.manager;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.itshop.web.annotation.RedisLock;
import com.itshop.web.bo.InternetAccessOrderAuditResult;
import com.itshop.web.bo.InternetAccessOrderBeforeChangeAndDetail;
import com.itshop.web.bo.InternetAccessOrderBeforeChangeQueryParam;
import com.itshop.web.bo.UserInfoVO;
import com.itshop.web.constants.ProductItemConstants;
import com.itshop.web.constants.SnowConstants;
import com.itshop.web.dao.SequenceNumberRepository;
import com.itshop.web.dao.devicecontrol.StatsRepository;
import com.itshop.web.dao.mysql.*;
import com.itshop.web.dao.po.*;
import com.itshop.web.dto.PageParam;
import com.itshop.web.dto.deviceControl.DeviceControlResponseResult;
import com.itshop.web.dto.deviceControl.TrendStackRequest;
import com.itshop.web.dto.deviceControl.TrendStackResponse;
import com.itshop.web.dto.price.InternetAccessProductPriceConfig;
import com.itshop.web.dto.price.InternetAccessProductPriceItem;
import com.itshop.web.dto.request.InternetAccessOrderQueryParam;
import com.itshop.web.dto.request.InternetAccessOrderSaveParam;
import com.itshop.web.dto.request.OrderAuditParam;
import com.itshop.web.dto.request.OrderMonitorQueryParam;
import com.itshop.web.dto.response.InternetAccessOrderAmountResp;
import com.itshop.web.dto.response.InternetAccessOrderPriceResp;
import com.itshop.web.dto.response.InternetAccessOrderResp;
import com.itshop.web.dto.response.OrderHistoryResp;
import com.itshop.web.enums.DeviceControlServiceType;
import com.itshop.web.enums.DeviceControlTransCode;
import com.itshop.web.enums.OrderStatusEnum;
import com.itshop.web.enums.OrderType;
import com.itshop.web.exception.BusinessException;
import com.itshop.web.util.DateUtil;
import com.itshop.web.util.PageInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 互联网接入-接入-自定义-订单服务
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Slf4j
@Service
public class InternetAccessOrderManager {
    @Autowired
    TInternetAccessOrderDAO tInternetAccessOrderDAO;

    @Autowired
    TInternetAccessOrderDetailDAO tInternetAccessOrderDetailDAO;

    @Autowired
    TInternetAccessOrderBeforeChangeDAO tInternetAccessOrderBeforeChangeDAO;

    @Autowired
    TInternetAccessOrderDetailBeforeChangeDAO tInternetAccessOrderDetailBeforeChangeDAO;

    @Autowired
    DeviceControlTransManager deviceControlTransManager;

    @Autowired
    SequenceNumberRepository sequenceNumberRepository;

    @Autowired
    TProductDAO tProductDAO;

    @Autowired
    TProductOemDAO tProductOemDAO;

    @Autowired
    TCategoryDAO tCategoryDAO;

    @Autowired
    StatsRepository statsRepository;

    @Autowired
    TOrderAmountDAO tOrderAmountDAO;

    @Autowired
    StatementBillManager statementBillManager;

    @Autowired
    AuthorizationManager authorizationManager;
    /**
     * 保存互联网接入-自定义-订单
     *
     * @param orderSaveParam 产品D订单保存参数
     * @return int
     */
    @RedisLock(key = "'orgId:'+ #userInfoVO.orgId+':orderId:'+ #orderSaveParam.orderId+':OrderSave'")
    @Transactional(value = "mysqlTransactionManager",rollbackFor = Exception.class)
    public InternetAccessOrderBeforeChangeAndDetail save(InternetAccessOrderSaveParam orderSaveParam,
                                                         InternetAccessOrderAmountResp orderAmountResp,
                        UserInfoVO userInfoVO) {
        boolean isNewAdd = true;
        TInternetAccessOrder productDOrder = null;
        if(orderSaveParam.getOrderId() !=null && orderSaveParam.getOrderId() > 0) {
            isNewAdd = false;
            productDOrder = tInternetAccessOrderDAO.selectByPrimaryKey(orderSaveParam.getOrderId());
            if (productDOrder == null) {
                throw new BusinessException("订单ID不存在,请检查!");
            }
        }
        if(isNewAdd) {
            productDOrder = new TInternetAccessOrder();
            settingOrderProperty(productDOrder, orderSaveParam, orderAmountResp.getOrderAmount(), userInfoVO, true);
            tInternetAccessOrderDAO.insertSelective(productDOrder);
            List<TInternetAccessOrderDetail> details= getOrderDetails(productDOrder,orderSaveParam);
            if(CollectionUtils.isNotEmpty(details)) {
                tInternetAccessOrderDetailDAO.insertBatch(details);
            }
            InternetAccessOrderBeforeChangeAndDetail orderBeforeChangeAndDetail= saveInternetAccessOrderHistory(productDOrder, details, userInfoVO);
            saveOrderAmount(orderAmountResp,orderBeforeChangeAndDetail);
            return orderBeforeChangeAndDetail;
        }
        else {
//            InternetAccessOrderBeforeChangeQueryParam queryParam = new InternetAccessOrderBeforeChangeQueryParam();
//            queryParam.setOrderId(orderSaveParam.getOrderId());
//            // 获取当天的零点
//            LocalDateTime startToday = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
//            // 获取当天结束时间
//            LocalDateTime endToday = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
//            queryParam.setStartDate(DateUtil.localDateTime2Date(startToday));
//            queryParam.setEndDate(DateUtil.localDateTime2Date(endToday));
//            boolean todayHasChanged = tInternetAccessOrderBeforeChangeDAO.selectCountByOrderId(queryParam) == 1;
//            if(todayHasChanged){
//                throw new BusinessException("业务一天仅允许变更一次，请次日再修改!");
//            }
            settingOrderProperty(productDOrder, orderSaveParam, orderAmountResp.getOrderAmount(), userInfoVO, false);
            List<TInternetAccessOrderDetail> details = getOrderDetails(productDOrder, orderSaveParam);
            InternetAccessOrderBeforeChangeAndDetail orderBeforeChangeAndDetail = saveInternetAccessOrderHistory(productDOrder, details, userInfoVO);
            saveOrderAmount(orderAmountResp, orderBeforeChangeAndDetail);
            return orderBeforeChangeAndDetail;
        }
    }

    private Integer saveOrderAmount(InternetAccessOrderAmountResp orderAmountResp,
                                      InternetAccessOrderBeforeChangeAndDetail orderBeforeChangeAndDetail) {
        TOrderAmount orderAmount = new TOrderAmount();
        InternetAccessOrderPriceResp orderPriceResp = orderAmountResp.getOrderAmount();
        orderAmount.setOrderId(orderBeforeChangeAndDetail.getChange().getOrderId());
        orderAmount.setChangeId(orderBeforeChangeAndDetail.getChange().getChangeId());
        orderAmount.setOrderTableName(OrderType.INTERNET_ACCESS_ORDER.getValue());

        orderAmount.setProductId(orderPriceResp.getProductId());
        orderAmount.setProductOemId(orderPriceResp.getProductOemId());
        orderAmount.setOrgId(orderBeforeChangeAndDetail.getChange().getOrgId());
        orderAmount.setOrgFullPath(orderBeforeChangeAndDetail.getChange().getOrgFullPath());
        InternetAccessProductPriceItem productPriceItem = orderPriceResp.getInternetAccessProductPriceItem();
        if (productPriceItem != null) {
            //出口
            InternetAccessProductPriceConfig.DiscountRateConfig discountRateConfig = productPriceItem.getExportDiscountRateConfig();
            if (discountRateConfig != null) {
                orderAmount.setExportDiscountRate(discountRateConfig.getDiscountRate());
                orderAmount.setExportDiscountRulesId(discountRateConfig.getProductOemDiscountRulesId());
            }
            //合同期限
            discountRateConfig = productPriceItem.getContractPeriodDiscountRateConfig();
            if (discountRateConfig != null) {
                orderAmount.setContractPeriodDiscountRate(discountRateConfig.getDiscountRate());
                orderAmount.setContractPeriodDiscountRulesId(discountRateConfig.getProductOemDiscountRulesId());
            }
            //缴费周期
            discountRateConfig = productPriceItem.getPaymentCycleDiscountRateConfig();
            if (discountRateConfig != null) {
                orderAmount.setPaymentCycleDiscountRate(discountRateConfig.getDiscountRate());
                orderAmount.setPaymentCycleDiscountRulesId(discountRateConfig.getProductOemDiscountRulesId());
            }
            //付费方式
            discountRateConfig = productPriceItem.getPaymentMethodDiscountRateConfig();
            if (discountRateConfig != null) {
                orderAmount.setPaymentMethodDiscountRate(discountRateConfig.getDiscountRate());
                orderAmount.setPaymentMethodDiscountRulesId(discountRateConfig.getProductOemDiscountRulesId());
            }
        }
        orderAmount.setExportDiscountPrice(orderPriceResp.getExportDiscountPrice());
        orderAmount.setContractPeriodDiscountPrice(orderPriceResp.getContractPeriodDiscountPrice());
        orderAmount.setPaymentCycleDiscountPrice(orderPriceResp.getPaymentCycleDiscountPrice());
        orderAmount.setPaymentMethodDiscountPrice(orderPriceResp.getPaymentMethodDiscountPrice());
        orderAmount.setTotalPrice(orderPriceResp.getTotalPrice());

        //终端价格
        orderPriceResp = orderAmountResp.getCustomerCompanyOrderAmount();
        if (orderPriceResp != null) {
            productPriceItem = orderPriceResp.getInternetAccessProductPriceItem();
            if (productPriceItem != null
                    && productPriceItem.getBroadBandPriceConfig() != null) {
                InternetAccessProductPriceConfig.BroadBandPriceConfig broadBandPriceConfig = productPriceItem.getBroadBandPriceConfig();
                orderAmount.setEndUserOemItemPriceId(broadBandPriceConfig.getProductOemItemPriceId());
                orderAmount.setEndUserPrice(BigDecimal.valueOf(broadBandPriceConfig.getPrice()));
                orderAmount.setEndUserPriceUnit(broadBandPriceConfig.getPriceUnit());
            }
            orderAmount.setSalesTotalPrice(orderPriceResp.getTotalPrice());
        }
        //供应商
        orderPriceResp = orderAmountResp.getServiceProviderOrderAmount();
        if (orderPriceResp != null) {
            productPriceItem = orderPriceResp.getInternetAccessProductPriceItem();
            if (productPriceItem != null
                    && productPriceItem.getBroadBandPriceConfig() != null) {
            }
            orderAmount.setProviderTotalPrice(orderPriceResp.getTotalPrice());
        }
        //一级代理商
        orderPriceResp = orderAmountResp.getAgentLevel1OrderAmount();
        if (orderPriceResp != null) {
            productPriceItem = orderPriceResp.getInternetAccessProductPriceItem();
            if (productPriceItem != null
                    && productPriceItem.getBroadBandPriceConfig() != null) {
                InternetAccessProductPriceConfig.BroadBandPriceConfig broadBandPriceConfig = productPriceItem.getBroadBandPriceConfig();
                orderAmount.setAgentLevel1OemItemPriceId(broadBandPriceConfig.getProductOemItemPriceId());
                orderAmount.setAgentLevel1Price(BigDecimal.valueOf(broadBandPriceConfig.getPrice()));
                orderAmount.setAgentLevel1PriceUnit(broadBandPriceConfig.getPriceUnit());
            }
            orderAmount.setAgentLevel1TotalPrice(orderPriceResp.getTotalPrice());
        }
        //二级代理商
        orderPriceResp = orderAmountResp.getAgentLevel2OrderAmount();
        if (orderPriceResp != null) {
            productPriceItem = orderPriceResp.getInternetAccessProductPriceItem();
            if (productPriceItem != null
                    && productPriceItem.getBroadBandPriceConfig() != null) {
                InternetAccessProductPriceConfig.BroadBandPriceConfig broadBandPriceConfig = productPriceItem.getBroadBandPriceConfig();
                orderAmount.setAgentLevel2OemItemPriceId(broadBandPriceConfig.getProductOemItemPriceId());
                orderAmount.setAgentLevel2Price(BigDecimal.valueOf(broadBandPriceConfig.getPrice()));
                orderAmount.setAgentLevel2PriceUnit(broadBandPriceConfig.getPriceUnit());
            }
            orderAmount.setAgentLevel2TotalPrice(orderPriceResp.getTotalPrice());
        }
        //三级代理商
        orderPriceResp = orderAmountResp.getAgentLevel3OrderAmount();
        if (orderPriceResp != null) {
            productPriceItem = orderPriceResp.getInternetAccessProductPriceItem();
            if (productPriceItem != null
                    && productPriceItem.getBroadBandPriceConfig() != null) {
                InternetAccessProductPriceConfig.BroadBandPriceConfig broadBandPriceConfig = productPriceItem.getBroadBandPriceConfig();
                orderAmount.setAgentLevel3OemItemPriceId(broadBandPriceConfig.getProductOemItemPriceId());
                orderAmount.setAgentLevel3Price(BigDecimal.valueOf(broadBandPriceConfig.getPrice()));
                orderAmount.setAgentLevel3PriceUnit(broadBandPriceConfig.getPriceUnit());
            }
            orderAmount.setAgentLevel3TotalPrice(orderPriceResp.getTotalPrice());
        }
        TOrderAmount oldInfo = tOrderAmountDAO.selectByOrderIdAndChangeId(orderAmount);
        if (oldInfo != null) {
            tOrderAmountDAO.deleteByPrimaryKey(oldInfo.getOrderAmountId());
        }
        return tOrderAmountDAO.insertSelective(orderAmount);
    }

    /**
     * 设置订单属性
     *
     * @param productDOrder
     * @param productDOrderParam
     * @param orderPriceResp
     * @param userInfoVO
     * @param newOrder
     */
    private void settingOrderProperty(TInternetAccessOrder productDOrder,
                                 InternetAccessOrderSaveParam productDOrderParam,
                                 InternetAccessOrderPriceResp orderPriceResp,
                                 UserInfoVO userInfoVO,
                                 boolean newOrder) {
        Date oldStartTime = productDOrder.getStartTime();

        BeanUtils.copyProperties(productDOrderParam, productDOrder);
        BeanUtils.copyProperties(orderPriceResp, productDOrder);
        productDOrder.setCreaterBy(userInfoVO.getAppUserInfoId());
        productDOrder.setCreateTime(new Date());
        productDOrder.setModifiedBy(userInfoVO.getAppUserInfoId());
        productDOrder.setModifiedTime(new Date());
        productDOrder.setIsDeleted(false);
        productDOrder.setOrgId(userInfoVO.getOrgId());
        productDOrder.setStatus(OrderStatusEnum.NOT_AUDIT.getValue());
        productDOrder.setOrgFullPath(userInfoVO.getOrgFullPath());
        productDOrder.setAuditTime(null);
        productDOrder.setAuditBy(null);
        productDOrder.setAuditPassRemark(null);
        productDOrder.setAuditRefusedRemark(null);
        productDOrder.setAuditPrice(orderPriceResp.getTotalPrice());
        productDOrder.setAuditPriceBy(userInfoVO.getAppUserInfoId());
        if (newOrder) {
            //订单新建时,以创建时间做为开始日期
            oldStartTime = productDOrder.getCreateTime();
            TProduct product = tProductDAO.selectByPrimaryKey(productDOrder.getProductId());
            productDOrder.setOrderNo(sequenceNumberRepository.getInternetAccessOrderNo());
            if (product != null) {
                productDOrder.setBusinessId(String.format("%s_%s", product.getProductCode(), productDOrder.getOrderNo()));
            }
        }
        if (productDOrder.getStartTime() == null) {
            productDOrder.setStartTime(oldStartTime);
        }
        LocalDate startDate = DateUtil.date2LocalDate(productDOrder.getStartTime());
        productDOrder.setStartTime(DateUtil.localDate2Date(startDate));
        productDOrder.setEndTime(DateUtil.localDate2Date(startDate.
                plusMonths(productDOrder.getContractPeriod())
                .plusDays(-1)));
    }

    /**
     * 得到订单详情
     *
     * @param productDOrder
     * @param productDOrderParam
     * @return
     */
    private  List<TInternetAccessOrderDetail> getOrderDetails (TInternetAccessOrder productDOrder,
                                                               InternetAccessOrderSaveParam productDOrderParam) {
        List<TInternetAccessOrderDetail> details = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(productDOrderParam.getApplicationLineIndicatorsIds())) {
            productDOrderParam.getApplicationLineIndicatorsIds().forEach(id -> {
                TInternetAccessOrderDetail dOrderDetail = new TInternetAccessOrderDetail();
                dOrderDetail.setOrderId(productDOrder.getOrderId());
                dOrderDetail.setApplicationLineIndicatorsId(id);
                details.add(dOrderDetail);
            });
        }
        return details;
    }

    private InternetAccessOrderBeforeChangeAndDetail saveInternetAccessOrderHistory(TInternetAccessOrder order,
                                                                                      List<TInternetAccessOrderDetail> details,
                                                                                      UserInfoVO userInfoVO) {
        InternetAccessOrderBeforeChangeAndDetail result = new InternetAccessOrderBeforeChangeAndDetail();
        TInternetAccessOrderBeforeChange productDOrderBeforeChange = new TInternetAccessOrderBeforeChange();
        TInternetAccessOrderBeforeChange lastTimeChangeInfo = tInternetAccessOrderBeforeChangeDAO.selectLastTimeChangeInfo(order.getOrderId(),null);
        //最后一次变更是待审核状态
        boolean lastTimeChangeInfoIsNotAudit = lastTimeChangeInfo !=null
                && OrderStatusEnum.NOT_AUDIT.getValue().equals(lastTimeChangeInfo.getStatus());
        if(lastTimeChangeInfoIsNotAudit) {
            BeanUtils.copyProperties(order, lastTimeChangeInfo);
            lastTimeChangeInfo.setModifiedBy(userInfoVO.getAppUserInfoId());
            lastTimeChangeInfo.setModifiedTime(new Date());
            tInternetAccessOrderBeforeChangeDAO.updateByPrimaryKeySelective(lastTimeChangeInfo);
            productDOrderBeforeChange = lastTimeChangeInfo;
        }
        else {
            BeanUtils.copyProperties(order, productDOrderBeforeChange);
            productDOrderBeforeChange.setCreateTime(new Date());
            productDOrderBeforeChange.setCreaterBy(userInfoVO.getAppUserInfoId());
            productDOrderBeforeChange.setModifiedBy(userInfoVO.getAppUserInfoId());
            productDOrderBeforeChange.setModifiedTime(new Date());
            productDOrderBeforeChange.setIsDeleted(false);
            tInternetAccessOrderBeforeChangeDAO.insertSelective(productDOrderBeforeChange);
        }
        result.setChange(productDOrderBeforeChange);
        if(lastTimeChangeInfoIsNotAudit){
            tInternetAccessOrderDetailBeforeChangeDAO.deleteByChangeId(productDOrderBeforeChange.getChangeId());
        }
        if (!CollectionUtils.isEmpty(details)) {
            List<TInternetAccessOrderDetailBeforeChange> list = Lists.newArrayList();
            TInternetAccessOrderBeforeChange finalProductDOrderBeforeChange = productDOrderBeforeChange;
            details.forEach(detail -> {
                TInternetAccessOrderDetailBeforeChange detailBeforeChange = new TInternetAccessOrderDetailBeforeChange();
                detailBeforeChange.setChangeId(finalProductDOrderBeforeChange.getChangeId());
                BeanUtils.copyProperties(detail, detailBeforeChange);
                list.add(detailBeforeChange);
            });
            tInternetAccessOrderDetailBeforeChangeDAO.insertBatch(list);
            result.setDetails(list);
        }
        return result;
    }

    /**
     * 得到 互联网接入-自定义-订单详情
     *
     * @param orderId 订单id
     * @return int
     */
    public InternetAccessOrderResp getOrderDetail(Integer orderId,Integer orgId) {
        InternetAccessOrderResp productDOrderDetailResp = new InternetAccessOrderResp();
        TInternetAccessOrder dOrder = tInternetAccessOrderDAO.selectByPrimaryKey(orderId);
        if (dOrder != null) {
            TProduct product = tProductDAO.selectByPrimaryKey(dOrder.getProductId());
            if (product != null) {
                dOrder.setProductCode(product.getProductCode());
                dOrder.setProductName(product.getProductName());
            }
            List<TInternetAccessOrderDetail> orderDetails = tInternetAccessOrderDetailDAO.selectByOrderId(ImmutableList.of(orderId));
            BeanUtils.copyProperties(dOrder, productDOrderDetailResp);
            productDOrderDetailResp.setStatusDesc(OrderStatusEnum.getDescByValue(productDOrderDetailResp.getStatus()));
            if (product != null) {
                TCategory category = tCategoryDAO.selectByPrimaryKey(product.getCategoryId());
                if (category != null) {
                    productDOrderDetailResp.setCategoryId(category.getCategoryId());
                    productDOrderDetailResp.setCategoryCode(category.getCategoryCode());
                    productDOrderDetailResp.setCategoryName(category.getCategoryName());
                    productDOrderDetailResp.setCategoryLevel(category.getCategoryLevel());
                }
            }
            productDOrderDetailResp.setOrderDetails(Lists.newArrayList());
            if (!CollectionUtils.isEmpty(orderDetails)) {
                orderDetails.forEach(tInternetAccessProductDOrderDetail -> {
                    InternetAccessOrderResp.OrderDetail orderDetail = new InternetAccessOrderResp.OrderDetail();
                    BeanUtils.copyProperties(tInternetAccessProductDOrderDetail, orderDetail);
                    productDOrderDetailResp.getOrderDetails().add(orderDetail);
                });
            }
            TProductOem productOem = tProductOemDAO.selectByProductAndOrgId(dOrder.getProductId(), orgId);
            if (productOem != null) {
                productDOrderDetailResp.setProductName(productOem.getProductOemName());
            }
        }
        return productDOrderDetailResp;
    }

    /**
     * 得到 互联网接入-自定义-订单列表
     *
     * @param pageParam 分页参数
     * @return PageInfo
     */
    public PageInfo<InternetAccessOrderResp> selectByQueryParam(PageParam<InternetAccessOrderQueryParam> pageParam) {
        String orderBy;
        if (StringUtils.isEmpty(pageParam.getSortName())) {
            orderBy = "create_time desc ";
        } else {
            orderBy = pageParam.getSortName() + (pageParam.getReverse() ? " desc" : " asc");
        }
        PageInfo<TInternetAccessOrder> pageInfo = PageHelper.offsetPage(pageParam.getOffset(), pageParam.getPageSize())
                .setOrderBy(orderBy)
                .doSelectPageInfo(
                        new ISelect() {
                            @Override
                            public void doSelect() {
                                tInternetAccessOrderDAO.selectByQueryParam(pageParam.getSearchParam());
                            }
                        }
                );
        PageInfo<InternetAccessOrderResp> result = PageInfoUtil.convert(pageInfo, InternetAccessOrderResp.class);
        if (!CollectionUtils.isEmpty(result.getList())) {
            result.getList().forEach(c ->{
                c.setStatusDesc(OrderStatusEnum.getDescByValue(c.getStatus()));
            });
        }
        return result;
    }

    /**
     * 得到订单历史信息
     *
     * @param orderId
     * @return
     */
    public OrderHistoryResp selectOrderHistory(Integer orderId,Integer orgId) {
        OrderHistoryResp orderHistoryResp = new OrderHistoryResp();
        TInternetAccessOrder order = tInternetAccessOrderDAO.selectByPrimaryKey(orderId);
        if (order != null) {
            orderHistoryResp.setProductId(order.getProductId());
            orderHistoryResp.setOrderNo(order.getOrderNo());
            TProduct product = tProductDAO.selectByPrimaryKey(order.getProductId());
            if (product != null) {
                orderHistoryResp.setProductCode(product.getProductCode());
                orderHistoryResp.setProductName(product.getProductName());
            }
            TProductOem productOem = tProductOemDAO.selectByProductAndOrgId(order.getProductId(), orgId);
            if (productOem != null) {
                orderHistoryResp.setProductName(productOem.getProductOemName());
            }
            List<TInternetAccessOrderBeforeChange> histories = tInternetAccessOrderBeforeChangeDAO.selectByOrderId(orderId);
            for (int i = histories.size() - 1; i >= 0; i--) {
                TInternetAccessOrderBeforeChange change = histories.get(i);
                TInternetAccessOrderBeforeChange lastVersion = null;
                if (i - 1 >= 0) {
                    lastVersion = histories.get(i - 1);
                }
                OrderHistoryResp.OrderHistoryDetailResp detailResp = new OrderHistoryResp.OrderHistoryDetailResp();
                detailResp.setVersion(change.getChangeId().toString());
                detailResp.setState(OrderStatusEnum.getDescByValue(change.getStatus()));
                detailResp.setCreaterBy(change.getCreaterBy());
                detailResp.setCreaterUserName(change.getCreaterUserName());
                detailResp.setCreateTime(change.getCreateTime());
                detailResp.setModifiedBy(change.getModifiedBy());
                detailResp.setModifiedUserName(change.getModifiedUserName());
                detailResp.setModifiedTime(change.getModifiedTime());
                detailResp.setStartTime(change.getStartTime());
                detailResp.setEndTime(change.getEndTime());
                detailResp.setApprovedTime(change.getAuditTime());
                detailResp.setAuditor(change.getAuditBy());
                detailResp.setAuditorUserName(change.getAuditUserName());
                detailResp.setDeliveryTime(change.getCreateTime());
                detailResp.setOrderDigest(getOrderDigest(change, lastVersion));
                orderHistoryResp.getDetails().add(detailResp);
            }
            //
//            OrderHistoryResp.OrderHistoryDetailResp detailResp = new OrderHistoryResp.OrderHistoryDetailResp();
//            detailResp.setVersion(String.valueOf(histories.size() + 1));
//            detailResp.setState(OrderStatusEnum.getDescByValue(order.getStatus()));
//            detailResp.setCreaterBy(order.getCreaterBy());
//            detailResp.setCreaterUserName(order.getCreaterUserName());
//            detailResp.setCreateTime(order.getModifiedTime());
//            detailResp.setModifiedBy(order.getModifiedBy());
//            detailResp.setModifiedUserName(order.getModifiedUserName());
//            detailResp.setModifiedTime(order.getModifiedTime());
//            detailResp.setStartTime(order.getStartTime());
//            detailResp.setEndTime(order.getEndTime());
//            detailResp.setApprovedTime(order.getAuditTime());
//            detailResp.setAuditor(order.getAuditBy());
//            detailResp.setAuditorUserName(order.getAuditUserName());
//            detailResp.setDeliveryTime(order.getCreateTime());
//            detailResp.setOrderDigest(getOrderDigest(order, histories.size() > 0 ? histories.get(histories.size() - 1) : null));
//            orderHistoryResp.getDetails().add(0, detailResp);
        }
        return orderHistoryResp;
    }

    private List<String> getOrderDigest(TInternetAccessOrderBeforeChange change,TInternetAccessOrderBeforeChange lastVersion) {
        List<String> orderDigest = Lists.newArrayList();
        if (lastVersion != null && !Objects.equals(lastVersion.getBroadBand(), change.getBroadBand())) {
            orderDigest.add(String.format("带宽(Mbps):%d >> %d", lastVersion.getBroadBand(), change.getBroadBand()));
        } else {
            orderDigest.add(String.format("带宽(Mbps):%d", change.getBroadBand()));
        }
        if (change.getExport() > 0) {
            if (lastVersion != null
                    && lastVersion.getExport() > 0
                    && !Objects.equals(lastVersion.getExport(), change.getExport())) {
                orderDigest.add(String.format("出口:%s >> %s",
                        ProductItemConstants.getExportDesc(lastVersion.getExport()),
                        ProductItemConstants.getExportDesc(change.getExport())));
            } else {
                orderDigest.add(String.format("出口:%s", ProductItemConstants.getExportDesc(change.getExport())));
            }
        }
        if (lastVersion != null && !Objects.equals(lastVersion.getContractPeriod(), change.getContractPeriod())) {
            orderDigest.add(String.format("合同期限(月):%d>> %d", lastVersion.getContractPeriod(), change.getContractPeriod()));
        } else {
            orderDigest.add(String.format("合同期限(月):%d", change.getContractPeriod()));
        }
        if (change.getPaymentCycle() > 0) {
            if (lastVersion != null
                    && lastVersion.getPaymentCycle() > 0
                    && !Objects.equals(lastVersion.getPaymentCycle(), change.getPaymentCycle())) {
                orderDigest.add(String.format("缴费周期:%s >> %s",
                        ProductItemConstants.getPaymentCycleDesc(lastVersion.getPaymentCycle()),
                        ProductItemConstants.getPaymentCycleDesc(change.getPaymentCycle())));
            } else {
                orderDigest.add(String.format("缴费周期:%s", ProductItemConstants.getPaymentCycleDesc(change.getPaymentCycle())));
            }
        }
        if (change.getPaymentMethod() > 0) {
            if (lastVersion != null
                    && lastVersion.getPaymentMethod() > 0
                    && !Objects.equals(lastVersion.getPaymentMethod(), change.getPaymentMethod())) {
                orderDigest.add(String.format("付费方式:%s >> %s",
                        ProductItemConstants.getPaymentMethodDesc(lastVersion.getPaymentMethod()),
                        ProductItemConstants.getPaymentMethodDesc(change.getPaymentMethod())));
            } else {
                orderDigest.add(String.format("付费方式:%s", ProductItemConstants.getPaymentMethodDesc(change.getPaymentMethod())));
            }
        }
        if (change.getIpNum() > 0) {
            if (lastVersion != null
                    && lastVersion.getIpNum() > 0
                    && !Objects.equals(lastVersion.getIpNum(), change.getIpNum())) {
                orderDigest.add(String.format("ip选择方式:%s >> %s",
                        ProductItemConstants.getIpNum(lastVersion.getIpNum()),
                        ProductItemConstants.getIpNum(change.getIpNum())));
            } else {
                orderDigest.add(String.format("ip选择方式:%s", ProductItemConstants.getIpNum(change.getIpNum())));
            }
        }
        if (change.getIpNumValue() > 0) {
            if (lastVersion != null
                    && lastVersion.getIpNumValue() > 0
                    && !Objects.equals(lastVersion.getIpNumValue(), change.getIpNumValue())) {
                orderDigest.add(String.format("IP数量:%d >> %d",
                        lastVersion.getIpNumValue(),
                        change.getIpNumValue()));
            } else {
                orderDigest.add(String.format("IP数量:%d", change.getIpNumValue()));
            }
        }
        if (change.getApplication() > 0) {
            if (lastVersion != null
                    && lastVersion.getApplication() > 0
                    && !Objects.equals(lastVersion.getApplication(), change.getApplication())) {
                orderDigest.add(String.format("ip选择方式:%s >> %s",
                        ProductItemConstants.getApplicationDesc(lastVersion.getApplication()),
                        ProductItemConstants.getApplicationDesc(change.getApplication())));
            } else {
                orderDigest.add(String.format("ip选择方式:%s", ProductItemConstants.getApplicationDesc(change.getApplication())));
            }
        }
        return orderDigest;
    }

    private List<String> getOrderDigest(TInternetAccessOrder change, TInternetAccessOrderBeforeChange lastVersion) {
        List<String> orderDigest = Lists.newArrayList();
        if (lastVersion != null && !Objects.equals(lastVersion.getBroadBand(), change.getBroadBand())) {
            orderDigest.add(String.format("带宽(Mbps):%d >> %d", lastVersion.getBroadBand(), change.getBroadBand()));
        } else {
            orderDigest.add(String.format("带宽(Mbps):%d", change.getBroadBand()));
        }
        if (change.getExport() > 0) {
            if (lastVersion != null
                    && lastVersion.getExport() > 0
                    && !Objects.equals(lastVersion.getExport(), change.getExport())) {
                orderDigest.add(String.format("出口:%s >> %s",
                        ProductItemConstants.getExportDesc(lastVersion.getExport()),
                        ProductItemConstants.getExportDesc(change.getExport())));
            } else {
                orderDigest.add(String.format("出口:%s", ProductItemConstants.getExportDesc(change.getExport())));
            }
        }
        if (lastVersion != null && !Objects.equals(lastVersion.getContractPeriod(), change.getContractPeriod())) {
            orderDigest.add(String.format("合同期限(月):%d>> %d", lastVersion.getContractPeriod(), change.getContractPeriod()));
        } else {
            orderDigest.add(String.format("合同期限(月):%d", change.getContractPeriod()));
        }
        if (change.getPaymentCycle() > 0) {
            if (lastVersion != null
                    && lastVersion.getPaymentCycle() > 0
                    && !Objects.equals(lastVersion.getPaymentCycle(), change.getPaymentCycle())) {
                orderDigest.add(String.format("缴费周期:%s >> %s",
                        ProductItemConstants.getPaymentCycleDesc(lastVersion.getPaymentCycle()),
                        ProductItemConstants.getPaymentCycleDesc(change.getPaymentCycle())));
            } else {
                orderDigest.add(String.format("缴费周期:%s", ProductItemConstants.getPaymentCycleDesc(change.getPaymentCycle())));
            }
        }
        if (change.getPaymentMethod() > 0) {
            if (lastVersion != null
                    && lastVersion.getPaymentMethod() > 0
                    && !Objects.equals(lastVersion.getPaymentMethod(), change.getPaymentMethod())) {
                orderDigest.add(String.format("付费方式:%s >> %s",
                        ProductItemConstants.getPaymentMethodDesc(lastVersion.getPaymentMethod()),
                        ProductItemConstants.getPaymentMethodDesc(change.getPaymentMethod())));
            } else {
                orderDigest.add(String.format("付费方式:%s", ProductItemConstants.getPaymentMethodDesc(change.getPaymentMethod())));
            }
        }
        if (change.getIpNum() > 0) {
            if (lastVersion != null
                    && lastVersion.getIpNum() > 0
                    && !Objects.equals(lastVersion.getIpNum(), change.getIpNum())) {
                orderDigest.add(String.format("ip选择方式:%s >> %s",
                        ProductItemConstants.getIpNum(lastVersion.getIpNum()),
                        ProductItemConstants.getIpNum(change.getIpNum())));
            } else {
                orderDigest.add(String.format("ip选择方式:%s", ProductItemConstants.getIpNum(change.getIpNum())));
            }
        }
        if (change.getIpNumValue() > 0) {
            if (lastVersion != null
                    && lastVersion.getIpNumValue() > 0
                    && !Objects.equals(lastVersion.getIpNumValue(), change.getIpNumValue())) {
                orderDigest.add(String.format("IP数量:%d >> %d",
                        lastVersion.getIpNumValue(),
                        change.getIpNumValue()));
            } else {
                orderDigest.add(String.format("IP数量:%d", change.getIpNumValue()));
            }
        }
        if (change.getApplication() > 0) {
            if (lastVersion != null
                    && lastVersion.getApplication() > 0
                    && !Objects.equals(lastVersion.getApplication(), change.getApplication())) {
                orderDigest.add(String.format("ip选择方式:%s >> %s",
                        ProductItemConstants.getApplicationDesc(lastVersion.getApplication()),
                        ProductItemConstants.getApplicationDesc(change.getApplication())));
            } else {
                orderDigest.add(String.format("ip选择方式:%s", ProductItemConstants.getApplicationDesc(change.getApplication())));
            }
        }
        return orderDigest;
    }

    /**
     * 得到订单详情
     *
     * @param changeId 订单变更序号id
     * @param orgId 组织id
     * @return
     */
    public Map<String,InternetAccessOrderResp> getOrderHistoryDetail(Integer changeId, Integer orderId, Integer orgId) {
        Map<String, InternetAccessOrderResp> respMap = Maps.newHashMap();
        respMap.put("source", getOrderDetail(orderId, orgId));
        if (changeId > 0) {
            TInternetAccessOrderBeforeChange orderBeforeChange = tInternetAccessOrderBeforeChangeDAO.selectByPrimaryKey(changeId);
            respMap.put("change", getBeforeChangeOrderDetail(orderBeforeChange, orgId));
        }
        return respMap;
    }

    /**
     * 从订单变更表得到订单详情
     *
     * @param orderId 订单id
     * @param orgId 组织id
     * @return
     */
    public InternetAccessOrderResp getBeforeChangeOrderDetail(Integer orderId, Integer orgId){
        TInternetAccessOrderBeforeChange lastTimeChangeInfo = tInternetAccessOrderBeforeChangeDAO.selectLastTimeChangeInfo(orderId,null);
        return getBeforeChangeOrderDetail(lastTimeChangeInfo,orgId);
    }

    private InternetAccessOrderResp getBeforeChangeOrderDetail(TInternetAccessOrderBeforeChange orderBeforeChange, Integer orgId) {
        InternetAccessOrderResp productDOrderDetailResp = new InternetAccessOrderResp();
        if (orderBeforeChange != null) {
            TProduct product = tProductDAO.selectByPrimaryKey(orderBeforeChange.getProductId());
            if (product != null) {
                productDOrderDetailResp.setProductCode(product.getProductCode());
                productDOrderDetailResp.setProductName(product.getProductName());
            }
            List<TInternetAccessOrderDetailBeforeChange> orderDetails = tInternetAccessOrderDetailBeforeChangeDAO.selectByChangeId(orderBeforeChange.getChangeId());
            BeanUtils.copyProperties(orderBeforeChange, productDOrderDetailResp);
            if (product != null) {
                TCategory category = tCategoryDAO.selectByPrimaryKey(product.getCategoryId());
                if (category != null) {
                    productDOrderDetailResp.setCategoryId(category.getCategoryId());
                    productDOrderDetailResp.setCategoryCode(category.getCategoryCode());
                    productDOrderDetailResp.setCategoryName(category.getCategoryName());
                    productDOrderDetailResp.setCategoryLevel(category.getCategoryLevel());
                }
            }
            productDOrderDetailResp.setStatusDesc(OrderStatusEnum.getDescByValue(productDOrderDetailResp.getStatus()));
            productDOrderDetailResp.setOrderDetails(Lists.newArrayList());
            if (!CollectionUtils.isEmpty(orderDetails)) {
                orderDetails.forEach(tInternetAccessProductDOrderDetail -> {
                    InternetAccessOrderResp.OrderDetail orderDetail = new InternetAccessOrderResp.OrderDetail();
                    BeanUtils.copyProperties(tInternetAccessProductDOrderDetail, orderDetail);
                    productDOrderDetailResp.getOrderDetails().add(orderDetail);
                });
            }
            TProductOem productOem = tProductOemDAO.selectByProductAndOrgId(orderBeforeChange.getProductId(), orgId);
            if (productOem != null) {
                productDOrderDetailResp.setProductName(productOem.getProductOemName());
            }

        }
        return productDOrderDetailResp;
    }

    /**
     * 订单审核
     *
     * @param orderAuditParam
     * @param userInfoVO
     * @return
     */
    @RedisLock(key = "'orgId:'+ #userInfoVO.orgId+':orderId:'+ #orderAuditParam.orderId+':OrderAudit'")
    @Transactional(value = "mysqlTransactionManager",rollbackFor = Exception.class)
    public InternetAccessOrderAuditResult orderAudit(OrderAuditParam orderAuditParam, UserInfoVO userInfoVO) {
        InternetAccessOrderAuditResult auditResult = new InternetAccessOrderAuditResult();
        auditResult.setTransId(0);
        auditResult.setOrderId(orderAuditParam.getOrderId());

        Date now = new Date();
        InternetAccessOrderBeforeChangeQueryParam queryParam = new InternetAccessOrderBeforeChangeQueryParam();
        queryParam.setOrderId(orderAuditParam.getOrderId());

        TInternetAccessOrderBeforeChange lastTimeAuditPassInfo= tInternetAccessOrderBeforeChangeDAO.selectLastTimeChangeInfo(orderAuditParam.getOrderId(),OrderStatusEnum.AUDIT_PASS.getValue());
        //订单日志
        TInternetAccessOrderBeforeChange lastTimeChangeInfo = tInternetAccessOrderBeforeChangeDAO.selectLastTimeChangeInfo(orderAuditParam.getOrderId(),null);
        if(lastTimeChangeInfo == null){
            throw new BusinessException("订单ID不存在,请检查!");
        }
        if (( orderAuditParam.getAuditPassRemark() != null || orderAuditParam.getAuditRefusedRemark() != null )
                && !OrderStatusEnum.NOT_AUDIT.getValue().equals(lastTimeChangeInfo.getStatus())) {
            throw new BusinessException("该订单已审批完成,无须重复审批!");
        }
        lastTimeChangeInfo.setModifiedBy(userInfoVO.getAppUserInfoId());
        lastTimeChangeInfo.setModifiedTime(now);
        //订单
        TInternetAccessOrder order = new TInternetAccessOrder();
        order.setOrderId(orderAuditParam.getOrderId());
        order.setModifiedBy(userInfoVO.getAppUserInfoId());
        order.setModifiedTime(now);
        //从订单日志覆盖至订单表
        boolean coverFromHistoryToOrder = false;
        boolean callDeviceControlApi = false;
        //审核通过
        if (orderAuditParam.getAuditPassRemark() != null) {
            //订单创建时,改价用户id默认等于创建订单用户id
            //订单审核通过时,需更新改价用户id为审批用户id
            if(Objects.equals(lastTimeChangeInfo.getAuditPriceBy(),lastTimeChangeInfo.getCreaterBy())) {
                lastTimeChangeInfo.setAuditPriceBy(userInfoVO.getAppUserInfoId());
                order.setAuditPriceBy(userInfoVO.getAppUserInfoId());
            }
            lastTimeChangeInfo.setAuditTime(now);
            lastTimeChangeInfo.setAuditBy(userInfoVO.getAppUserInfoId());
            lastTimeChangeInfo.setStatus(OrderStatusEnum.AUDIT_PASS.getValue());
            lastTimeChangeInfo.setAuditPassRemark(orderAuditParam.getAuditPassRemark());
            lastTimeChangeInfo.setDeliveryTime(now);

            order.setAuditTime(now);
            order.setAuditBy(userInfoVO.getAppUserInfoId());
            order.setStatus(OrderStatusEnum.AUDIT_PASS.getValue());
            order.setAuditPassRemark(orderAuditParam.getAuditPassRemark());
            order.setDeliveryTime(now);
            coverFromHistoryToOrder = true;
            callDeviceControlApi = true;
        }
        //审核拒绝
        if (orderAuditParam.getAuditRefusedRemark() != null) {
            lastTimeChangeInfo.setAuditTime(now);
            lastTimeChangeInfo.setAuditBy(userInfoVO.getAppUserInfoId());
            lastTimeChangeInfo.setStatus(OrderStatusEnum.AUDIT_REFUSED.getValue());
            lastTimeChangeInfo.setAuditRefusedRemark(orderAuditParam.getAuditRefusedRemark());

            order.setAuditTime(now);
            order.setAuditBy(userInfoVO.getAppUserInfoId());
            order.setStatus(OrderStatusEnum.AUDIT_REFUSED.getValue());
            order.setAuditRefusedRemark(orderAuditParam.getAuditRefusedRemark());
        }
        //改价
        if (orderAuditParam.getAuditPrice() != null
                && orderAuditParam.getAuditPrice().compareTo(BigDecimal.ZERO) >= 0) {
            lastTimeChangeInfo.setAuditPrice(orderAuditParam.getAuditPrice());
            lastTimeChangeInfo.setAuditPriceBy(userInfoVO.getAppUserInfoId());

            order.setAuditPrice(orderAuditParam.getAuditPrice());
            order.setAuditPriceBy(userInfoVO.getAppUserInfoId());
        }
        tInternetAccessOrderBeforeChangeDAO.updateByPrimaryKeySelective(lastTimeChangeInfo);
        if (callDeviceControlApi) {
            TInternetAccessOrder oldInfo = tInternetAccessOrderDAO.selectByPrimaryKey(order.getOrderId());
            auditResult = getOrderAuditResult(oldInfo, lastTimeChangeInfo, userInfoVO);
        }
        if (coverFromHistoryToOrder) {
            TInternetAccessOrderBeforeChange newInfo = tInternetAccessOrderBeforeChangeDAO.selectByPrimaryKey(lastTimeChangeInfo.getChangeId());
            BeanUtils.copyProperties(newInfo, order);
            tInternetAccessOrderDAO.updateByPrimaryKeySelective(order);

            List<TInternetAccessOrderDetailBeforeChange> newDetails = tInternetAccessOrderDetailBeforeChangeDAO.selectByChangeId(lastTimeChangeInfo.getChangeId());
            if (CollectionUtils.isNotEmpty(newDetails)) {
                List<TInternetAccessOrderDetail> orderDetails = Lists.newArrayList();
                newDetails.forEach(changeDetail -> {
                    TInternetAccessOrderDetail detail = new TInternetAccessOrderDetail();
                    detail.setApplicationLineIndicatorsId(changeDetail.getApplicationLineIndicatorsId());
                    detail.setOrderId(order.getOrderId());
                    orderDetails.add(detail);
                });
                tInternetAccessOrderDetailDAO.deleteByOrderId(order.getOrderId());
                tInternetAccessOrderDetailDAO.insertBatch(orderDetails);
            }
            if (newInfo.getAuditPrice().compareTo(newInfo.getTotalPrice()) != 0) {
                TOrganizational organizational = authorizationManager.selectByOrgId(newInfo.getOrgId());
                TOrderAmount orderAmountQuery = new TOrderAmount();
                orderAmountQuery.setOrderId(newInfo.getOrderId());
                orderAmountQuery.setChangeId(newInfo.getChangeId());
                orderAmountQuery.setOrderTableName(OrderType.INTERNET_ACCESS_ORDER.getValue());
                TOrderAmount orderAmount = tOrderAmountDAO.selectByOrderIdAndChangeId(orderAmountQuery);
                TOrderAmount orderAmountUpdate = new TOrderAmount();
                orderAmountUpdate.setOrderAmountId(orderAmount.getOrderAmountId());
                orderAmountUpdate.setTotalPrice(lastTimeChangeInfo.getAuditPrice());
                if (organizational.getCustomerCompanyOrgId() > 0) {
                    orderAmountUpdate.setSalesTotalPrice(lastTimeChangeInfo.getAuditPrice());
                } else if (organizational.getAgentLevel3OrgId() > 0) {
                    orderAmountUpdate.setAgentLevel3TotalPrice(lastTimeChangeInfo.getAuditPrice());
                } else if (organizational.getAgentLevel2OrgId() > 0) {
                    orderAmountUpdate.setAgentLevel2TotalPrice(lastTimeChangeInfo.getAuditPrice());
                } else if (organizational.getAgentLevel1OrgId() > 0) {
                    orderAmountUpdate.setAgentLevel1TotalPrice(lastTimeChangeInfo.getAuditPrice());
                } else {
                    orderAmountUpdate.setProviderTotalPrice(lastTimeChangeInfo.getAuditPrice());
                }
                tOrderAmountDAO.updateByPrimaryKeySelective(orderAmountUpdate);
            }
            statementBillManager.SaveInternetAccessOrderBill(lastTimeAuditPassInfo, newInfo);
        }
        return auditResult;
    }

    private InternetAccessOrderAuditResult getOrderAuditResult(TInternetAccessOrder oldInfo, TInternetAccessOrderBeforeChange newInfo, UserInfoVO userInfoVO) {
        boolean isNewOrder = (oldInfo == null);
        List<Integer> middleStatus = Lists.newLinkedList();
        middleStatus.add(DeviceControlTransCode.INITIAL.getCode());
        if (isNewOrder || !Objects.equals(oldInfo.getBroadBand(), newInfo.getBroadBand())) {
            middleStatus.add(DeviceControlTransCode.BROAD_BAND_UPDATE.getCode());
        }
        if (isNewOrder
                || (newInfo.getExport() != null && !Objects.equals(oldInfo.getExport(), newInfo.getExport()))) {
            middleStatus.add(DeviceControlTransCode.BROAD_BAND_ISP_CHANGE.getCode());
        }
        TProduct product = tProductDAO.selectByPrimaryKey(newInfo.getProductId());

        InternetAccessOrderAuditResult result = new InternetAccessOrderAuditResult();
        result.setServiceType(DeviceControlServiceType.BROAD_BAND.getCode());
        result.setOrderId(newInfo.getOrderId());
        result.setOrderNo(newInfo.getOrderNo());
        if (product != null) {
            result.setProductCode(product.getProductCode());
        }
        result.setChangeId(newInfo.getChangeId());
        result.setTransId(0);
        result.setMiddleStatus(middleStatus);
        result.setTransCurrent(DeviceControlTransCode.INITIAL.getCode());
        result.setBroadBand(newInfo.getBroadBand());
        result.setExport(newInfo.getExport());
        if (middleStatus.size() > 1) {
            result.setTransId(deviceControlTransManager.insertSelective(result, userInfoVO.getAppUserInfoId()));
        }
        return result;
    }

    public DeviceControlResponseResult<TrendStackResponse> trendStacking(OrderMonitorQueryParam queryParam){
        TrendStackRequest trendStackRequest = new TrendStackRequest();
        TInternetAccessOrder tInternetAccessOrder= tInternetAccessOrderDAO.selectByPrimaryKey(queryParam.getOrderId());
        if(tInternetAccessOrder == null) {
            throw new BusinessException("订单ID不存在,请检查!");
        }
        trendStackRequest.setBusiness_id(tInternetAccessOrder.getBusinessId());
        if (StringUtils.isNotBlank(queryParam.getStart())) {
            trendStackRequest.setTmstart(queryParam.getStart());
        } else {
            trendStackRequest.setTmstart(LocalDateTime.of(LocalDate.now(), LocalTime.MIN).format(SnowConstants.DATE_HOUR_MiN_SEC_WITH_HYPHEN_FORMATTER));
        }
        if (StringUtils.isNotBlank(queryParam.getEnd())) {
            trendStackRequest.setTmend(queryParam.getEnd());
        } else {
            trendStackRequest.setTmend(LocalDateTime.of(LocalDate.now(), LocalTime.MAX).format(SnowConstants.DATE_HOUR_MiN_SEC_WITH_HYPHEN_FORMATTER));
        }
        return statsRepository.trendStacking(trendStackRequest);
    }
}
