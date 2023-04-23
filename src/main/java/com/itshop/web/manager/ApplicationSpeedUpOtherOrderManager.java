package com.itshop.web.manager;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.itshop.web.dao.SequenceNumberRepository;
import com.itshop.web.dao.mysql.TApplicationSpeedUpOtherOrderDAO;
import com.itshop.web.dao.mysql.TCategoryDAO;
import com.itshop.web.dao.mysql.TOrderChangeLogDAO;
import com.itshop.web.dao.mysql.TProductDAO;
import com.itshop.web.dao.po.*;
import com.itshop.web.dto.PageParam;
import com.itshop.web.dto.request.ApplicationSpeedUpOtherOrderQueryParam;
import com.itshop.web.dto.request.ApplicationSpeedUpOtherOrderSaveParam;
import com.itshop.web.dto.request.InternetAccessUpdateServiceOrderQueryParam;
import com.itshop.web.dto.response.ApplicationSpeedUpOrderPriceResp;
import com.itshop.web.dto.response.ApplicationSpeedUpOtherOrderResp;
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
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class ApplicationSpeedUpOtherOrderManager {
    @Autowired
    TApplicationSpeedUpOtherOrderDAO tApplicationSpeedUpOtherOrderDAO;

    @Autowired
    TOrderChangeLogDAO tOrderChangeLogDAO;

    @Autowired
    SequenceNumberRepository sequenceNumberRepository;

    @Autowired
    TProductDAO tProductDAO;

    @Autowired
    TCategoryDAO tCategoryDAO;

    public int save(ApplicationSpeedUpOtherOrderSaveParam orderSaveParam, ApplicationSpeedUpOrderPriceResp orderPriceResp) {
        TApplicationSpeedUpOtherOrder oldOrder = null;
        if (orderSaveParam.getOrderId() != null && orderSaveParam.getOrderId() > 0) {
            oldOrder = tApplicationSpeedUpOtherOrderDAO.selectByPrimaryKey(orderSaveParam.getOrderId());
        }
        TApplicationSpeedUpOtherOrder newOrder = new TApplicationSpeedUpOtherOrder();
        BeanUtils.copyProperties(orderSaveParam, newOrder);
        BeanUtils.copyProperties(orderPriceResp, newOrder);
        newOrder.setModifiedBy(newOrder.getCreaterBy());
        newOrder.setModifiedTime(new Date());
        newOrder.setIsDeleted(false);
        if (oldOrder == null) {
            newOrder.setOrderNo(sequenceNumberRepository.getApplicationSpeedUpOrderNo());
            newOrder.setCreateTime(new Date());
            tApplicationSpeedUpOtherOrderDAO.insertSelective(newOrder);
        } else {
            newOrder.setOrderNo(oldOrder.getOrderNo());
            saveOrderChangeLog(oldOrder, newOrder);
            newOrder.setCreaterBy(null);
            newOrder.setCreateTime(null);
            tApplicationSpeedUpOtherOrderDAO.updateByPrimaryKeySelective(newOrder);
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
    private Integer saveOrderChangeLog(TApplicationSpeedUpOtherOrder oldOrder,TApplicationSpeedUpOtherOrder newOrder) {
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
                log.setOrderTableName(OrderType.APPLICATION_SPEED_UP_OTHER_ORDER.getValue());
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
     * 得到 应用加速-其他类型-订单详情
     *
     * @param orderId 订单id
     * @return int
     */
    public ApplicationSpeedUpOtherOrderResp getOrderDetail(Integer orderId) {
        ApplicationSpeedUpOtherOrderResp productDOrderDetailResp = new ApplicationSpeedUpOtherOrderResp();
        TApplicationSpeedUpOtherOrder dOrder = tApplicationSpeedUpOtherOrderDAO.selectByPrimaryKey(orderId);
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
     * 得到 应用加速-其他类型-订单列表
     *
     * @param pageParam 分页参数
     * @return PageInfo
     */
    public PageInfo<ApplicationSpeedUpOtherOrderResp> selectByQueryParam(PageParam<ApplicationSpeedUpOtherOrderQueryParam> pageParam){
        String orderBy;
        if (StringUtils.isEmpty(pageParam.getSortName())) {
            orderBy = "create_time desc ";
        } else {
            orderBy = pageParam.getSortName() + (pageParam.getReverse() ? " desc" : " asc");
        }
        PageInfo<TApplicationSpeedUpOtherOrder> pageInfo= PageHelper.offsetPage(pageParam.getOffset(), pageParam.getPageSize())
                .setOrderBy(orderBy)
                .doSelectPageInfo(
                        new ISelect() {
                            @Override
                            public void doSelect() {
                                tApplicationSpeedUpOtherOrderDAO.selectByQueryParam(pageParam.getSearchParam());
                            }
                        }
                );
        return PageInfoUtil.convert(pageInfo, ApplicationSpeedUpOtherOrderResp.class);
    }
}
