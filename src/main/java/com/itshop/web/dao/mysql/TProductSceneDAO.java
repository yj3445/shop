package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TProductScene;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TProductSceneDAO {
    int deleteByPrimaryKey(@Param("productSceneId") Integer productSceneId,
                           @Param("modifiedBy") Integer modifiedBy);

    /**
     * 根据产品ID删除应用场景 数据
     *
     * @param productId  产品id
     * @param modifiedBy 更新人
     * @return
     */
    int deleteByProductId(@Param("productId") Integer productId,
                          @Param("modifiedBy") Integer modifiedBy);

    /**
     * 批量添加 应用场景 数据
     *
     * @param list
     * @return
     */
    int insertBatch(@Param("list") List<TProductScene> list);

    int insertSelective(TProductScene record);

    TProductScene selectByPrimaryKey(Integer productSceneId);

    /**
     * 根据产品id得到 应用场景 数据
     *
     * @param productId
     * @return
     */
    List<TProductScene> selectByProductId(@Param("productId") Integer productId);

    int updateByPrimaryKeySelective(TProductScene record);
}