package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TAgentPrice;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TAgentPriceDAO {
    int deleteByPrimaryKey(@Param("agentPriceId") Integer agentPriceId, @Param("modifiedBy") Integer modifiedBy);

    int insert(TAgentPrice record);

    int insertSelective(TAgentPrice record);

    TAgentPrice selectByPrimaryKey(Integer agentPriceId);

    List<TAgentPrice> selectByAgentPriceIds(@Param("list") List<Integer> agentPriceIds);

    int updateByPrimaryKeySelective(TAgentPrice record);

    int updateByPrimaryKey(TAgentPrice record);

    /**
     * 根据组织ID得到价格序列信息
     *
     * @param orgId 组织id
     * @return
     */
    List<TAgentPrice> selectByOrgId(@Param("orgId") Integer orgId);

    /**
     * 判断同一组织下是否存在同名的价格序列名称
     *
     * @param orgId     组织id
     * @param priceName 价格序列名称
     * @return
     */
    int countByOrgIdAndPriceName(@Param("orgId") Integer orgId, @Param("priceName") String priceName);


    /**
     * 判断同一组织下是否存在同名的价格序列名称
     *
     * @param orgId        组织id
     * @param priceName    价格序列名称
     * @param agentPriceId 价格序列id
     * @return
     */
    int countByOrgIdAndPriceNameAndAgentPriceId(@Param("orgId") Integer orgId, @Param("priceName") String priceName, @Param("agentPriceId") Integer agentPriceId);

    /**
     * 代理商新增默认价格序列（客户公司价格）
     * @param StartCreateTime
     * @param endCreateTime
     * @return
     */
    int agentCompanyInsertDefaultPrice(@Param("StartCreateTime") Date StartCreateTime, @Param("endCreateTime") Date endCreateTime);
}