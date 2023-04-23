package com.itshop.web.dao.po;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * t_product_item
 * @author 
 */
public class TProductItem implements Serializable {
    /**
     * 产品项id
     */
    private Integer productItemId;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 产品项类型(存:BROAD_BAND、EXPORT、CONTRACT_PERIOD、PAYMENT_CYCLE、PAYMENT_METHOD)
     */
    private String itemType;

    /**
     * 产品项类型名称
     */
    private String itemTypeName;

    /**
     * 产品项类型-排序
     */
    private Integer itemTypeOrderNum;

    /**
     * 产品项名称
     */
    private String itemName;

    /**
     * 产品项值
     */
    private Integer itemValue;

    /**
     * 产品项-排序
     */
    private Integer orderNum;

    /**
     * 是否价格项(0-否,1-是)
     */
    private Boolean priceItem;

    /**
     * 默认价格
     */
    private BigDecimal defaultPrice;

    /**
     * 默认价格单位(YEAR,MONTH,DAY,HOUR)
     */
    private String defaultPriceUnit;

    /**
     * 是否折扣项(0-否,1-是)
     */
    private Boolean discountItem;

    /**
     * 默认折扣
     */
    private BigDecimal defaultDiscount;

    private static final long serialVersionUID = 1L;

    public Integer getProductItemId() {
        return productItemId;
    }

    public void setProductItemId(Integer productItemId) {
        this.productItemId = productItemId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    public Integer getItemTypeOrderNum() {
        return itemTypeOrderNum;
    }

    public void setItemTypeOrderNum(Integer itemTypeOrderNum) {
        this.itemTypeOrderNum = itemTypeOrderNum;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemValue() {
        return itemValue;
    }

    public void setItemValue(Integer itemValue) {
        this.itemValue = itemValue;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Boolean getPriceItem() {
        return priceItem;
    }

    public void setPriceItem(Boolean priceItem) {
        this.priceItem = priceItem;
    }

    public BigDecimal getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(BigDecimal defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public String getDefaultPriceUnit() {
        return defaultPriceUnit;
    }

    public void setDefaultPriceUnit(String defaultPriceUnit) {
        this.defaultPriceUnit = defaultPriceUnit;
    }

    public Boolean getDiscountItem() {
        return discountItem;
    }

    public void setDiscountItem(Boolean discountItem) {
        this.discountItem = discountItem;
    }

    public BigDecimal getDefaultDiscount() {
        return defaultDiscount;
    }

    public void setDefaultDiscount(BigDecimal defaultDiscount) {
        this.defaultDiscount = defaultDiscount;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TProductItem other = (TProductItem) that;
        return (this.getProductItemId() == null ? other.getProductItemId() == null : this.getProductItemId().equals(other.getProductItemId()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getItemType() == null ? other.getItemType() == null : this.getItemType().equals(other.getItemType()))
            && (this.getItemTypeName() == null ? other.getItemTypeName() == null : this.getItemTypeName().equals(other.getItemTypeName()))
            && (this.getItemTypeOrderNum() == null ? other.getItemTypeOrderNum() == null : this.getItemTypeOrderNum().equals(other.getItemTypeOrderNum()))
            && (this.getItemName() == null ? other.getItemName() == null : this.getItemName().equals(other.getItemName()))
            && (this.getItemValue() == null ? other.getItemValue() == null : this.getItemValue().equals(other.getItemValue()))
            && (this.getOrderNum() == null ? other.getOrderNum() == null : this.getOrderNum().equals(other.getOrderNum()))
            && (this.getPriceItem() == null ? other.getPriceItem() == null : this.getPriceItem().equals(other.getPriceItem()))
            && (this.getDefaultPrice() == null ? other.getDefaultPrice() == null : this.getDefaultPrice().equals(other.getDefaultPrice()))
            && (this.getDefaultPriceUnit() == null ? other.getDefaultPriceUnit() == null : this.getDefaultPriceUnit().equals(other.getDefaultPriceUnit()))
            && (this.getDiscountItem() == null ? other.getDiscountItem() == null : this.getDiscountItem().equals(other.getDiscountItem()))
            && (this.getDefaultDiscount() == null ? other.getDefaultDiscount() == null : this.getDefaultDiscount().equals(other.getDefaultDiscount()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getProductItemId() == null) ? 0 : getProductItemId().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getItemType() == null) ? 0 : getItemType().hashCode());
        result = prime * result + ((getItemTypeName() == null) ? 0 : getItemTypeName().hashCode());
        result = prime * result + ((getItemTypeOrderNum() == null) ? 0 : getItemTypeOrderNum().hashCode());
        result = prime * result + ((getItemName() == null) ? 0 : getItemName().hashCode());
        result = prime * result + ((getItemValue() == null) ? 0 : getItemValue().hashCode());
        result = prime * result + ((getOrderNum() == null) ? 0 : getOrderNum().hashCode());
        result = prime * result + ((getPriceItem() == null) ? 0 : getPriceItem().hashCode());
        result = prime * result + ((getDefaultPrice() == null) ? 0 : getDefaultPrice().hashCode());
        result = prime * result + ((getDefaultPriceUnit() == null) ? 0 : getDefaultPriceUnit().hashCode());
        result = prime * result + ((getDiscountItem() == null) ? 0 : getDiscountItem().hashCode());
        result = prime * result + ((getDefaultDiscount() == null) ? 0 : getDefaultDiscount().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", productItemId=").append(productItemId);
        sb.append(", productId=").append(productId);
        sb.append(", itemType=").append(itemType);
        sb.append(", itemTypeName=").append(itemTypeName);
        sb.append(", itemTypeOrderNum=").append(itemTypeOrderNum);
        sb.append(", itemName=").append(itemName);
        sb.append(", itemValue=").append(itemValue);
        sb.append(", orderNum=").append(orderNum);
        sb.append(", priceItem=").append(priceItem);
        sb.append(", defaultPrice=").append(defaultPrice);
        sb.append(", defaultPriceUnit=").append(defaultPriceUnit);
        sb.append(", discountItem=").append(discountItem);
        sb.append(", defaultDiscount=").append(defaultDiscount);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}