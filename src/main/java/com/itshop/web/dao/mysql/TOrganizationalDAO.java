package com.itshop.web.dao.mysql;


import com.itshop.web.dao.po.TOrganizational;

import java.util.List;

/**
 * 组织架构 数据处理
 *
 * @author liufenglong
 * @date 2022/5/7
 */
public interface TOrganizationalDAO {


    TOrganizational selectByPrimaryKey(Integer orgId);


    List<TOrganizational> selectAllOrganizational();

}