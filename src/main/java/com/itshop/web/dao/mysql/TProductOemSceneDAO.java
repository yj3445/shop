package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TProductOemScene;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TProductOemSceneDAO {
    int deleteByPrimaryKey( @Param("productOemSceneId") Integer productOemSceneId,
                           @Param("modifiedBy") Integer modifiedBy);

    int deleteByProductOemId( @Param("productOemId") Integer productOemId,
                            @Param("modifiedBy") Integer modifiedBy);

    /**
     * 批量添加 应用场景定义 数据
     *
     * @param list
     * @return
     */
    int insertBatch(@Param("list") List<TProductOemScene> list);

    int insertSelective(TProductOemScene record);

    TProductOemScene selectByPrimaryKey(Integer productOemSceneId);

    /**
     * 根据产品id得到对应的应用场景定义
     *
     * @param productOemId
     * @return
     */
    List<TProductOemScene> selectByProductOemId(@Param("productOemId") Integer productOemId);

    int updateByPrimaryKeySelective(TProductOemScene record);

}