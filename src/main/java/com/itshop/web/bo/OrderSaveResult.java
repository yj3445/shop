package com.itshop.web.bo;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.itshop.web.enums.DeviceControlTransCode;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
public class OrderSaveResult implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 业务类型:互联网接入,防火墙,url加速
     */
    private String serviceType;

    /**
     * 变更记录id
     */
    private Integer changeId;

    /**
     * 全局事务ID
     */
    private Integer transId;

    /**
     * 事务中间状态
     */
    private List<Integer> middleStatus;

    /**
     * 事务当前状态
     */
    private Integer transCurrent;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 订单
     */
    private String orderNo;

    /**
     * 产品编码
     */
    private String productCode;

    public String getBusiness_id(){
        return String.format("%s_%s",productCode,orderNo);
    }

    public String getMiddleStatusStr() {
        if (CollectionUtils.isEmpty(middleStatus)) {
            return String.valueOf(DeviceControlTransCode.INITIAL.getCode());
        }
        return Joiner.on(",").join(middleStatus);
    }

    public String getMiddleStatusDesc(){
        if (CollectionUtils.isEmpty(middleStatus)) {
            return DeviceControlTransCode.INITIAL.getDesc();
        }
        StringBuilder remark = new StringBuilder();
        middleStatus.forEach(integer -> {
            remark.append(DeviceControlTransCode.getDescByStatus(integer));
            remark.append(";");
        });
        return remark.toString();
    }

    public Integer getTransEnd() {
        if (CollectionUtils.isEmpty(middleStatus)) {
            return DeviceControlTransCode.INITIAL.getCode();
        }
        StringBuilder end = new StringBuilder();
        middleStatus.forEach(end::append);
        return Integer.parseInt(end.toString());
    }

    public LinkedList<Integer> getAfterStatusList(Integer currentStatus) {
        LinkedList<Integer> afterStatusList = Lists.newLinkedList();
        if (!CollectionUtils.isEmpty(middleStatus)) {
            int currentIndex = this.middleStatus.indexOf(currentStatus);
            for (int i = currentIndex + 1; i < this.middleStatus.size(); i++) {
                afterStatusList.add(this.middleStatus.get(i));
            }
        }
        if (afterStatusList.size() == 0) {
            afterStatusList.add(getTransEnd());
        }
        return afterStatusList;
    }
}
