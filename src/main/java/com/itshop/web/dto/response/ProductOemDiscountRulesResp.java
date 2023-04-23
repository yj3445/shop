package com.itshop.web.dto.response;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

/**
 * OEM产品 折扣规则 项类型
 *
 * @author liufenglong
 * @date 2022/7/21
 */
@Data
public class ProductOemDiscountRulesResp
    implements Comparable<ProductOemDiscountRulesResp>, Serializable {
    private static final long serialVersionUID = 1L;

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
     * 规则项
     */
    private List<ProductOemDiscountRulesItemResp> rulesItems;

    public ProductOemDiscountRulesResp(){
        rulesItems = Lists.newArrayList();
    }

    @Override
    public int compareTo(@NotNull ProductOemDiscountRulesResp o) {
        return Comparator.comparing(ProductOemDiscountRulesResp::getItemType)
                .thenComparing(ProductOemDiscountRulesResp::getItemTypeName)
                .thenComparingInt(ProductOemDiscountRulesResp::getItemTypeOrderNum)
                .compare(this, o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOemDiscountRulesResp that = (ProductOemDiscountRulesResp) o;
        return Objects.equal(itemType, that.itemType) &&
                Objects.equal(itemTypeName, that.itemTypeName) &&
                Objects.equal(itemTypeOrderNum, that.itemTypeOrderNum);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(itemType, itemTypeName, itemTypeOrderNum);
    }
}
