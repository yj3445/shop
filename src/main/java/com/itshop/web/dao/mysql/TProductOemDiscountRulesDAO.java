package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TProductOemDiscountRules;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TProductOemDiscountRulesDAO {
    int deleteByPrimaryKey(@Param("productOemDiscountRulesId") Integer productOemDiscountRulesId, @Param("modifiedBy") Integer modifiedBy);

    /**
     * 批量新增OEM产品折扣规则
     *
     * @param list 折扣规则
     * @return
     */
    int insertBatch(@Param("list") List<TProductOemDiscountRules> list);

    int insertSelective(TProductOemDiscountRules record);

    TProductOemDiscountRules selectByPrimaryKey(Integer productOemDiscountRulesId);

    int updateByPrimaryKeySelective(TProductOemDiscountRules record);

    int deleteByProductOemId(@Param("productOemId") Integer productOemId, @Param("modifiedBy") Integer modifiedBy);

    /**
     * 根据OEM产品ID
     *
     * @param productOemId
     * @return
     */
    List<TProductOemDiscountRules> selectByProductOemId(@Param("productOemId") Integer productOemId);
}