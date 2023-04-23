package com.itshop.web.dao.mysql;


import com.itshop.web.dao.po.TUser;

/**
 * 用户 数据处理
 *
 * @author liufenglong
 * @date 2022/5/7
 */
public interface TUserDAO {
    int deleteByPrimaryKey(Integer userId);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);
}