package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TProductCustomeCase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TProductCustomeCaseDAO {
    int deleteByPrimaryKey(@Param("productCustomeCaseId") Integer productCustomeCaseId,
                           @Param("modifiedBy") Integer modifiedBy);

    /**
     * 根据产品ID删除客户案例 数据
     *
     * @param productId  产品id
     * @param modifiedBy 更新人
     * @return
     */
    int deleteByProductId(@Param("productId") Integer productId,
                          @Param("modifiedBy") Integer modifiedBy);

    /**
     * 批量添加 客户案例 数据
     *
     * @param list
     * @return
     */
    int insertBatch(@Param("list") List<TProductCustomeCase> list);

    int insertSelective(TProductCustomeCase record);

    TProductCustomeCase selectByPrimaryKey(Integer productCustomeCaseId);

    /**
     * 根据产品id得到对应的客户案例 数据
     *
     * @param productId
     * @return
     */
    List<TProductCustomeCase> selectByProductId(@Param("productId") Integer productId);

    int updateByPrimaryKeySelective(TProductCustomeCase record);
}