package com.itshop.web.dao.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * t_internet_access_order
 *
 * @author liufenglong
 * @date 2022/5/7
 */
public class TInternetAccessOrder implements Serializable {
    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 订单编码
     */
    private String orderNo;

    /**
     * 带宽(单位:兆)
     */
    private Integer broadBand;

    /**
     * 出口(1智选,2联通,3电信,4移动)
     */
    private Integer export;

    /**
     * 合同期限(0,12,36)月
     */
    private Integer contractPeriod;

    /**
     * 缴费周期(1月,2季,3年)
     */
    private Integer paymentCycle;

    /**
     * 付费方式(1预付,2后付)
     */
    private Integer paymentMethod;

    /**
     * ip地址数量选择方式(1智选,2自选)
     */
    private Integer ipNum;

    /**
     * ip地址数量
     */
    private Integer ipNumValue;

    /**
     * 应用实例组(1产品A,2产品B,3产品C,4自定义)
     */
    private Integer application;

    /**
     * 创建人
     */
    private Integer createrBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 操作人
     */
    private Integer modifiedBy;

    /**
     * 更新时间
     */
    private Date modifiedTime;

    /**
     * 删除标识(0-未删除,1-删除)
     */
    private Boolean isDeleted;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 基础价格
     */
    private BigDecimal basicPrice;

    /**
     * 出口-折扣价
     */
    private BigDecimal exportDiscountPrice;

    /**
     * 合同期限-折扣价
     */
    private BigDecimal contractPeriodDiscountPrice;

    /**
     * 缴费周期-折扣价
     */
    private BigDecimal paymentCycleDiscountPrice;

    /**
     * 付费方式-折扣价
     */
    private BigDecimal paymentMethodDiscountPrice;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 业务编号
     */
    private String businessId;


    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 组织id
     */
    private Integer orgId;

    /**
     * 组织全称
     */
    private String orgFullPath;
    /**
     * 审核人
     */
    private Integer auditBy;
    /**
     * 审核时间
     */
    private Date auditTime;
    /**
     * 审核通过(备注)
     */
    private String auditPassRemark;
    /**
     * 审核拒绝(备注)
     */
    private String auditRefusedRemark;

    /**
     * 审核价格人
     */
    private Integer auditPriceBy;
    /**
     * 审核价格
     */
    private BigDecimal auditPrice;

    /**
     * 安装地址
     */
    private String installAddress;

    /**
     * 线路名称
     */
    private String lineName;


    /**
     * 创建人(英文名称)
     */
    private String createrUserId;

    /**
     * 创建人(用户名)
     */
    private String createrUserName;

    /**
     * 操作人(账户)
     */
    private String modifiedUserId;

    /**
     * 操作人(用户名)
     */
    private String modifiedUserName;

    /**
     * 审核人（账户)
     */
    private String auditUserId;

    /**
     * 审核人(用户名)
     */
    private String auditUserName;

    /**
     * 改价人（账户）
     */
    private String auditPriceUserId;

    /**
     * 改价人(用户名)
     */
    private String auditPriceUserName;


    /**
     * 交付时间
     */
    private Date deliveryTime;

    private static final long serialVersionUID = 1L;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getBroadBand() {
        return broadBand;
    }

    public void setBroadBand(Integer broadBand) {
        this.broadBand = broadBand;
    }

    public Integer getExport() {
        return export;
    }

    public void setExport(Integer export) {
        this.export = export;
    }

    public Integer getContractPeriod() {
        return contractPeriod;
    }

    public void setContractPeriod(Integer contractPeriod) {
        this.contractPeriod = contractPeriod;
    }

    public Integer getPaymentCycle() {
        return paymentCycle;
    }

    public void setPaymentCycle(Integer paymentCycle) {
        this.paymentCycle = paymentCycle;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getIpNum() {
        return ipNum;
    }

    public void setIpNum(Integer ipNum) {
        this.ipNum = ipNum;
    }

    public Integer getIpNumValue() {
        return ipNumValue;
    }

    public void setIpNumValue(Integer ipNumValue) {
        this.ipNumValue = ipNumValue;
    }

    public Integer getApplication() {
        return application;
    }

    public void setApplication(Integer application) {
        this.application = application;
    }

    public Integer getCreaterBy() {
        return createrBy;
    }

    public void setCreaterBy(Integer createrBy) {
        this.createrBy = createrBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(BigDecimal basicPrice) {
        this.basicPrice = basicPrice;
    }

    public BigDecimal getExportDiscountPrice() {
        return exportDiscountPrice;
    }

    public void setExportDiscountPrice(BigDecimal exportDiscountPrice) {
        this.exportDiscountPrice = exportDiscountPrice;
    }

    public BigDecimal getContractPeriodDiscountPrice() {
        return contractPeriodDiscountPrice;
    }

    public void setContractPeriodDiscountPrice(BigDecimal contractPeriodDiscountPrice) {
        this.contractPeriodDiscountPrice = contractPeriodDiscountPrice;
    }

    public BigDecimal getPaymentCycleDiscountPrice() {
        return paymentCycleDiscountPrice;
    }

    public void setPaymentCycleDiscountPrice(BigDecimal paymentCycleDiscountPrice) {
        this.paymentCycleDiscountPrice = paymentCycleDiscountPrice;
    }

    public BigDecimal getPaymentMethodDiscountPrice() {
        return paymentMethodDiscountPrice;
    }

    public void setPaymentMethodDiscountPrice(BigDecimal paymentMethodDiscountPrice) {
        this.paymentMethodDiscountPrice = paymentMethodDiscountPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgFullPath() {
        return orgFullPath;
    }

    public void setOrgFullPath(String orgFullPath) {
        this.orgFullPath = orgFullPath;
    }

    public Integer getAuditBy() {
        return auditBy;
    }

    public void setAuditBy(Integer auditBy) {
        this.auditBy = auditBy;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditPassRemark() {
        return auditPassRemark;
    }

    public void setAuditPassRemark(String auditPassRemark) {
        this.auditPassRemark = auditPassRemark;
    }

    public String getAuditRefusedRemark() {
        return auditRefusedRemark;
    }

    public void setAuditRefusedRemark(String auditRefusedRemark) {
        this.auditRefusedRemark = auditRefusedRemark;
    }

    public Integer getAuditPriceBy() {
        return auditPriceBy;
    }

    public void setAuditPriceBy(Integer auditPriceBy) {
        this.auditPriceBy = auditPriceBy;
    }

    public BigDecimal getAuditPrice() {
        return auditPrice;
    }

    public void setAuditPrice(BigDecimal auditPrice) {
        this.auditPrice = auditPrice;
    }

    public String getInstallAddress() {
        return installAddress;
    }

    public void setInstallAddress(String installAddress) {
        this.installAddress = installAddress;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getCreaterUserName() {
        return createrUserName;
    }

    public void setCreaterUserName(String createrUserName) {
        this.createrUserName = createrUserName;
    }

    public String getModifiedUserName() {
        return modifiedUserName;
    }

    public void setModifiedUserName(String modifiedUserName) {
        this.modifiedUserName = modifiedUserName;
    }

    public String getCreaterUserId() {
        return createrUserId;
    }

    public void setCreaterUserId(String createrUserId) {
        this.createrUserId = createrUserId;
    }

    public String getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(String modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public String getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId;
    }

    public String getAuditUserName() {
        return auditUserName;
    }

    public void setAuditUserName(String auditUserName) {
        this.auditUserName = auditUserName;
    }

    public String getAuditPriceUserId() {
        return auditPriceUserId;
    }

    public void setAuditPriceUserId(String auditPriceUserId) {
        this.auditPriceUserId = auditPriceUserId;
    }

    public String getAuditPriceUserName() {
        return auditPriceUserName;
    }

    public void setAuditPriceUserName(String auditPriceUserName) {
        this.auditPriceUserName = auditPriceUserName;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}