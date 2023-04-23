package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TCategoryProductOEM;
import com.itshop.web.dao.po.TProductOem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TProductOemDAO {
    int deleteByPrimaryKey(Integer productOemId);

    int insertSelective(TProductOem record);

    TProductOem selectByPrimaryKey(Integer productOemId);

    int updateByPrimaryKeySelective(TProductOem record);

    /**
     * 根据 产品id 及 组织id 得到产品OEM信息
     *
     * @param productId
     * @param orgId
     * @return
     */
    TProductOem selectByProductAndOrgId(@Param("productId") Integer productId,
                                        @Param("orgId") Integer orgId);

    List<TProductOem> selectByProductAndOrgIds(@Param("list") List<Integer> productIds,
                                               @Param("orgId") Integer orgId);


    /**
     * 得到组织ID代理产品的OEM信息
     *
     * @param orgId
     * @return
     */
    List<TCategoryProductOEM> selectCategoryProductOEM(@Param("orgId") Integer orgId);
}