package com.itshop.web.manager;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.itshop.web.dao.SequenceNumberRepository;
import com.itshop.web.dao.mysql.TCategoryDAO;
import com.itshop.web.dao.mysql.TInternetAccessUpdateServiceOrderDAO;
import com.itshop.web.dao.mysql.TOrderChangeLogDAO;
import com.itshop.web.dao.mysql.TProductDAO;
import com.itshop.web.dao.po.*;
import com.itshop.web.dto.PageParam;
import com.itshop.web.dto.request.InternetAccessUpdateServiceOrderQueryParam;
import com.itshop.web.dto.request.InternetAccessUpdateServiceOrderSaveParam;
import com.itshop.web.dto.response.InternetAccessUpdateServiceOrderPriceResp;
import com.itshop.web.dto.response.InternetAccessUpdateServiceOrderResp;
import com.itshop.web.enums.ChangeType;
import com.itshop.web.enums.OrderType;
import com.itshop.web.util.CompareObjectPropertyUtil;
import com.itshop.web.util.ModifiedPropertyInfo;
import com.itshop.web.util.PageInfoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * 互联网接入-升级服务-订单管理
 */
@Service
public class InternetAccessUpdateServiceOrderManager {

    @Autowired
    TOrderChangeLogDAO tOrderChangeLogDAO;

    @Autowired
    TInternetAccessUpdateServiceOrderDAO tInternetAccessUpdateServiceOrderDAO;

    @Autowired
    SequenceNumberRepository sequenceNumberRepository;

    @Autowired
    TProductDAO tProductDAO;

    @Autowired
    TCategoryDAO tCategoryDAO;

    /**
     * 保存互联网接入-升级服务-订单
     *
     * @param orderSaveParam 订单保存参数
     * @param orderPriceResp 订单价格
     * @return int
     */
    @Transactional(value = "mysqlTransactionManager",rollbackFor = Exception.class)
    public Integer save(InternetAccessUpdateServiceOrderSaveParam orderSaveParam, InternetAccessUpdateServiceOrderPriceResp orderPriceResp) {
        TInternetAccessUpdateServiceOrder oldOrder = null;
        if (orderSaveParam.getOrderId() != null && orderSaveParam.getOrderId() > 0) {
            oldOrder = tInternetAccessUpdateServiceOrderDAO.selectByPrimaryKey(orderSaveParam.getOrderId());
        }
        TInternetAccessUpdateServiceOrder newOrder = new TInternetAccessUpdateServiceOrder();
        BeanUtils.copyProperties(orderSaveParam, newOrder);
        BeanUtils.copyProperties(orderPriceResp, newOrder);
        newOrder.setModifiedBy(newOrder.getCreaterBy());
        newOrder.setModifiedTime(new Date());
        newOrder.setIsDeleted(false);
        if (oldOrder == null) {
            newOrder.setOrderNo(sequenceNumberRepository.getInternetAccessOrderNo());
            newOrder.setCreateTime(new Date());
            tInternetAccessUpdateServiceOrderDAO.insertSelective(newOrder);
        } else {
            newOrder.setOrderNo(oldOrder.getOrderNo());
            saveOrderChangeLog(oldOrder, newOrder);
            newOrder.setCreaterBy(null);
            newOrder.setCreateTime(null);
            tInternetAccessUpdateServiceOrderDAO.updateByPrimaryKeySelective(newOrder);
        }
        return newOrder.getOrderId();
    }

    /**
     * 保存订单变更记录
     *
     * @param oldOrder 旧值
     * @param newOrder 新值
     * @return
     */
    private Integer saveOrderChangeLog(TInternetAccessUpdateServiceOrder oldOrder,TInternetAccessUpdateServiceOrder newOrder) {
        int result = 0;
        long version = Instant.now().toEpochMilli();
        Date now = new Date();
        List<TOrderChangeLog> changeLogs = Lists.newArrayList();
        List<ModifiedPropertyInfo> modifiedPropertyInfos = CompareObjectPropertyUtil.getDifferentProperty(oldOrder, newOrder, "createrBy", "createTime", "modifiedBy", "modifiedTime");
        if (!CollectionUtils.isEmpty(modifiedPropertyInfos)) {
            modifiedPropertyInfos.forEach(p -> {
                TOrderChangeLog log = new TOrderChangeLog();
                log.setChageType(ChangeType.MODIFIED.getValue());
                log.setVersion((int) version);
                log.setOrderTableName(OrderType.INTERNET_ACCESS_UPDATE_SERVICE_ORDER.getValue());
                log.setOrderId(oldOrder.getOrderId());
                log.setFieldName(p.getPropertyName());
                log.setOldVlaue(p.getOldValueString());
                log.setNewVlaue(p.getNewValueString());
                log.setCreaterBy(newOrder.getCreaterBy());
                log.setCreateTime(now);
                log.setIsDeleted(false);
                changeLogs.add(log);
            });
            result = tOrderChangeLogDAO.insertBatch(changeLogs);
        }
        return result;
    }

    /**
     * 得到 互联网接入-升级服务-订单详情
     *
     * @param orderId 订单id
     * @return int
     */
    public InternetAccessUpdateServiceOrderResp getOrderDetail(Integer orderId) {
        InternetAccessUpdateServiceOrderResp productDOrderDetailResp = new InternetAccessUpdateServiceOrderResp();
        TInternetAccessUpdateServiceOrder dOrder = tInternetAccessUpdateServiceOrderDAO.selectByPrimaryKey(orderId);
        if (dOrder != null) {
            TProduct product = tProductDAO.selectByPrimaryKey(dOrder.getProductId());
            if (product != null) {
                dOrder.setProductCode(product.getProductCode());
                dOrder.setProductName(product.getProductName());
            }
            BeanUtils.copyProperties(dOrder,productDOrderDetailResp);
            if(product !=null){
                TCategory category= tCategoryDAO.selectByPrimaryKey(product.getCategoryId());
                if(category !=null){
                    productDOrderDetailResp.setCategoryId(category.getCategoryId());
                    productDOrderDetailResp.setCategoryCode(category.getCategoryCode());
                    productDOrderDetailResp.setCategoryName(category.getCategoryName());
                    productDOrderDetailResp.setCategoryLevel(category.getCategoryLevel());
                }
            }
        }
        return productDOrderDetailResp;
    }


    /**
     * 得到 互联网接入-升级服务-订单列表
     *
     * @param pageParam 分页参数
     * @return PageInfo
     */
    public PageInfo<InternetAccessUpdateServiceOrderResp> selectByQueryParam(PageParam<InternetAccessUpdateServiceOrderQueryParam> pageParam){
        String orderBy;
        if (StringUtils.isEmpty(pageParam.getSortName())) {
            orderBy = "create_time desc ";
        } else {
            orderBy = pageParam.getSortName() + (pageParam.getReverse() ? " desc" : " asc");
        }
        PageInfo<TInternetAccessUpdateServiceOrder> pageInfo= PageHelper.offsetPage(pageParam.getOffset(), pageParam.getPageSize())
                .setOrderBy(orderBy)
                .doSelectPageInfo(
                        new ISelect() {
                            @Override
                            public void doSelect() {
                                tInternetAccessUpdateServiceOrderDAO.selectByQueryParam(pageParam.getSearchParam());
                            }
                        }
                );
        return PageInfoUtil.convert(pageInfo, InternetAccessUpdateServiceOrderResp.class);
    }
}
