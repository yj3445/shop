package com.itshop.web.manager;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.itshop.web.bo.IdcFireWallOrderSaveResult;
import com.itshop.web.dao.SequenceNumberRepository;
import com.itshop.web.dao.mysql.*;
import com.itshop.web.dao.po.*;
import com.itshop.web.dto.PageParam;
import com.itshop.web.dto.request.IdcFireWallOrderSaveParam;
import com.itshop.web.dto.request.TIdcFirewallOrderQueryParam;
import com.itshop.web.dto.response.IdcFireWallOrderResp;
import com.itshop.web.enums.DeviceControlFinalTransCode;
import com.itshop.web.enums.DeviceControlServiceType;
import com.itshop.web.enums.DeviceControlTransCode;
import com.itshop.web.enums.NetworkInOrOut;
import com.itshop.web.util.ListUtil;
import com.itshop.web.util.PageInfoUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * idc-防火墙订单
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Service
public class IdcFireWallOrderManager {

    @Autowired
    TIdcFirewallOrderDAO tIdcFirewallOrderDAO;

    @Autowired
    TIdcFirewallOrderDetailDAO tIdcFirewallOrderDetailDAO;

    @Autowired
    TIdcFirewallOrderBeforeChangeDAO tIdcFirewallOrderBeforeChangeDAO;

    @Autowired
    TIdcFirewallOrderDetailBeforeChangeDAO tIdcFirewallOrderDetailBeforeChangeDAO;

    @Autowired
    TDeviceControlTransDAO tDeviceControlTransDAO;

    @Autowired
    DeviceControlTransManager deviceControlTransManager;

    @Autowired
    SequenceNumberRepository sequenceNumberRepository;

    @Autowired
    TProductDAO tProductDAO;

    @Autowired
    TCategoryDAO tCategoryDAO;

    /**
     * 保存idc-防火墙订单订单
     *
     * @param firewallOrderSaveParam idc-防火墙订单保存参数
     * @return int
     */
    @Transactional(value = "mysqlTransactionManager",rollbackFor = Exception.class)
    public IdcFireWallOrderSaveResult save(IdcFireWallOrderSaveParam firewallOrderSaveParam) {
        Integer changeId = 0;
        Integer orderId = 0;
        TIdcFirewallOrder firewallOrder = null;
        boolean isNewAdd = false;
        Boolean oldFireWallOpen = true;
        List<IdcFireWallOrderSaveResult.InOrOutProtocolPortInfo> oldEnableInPorts = Lists.newArrayList();
        List<IdcFireWallOrderSaveResult.InOrOutProtocolPortInfo> oldDisableOutPorts = Lists.newArrayList();
        if (firewallOrderSaveParam.getOrderId() != null && firewallOrderSaveParam.getOrderId() > 0) {
            firewallOrder = tIdcFirewallOrderDAO.selectByPrimaryKey(firewallOrderSaveParam.getOrderId());
            if (firewallOrder != null) {
                //保存更新前记录
                TIdcFirewallOrderBeforeChange firewallOrderBeforeChange = new TIdcFirewallOrderBeforeChange();
                BeanUtils.copyProperties(firewallOrder, firewallOrderBeforeChange);
                firewallOrderBeforeChange.setCreateTime(new Date());
                firewallOrderBeforeChange.setCreaterBy(firewallOrderSaveParam.getCreaterBy());
                firewallOrderBeforeChange.setModifiedBy(firewallOrderSaveParam.getCreaterBy());
                firewallOrderBeforeChange.setModifiedTime(new Date());
                firewallOrderBeforeChange.setIsDeleted(false);
                tIdcFirewallOrderBeforeChangeDAO.insertSelective(firewallOrderBeforeChange);
                changeId = firewallOrderBeforeChange.getChangeId();
                List<TIdcFirewallOrderDetail> orderDetails = tIdcFirewallOrderDetailDAO.selectByOrderId(ImmutableList.of(firewallOrder.getOrderId()));
                if (!CollectionUtils.isEmpty(orderDetails)) {
                    Map<String,List<TIdcFirewallOrderDetail>> stringListMap= orderDetails.stream()
                            .collect(Collectors.groupingBy(p -> p.getInOrOut().toString()+"-"+p.getProtocol()));
                    stringListMap.forEach((key,value) ->{
                        String [] arr = key.split("-");
                        IdcFireWallOrderSaveResult.InOrOutProtocolPortInfo portInfo = new IdcFireWallOrderSaveResult.InOrOutProtocolPortInfo();
                        portInfo.setInOrOut(Integer.parseInt(arr[0]));
                        portInfo.setProtocol(arr[1]);
                        portInfo.setPort(value.stream().map(TIdcFirewallOrderDetail::getPort).collect(Collectors.toList()));
                        if(NetworkInOrOut.OUT.getCode().toString().equalsIgnoreCase(arr[0])) {
                            oldDisableOutPorts.add(portInfo);
                        }
                        else {
                            oldEnableInPorts.add(portInfo);
                        }
                    });
                    List<TIdcFirewallOrderDetailBeforeChange> list = Lists.newArrayList();
                    orderDetails.forEach(tIdcFirewallOrderDetail -> {
                        TIdcFirewallOrderDetailBeforeChange detailBeforeChange = new TIdcFirewallOrderDetailBeforeChange();
                        detailBeforeChange.setChangeId(firewallOrderBeforeChange.getChangeId());
                        BeanUtils.copyProperties(tIdcFirewallOrderDetail, detailBeforeChange);
                        list.add(detailBeforeChange);
                    });
                    tIdcFirewallOrderDetailBeforeChangeDAO.insertBatch(list);
                }
            }
        }
        if (firewallOrder == null) {
            isNewAdd = true;
            firewallOrder = new TIdcFirewallOrder();
            BeanUtils.copyProperties(firewallOrderSaveParam, firewallOrder);
            firewallOrder.setOrderNo(sequenceNumberRepository.getIdcFireWallOrderNo());
            firewallOrder.setCreateTime(new Date());
            firewallOrder.setModifiedBy(firewallOrder.getCreaterBy());
            firewallOrder.setModifiedTime(new Date());
            firewallOrder.setIsDeleted(false);
            tIdcFirewallOrderDAO.insertSelective(firewallOrder);
        } else {
            oldFireWallOpen = firewallOrder.getOpen();
            BeanUtils.copyProperties(firewallOrderSaveParam, firewallOrder);
            firewallOrder.setCreaterBy(null);
            firewallOrder.setCreateTime(null);
            firewallOrder.setModifiedBy(firewallOrder.getCreaterBy());
            firewallOrder.setModifiedTime(new Date());
            firewallOrder.setIsDeleted(false);
            tIdcFirewallOrderDAO.updateByPrimaryKeySelective(firewallOrder);
            tIdcFirewallOrderDetailDAO.deleteByOrderId(firewallOrder.getOrderId());
        }
        orderId = firewallOrder.getOrderId();
        List<IdcFireWallOrderSaveResult.InOrOutProtocolPortInfo> newEnableInPorts = Lists.newArrayList();
        List<IdcFireWallOrderSaveResult.InOrOutProtocolPortInfo> newDisableOutPorts = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(firewallOrderSaveParam.getPortInfos())) {
            Map<String,List<IdcFireWallOrderSaveParam.PortInfo>> stringListMap= firewallOrderSaveParam.getPortInfos()
                    .stream()
                    .collect(Collectors.groupingBy(p -> p.getInOrOut().toString()+"-"+p.getProtocol()));
            stringListMap.forEach((key,value) ->{
                String [] arr = key.split("-");
                IdcFireWallOrderSaveResult.InOrOutProtocolPortInfo portInfo = new IdcFireWallOrderSaveResult.InOrOutProtocolPortInfo();
                portInfo.setInOrOut(Integer.parseInt(arr[0]));
                portInfo.setProtocol(arr[1]);
                portInfo.setPort(value.stream().map(IdcFireWallOrderSaveParam.PortInfo::getPort).collect(Collectors.toList()));
                if(NetworkInOrOut.OUT.getCode().toString().equalsIgnoreCase(arr[0])) {
                    newDisableOutPorts.add(portInfo);
                }
                else {
                    newEnableInPorts.add(portInfo);
                }
            });
            List<TIdcFirewallOrderDetail> list = Lists.newArrayList();
            Integer finalOrderId = orderId;
            firewallOrderSaveParam.getPortInfos().forEach(portInfo -> {
                TIdcFirewallOrderDetail orderDetail = new TIdcFirewallOrderDetail();
                orderDetail.setInOrOut(portInfo.getInOrOut());
                orderDetail.setProtocol(portInfo.getProtocol());
                orderDetail.setPort(portInfo.getPort());
                orderDetail.setOrderId(finalOrderId);
                list.add(orderDetail);
            });
            tIdcFirewallOrderDetailDAO.insertBatch(list);
        }
        //中间状态
        List<Integer> middleStatus = Lists.newLinkedList();
        middleStatus.add(DeviceControlTransCode.INITIAL.getCode());
        //新增
        if (isNewAdd) {
            middleStatus.add(BooleanUtils.isTrue(firewallOrderSaveParam.getOpen())
                    ? DeviceControlTransCode.FIRE_WALL_ENABLE.getCode()
                    : DeviceControlTransCode.FIRE_WALL_DISABLE.getCode());
            if (!CollectionUtils.isEmpty(newEnableInPorts)) {
                middleStatus.add(DeviceControlTransCode.FIRE_WALL_ENABLE_IN_PORT.getCode());
            }
            if (!CollectionUtils.isEmpty(newDisableOutPorts)) {
                middleStatus.add(DeviceControlTransCode.FIRE_WALL_DISABLE_OUT_PORT.getCode());
            }
        } else {
            if (firewallOrderSaveParam.getOpen().compareTo(oldFireWallOpen) != 0) {
                if (BooleanUtils.isTrue(firewallOrderSaveParam.getOpen())) {
                    middleStatus.add(DeviceControlTransCode.FIRE_WALL_ENABLE.getCode());
                } else {
                    middleStatus.add(DeviceControlTransCode.FIRE_WALL_DISABLE.getCode());
                }
            }
            if (!ListUtil.equals(oldEnableInPorts, newEnableInPorts)) {
                if (!CollectionUtils.isEmpty(oldEnableInPorts)) {
                    //需要删除之前的
                }
                if (!CollectionUtils.isEmpty(newEnableInPorts)) {
                    middleStatus.add(DeviceControlTransCode.FIRE_WALL_ENABLE_IN_PORT.getCode());
                }
            }
            if (!ListUtil.equals(oldDisableOutPorts, newDisableOutPorts)) {
                if (!CollectionUtils.isEmpty(oldDisableOutPorts)) {
                    //需要删除之前的
                }
                if (!CollectionUtils.isEmpty(newDisableOutPorts)) {
                    middleStatus.add(DeviceControlTransCode.FIRE_WALL_DISABLE_OUT_PORT.getCode());
                }
            }
        }
        TProduct product= tProductDAO.selectByPrimaryKey(firewallOrder.getProductId());
        IdcFireWallOrderSaveResult result = new IdcFireWallOrderSaveResult();
        result.setServiceType(DeviceControlServiceType.FIRE_WALL.getCode());
        result.setOrderId(orderId);
        result.setOrderNo(firewallOrder.getOrderNo());
        if(product !=null) {
            result.setProductCode(product.getProductCode());
        }
        result.setChangeId(changeId);
        result.setMiddleStatus(middleStatus);
        result.setTransCurrent(DeviceControlTransCode.INITIAL.getCode());
        result.setOldOpen(oldFireWallOpen);
        result.setOldEnableInPorts(oldEnableInPorts);
        result.setOldDisableOutPorts(oldDisableOutPorts);
        result.setNewOpen(firewallOrderSaveParam.getOpen());
        result.setNewEnableInPorts(newEnableInPorts);
        result.setNewDisableOutPorts(newDisableOutPorts);
        result.setTransId(0);
        if (middleStatus.size() > 1) {
            result.setTransId(deviceControlTransManager.insertSelective(result, firewallOrderSaveParam.getCreaterBy()));
        }
        return result;
    }

    /**
     * 得到 idc-防火墙订单-订单详情
     *
     * @param orderId 订单id
     * @return int
     */
    public IdcFireWallOrderResp getOrderDetail(Integer orderId) {
        IdcFireWallOrderResp orderResp = new IdcFireWallOrderResp();
        TIdcFirewallOrder order = tIdcFirewallOrderDAO.selectByPrimaryKey(orderId);
        if (order != null) {
            TProduct product= tProductDAO.selectByPrimaryKey(order.getProductId());
            if(product !=null){
                order.setProductCode(product.getProductCode());
                order.setProductName(product.getProductName());
            }
            BeanUtils.copyProperties(order, orderResp);
            if(product !=null){
                TCategory category= tCategoryDAO.selectByPrimaryKey(product.getCategoryId());
                if(category !=null){
                    orderResp.setCategoryId(category.getCategoryId());
                    orderResp.setCategoryCode(category.getCategoryCode());
                    orderResp.setCategoryName(category.getCategoryName());
                    orderResp.setCategoryLevel(category.getCategoryLevel());
                }
            }
            List<TIdcFirewallOrderDetail> orderDetails = tIdcFirewallOrderDetailDAO.selectByOrderId(ImmutableList.of(orderId));
            orderResp.setPortInfos(Lists.newArrayList());
            if (!CollectionUtils.isEmpty(orderDetails)) {
                orderDetails.forEach(p ->{
                    IdcFireWallOrderSaveParam.PortInfo portInfo = new IdcFireWallOrderSaveParam.PortInfo();
                    portInfo.setPort(p.getPort());
                    portInfo.setProtocol(p.getProtocol());
                    portInfo.setInOrOut(p.getInOrOut());
                    orderResp.getPortInfos().add(portInfo);
                });
            }
        }
        return orderResp;
    }

    /**
     * 得到 idc-防火墙订单-订单列表
     *
     * @param pageParam 分页参数
     * @return PageInfo
     */
    public PageInfo<IdcFireWallOrderResp> selectByQueryParam(PageParam<TIdcFirewallOrderQueryParam> pageParam) {
        String orderBy;
        if (StringUtils.isEmpty(pageParam.getSortName())) {
            orderBy = "create_time desc ";
        } else {
            orderBy = pageParam.getSortName() + (pageParam.getReverse() ? " desc" : " asc");
        }
        PageInfo<TIdcFirewallOrder> pageInfo = PageHelper.offsetPage(pageParam.getOffset(), pageParam.getPageSize())
                .setOrderBy(orderBy)
                .doSelectPageInfo(
                        new ISelect() {
                            @Override
                            public void doSelect() {
                                tIdcFirewallOrderDAO.selectByQueryParam(pageParam.getSearchParam());
                            }
                        }
                );
        return PageInfoUtil.convert(pageInfo, IdcFireWallOrderResp.class);
    }
}


