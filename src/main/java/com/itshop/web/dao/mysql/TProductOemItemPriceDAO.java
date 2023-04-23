package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TProductOemItemPrice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TProductOemItemPriceDAO {
    int deleteByPrimaryKey(@Param("productOemItemPriceId") Integer productOemItemPriceId, @Param("modifiedBy") Integer modifiedBy);

    int deleteByAgentPriceId(@Param("agentPriceId") Integer agentPriceId, @Param("modifiedBy") Integer modifiedBy);

    int insertSelective(TProductOemItemPrice record);

    TProductOemItemPrice selectByPrimaryKey(Integer productOemItemPriceId);

    int updateByPrimaryKeySelective(TProductOemItemPrice record);

    /**
     * 根据组织及产品ID得到价格信息
     *
     * @param orgId 组织id
     * @param productId 产品id
     * @return
     */
    List<TProductOemItemPrice> selectByProductAndOrgId( @Param("productId") Integer productId,@Param("orgId") Integer orgId);

    /**
     * 根据组织id及产品id得到上级组织设置的价格信息
     * @param orgId 组织id
     * @param parentOrgId 上级组织id
     * @param productId 产品id
     * @return
     */
    List<TProductOemItemPrice> selectByTargetOrgIdAndProductId(@Param("orgId") Integer orgId,
                                                               @Param("parentOrgId") Integer parentOrgId,
                                                               @Param("productId") Integer productId);

    /**
     * 得到终端价格
     * @param orgId 组织id
     * @param productId 产品id
     * @return
     */
    List<TProductOemItemPrice> selectEndPrice(@Param("orgId") Integer orgId, @Param("productId") Integer productId);

    /**
     * 根据产品项ID及价格序列ID 得到产品项价格信息
     *
     * @param productItemId 产品项ID
     * @param agentPriceId 价格序列ID
     * @return
     */
    TProductOemItemPrice selectByProductItemIdAndAgentPriceId (@Param("productItemId") Integer productItemId,
                                 @Param("agentPriceId") Integer agentPriceId);

    /**
     * 更新产品OEMID
     *
     * @param record
     * @return
     */
    int updateProductOemId(TProductOemItemPrice record);
}