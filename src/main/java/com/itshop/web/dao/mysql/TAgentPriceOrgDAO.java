package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TAgentPriceOrg;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TAgentPriceOrgDAO {
    int deleteByPrimaryKey(Integer agentPriceOrgId);

    int deleteByAgentPriceId(@Param("agentPriceId") Integer agentPriceId);

    int delete(@Param("list") List<Integer> agentPriceOrgIds);

    int insert(TAgentPriceOrg record);

    int insertBatch(@Param("list") List<TAgentPriceOrg> records);

    int insertSelective(TAgentPriceOrg record);

    TAgentPriceOrg selectByPrimaryKey(Integer agentPriceOrgId);

    int updateByPrimaryKeySelective(TAgentPriceOrg record);

    /**
     * 根据价格序列ID得到该价格序列下的组织成员
     *
     * @param agentPriceId
     * @return
     */
    List<TAgentPriceOrg> selectByAgentPriceId(@Param("agentPriceId") Integer agentPriceId);

    /**
     * 判断组织id是否已经存在于其他 价格序列中
     *
     * @param agentPriceId 当前价格序列id
     * @param targetOrgIds 目标组织id集合
     * @return
     */
    List<TAgentPriceOrg> selectByTargetOrgIdUnAgentPriceId(@Param("agentPriceId") Integer agentPriceId,
                                                            @Param("list") List<Integer> targetOrgIds);


    /**
     * 根据组织ID得到该价格序列下的组织成员
     *
     * @param orgId
     * @param priceType
     * @return
     */
    List<TAgentPriceOrg> selectByOrgIdAndPriceType(@Param("orgId") Integer orgId,
                                                   @Param("priceType")Integer priceType);
}