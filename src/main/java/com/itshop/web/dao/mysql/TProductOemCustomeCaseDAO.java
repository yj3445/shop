package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TProductOemCustomeCase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TProductOemCustomeCaseDAO {
    int deleteByPrimaryKey(@Param("productOemCustomeCaseId") Integer productOemCustomeCaseId,
                           @Param("modifiedBy") Integer modifiedBy);

    /**
     * 根据产品OEMid删除 客户案例数据
     *
     * @param productOemId 产品OEM id
     * @param modifiedBy 更新人
     * @return
     */
    int deleteByProductOemId(@Param("productOemId") Integer productOemId,
                             @Param("modifiedBy") Integer modifiedBy);

    /**
     * 批量添加 客户案例 数据
     *
     * @param list
     * @return
     */
    int insertBatch(@Param("list") List<TProductOemCustomeCase> list);

    int insertSelective(TProductOemCustomeCase record);

    TProductOemCustomeCase selectByPrimaryKey(Integer productOemCustomeCaseId);

    /**
     * 根据产品id得到对应的客户案例 数据
     *
     * @param productOemId
     * @return
     */
    List<TProductOemCustomeCase> selectByProductOemId(@Param("productOemId") Integer productOemId);

    int updateByPrimaryKeySelective(TProductOemCustomeCase record);

}