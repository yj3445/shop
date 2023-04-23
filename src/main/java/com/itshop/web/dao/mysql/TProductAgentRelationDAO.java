package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TCategoryProductAgent;
import com.itshop.web.dao.po.TProductAgentRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TProductAgentRelationDAO {
    int deleteByPrimaryKey(Integer productAgentRelationId);

    int insertSelective(TProductAgentRelation record);

    TProductAgentRelation selectByPrimaryKey(Integer productAgentRelationId);


    TProductAgentRelation selectByProductIdAndOrgId(Integer productId, Integer orgId);

    int updateByPrimaryKeySelective(TProductAgentRelation record);

    /**
     * 得到产品代理关系
     *
     * @param orgId 组织id
     * @return
     */
    List<TCategoryProductAgent> selectCategoryProductAgent(@Param("orgId") Integer orgId);

    /**
     * 根据组织ID及产品ID得到 代理商代理产品关系
     *
     * @param orgId
     * @return
     */
    TProductAgentRelation selectByOrgIdAndProductId(@Param("orgId") Integer orgId,
                                                    @Param("productId") Integer productId);
}