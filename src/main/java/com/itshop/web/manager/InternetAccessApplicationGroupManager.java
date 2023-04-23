package com.itshop.web.manager;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itshop.web.dao.mysql.TApplicationGroupCustomDAO;
import com.itshop.web.dao.mysql.TApplicationGroupDAO;
import com.itshop.web.dao.po.TApplicationGroup;
import com.itshop.web.dto.PageParam;
import com.itshop.web.dto.request.ApplicationGroupParam;
import com.itshop.web.dto.response.ApplicationGroupCustomAndLineIndicators;
import com.itshop.web.dto.response.ApplicationGroupResp;
import com.itshop.web.util.PageInfoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 互联网接入-接入-自定义
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Service
public class InternetAccessApplicationGroupManager {

    @Autowired
    private TApplicationGroupCustomDAO tApplicationGroupCustomDAO;

    @Autowired
    private TApplicationGroupDAO tApplicationGroupDAO;

    /**
     * 应用实例组(产品D) 应用线路性能指标列表
     *
     * @param pageParam 分页参数
     * @return PageInfo
     */
    public PageInfo<ApplicationGroupCustomAndLineIndicators> selectTApplicationGroupCustomAndLineIndicators(PageParam pageParam) {
        String orderBy;
        if (StringUtils.isEmpty(pageParam.getSortName())) {
            orderBy = "a.application_id asc ";
        } else {
            orderBy = pageParam.getSortName() + (pageParam.getReverse() ? " desc" : " asc");
        }
        return PageHelper.offsetPage(pageParam.getOffset(), pageParam.getPageSize())
                .setOrderBy(orderBy)
                .doSelectPageInfo(
                        new ISelect() {
                            @Override
                            public void doSelect() {
                                tApplicationGroupCustomDAO.selectTApplicationGroupCustomAndLineIndicators();
                            }
                        }
                );
    }

    /**
     * 应用实例组(产品A、产品B、产品C) 应用列表
     *
     * @param pageParam 分页参数
     * @return PageInfo
     */
    public PageInfo<ApplicationGroupResp> selectTApplicationGroups(PageParam<ApplicationGroupParam> pageParam) {
        String orderBy;
        if (StringUtils.isEmpty(pageParam.getSortName())) {
            orderBy = "application_id asc ";
        } else {
            orderBy = pageParam.getSortName() + (pageParam.getReverse() ? " desc" : " asc");
        }
        if(pageParam.getSearchParam() == null) {
            pageParam.setSearchParam(new ApplicationGroupParam());
        }
        PageInfo<TApplicationGroup> pageInfo = PageHelper.offsetPage(pageParam.getOffset(), pageParam.getPageSize())
                .setOrderBy(orderBy)
                .doSelectPageInfo(
                        new ISelect() {
                            @Override
                            public void doSelect() {
                                tApplicationGroupDAO.selectTApplicationGroups(pageParam.getSearchParam().getApplicationGroup());
                            }
                        }
                );
        return PageInfoUtil.convert(pageInfo, ApplicationGroupResp.class);
    }

}
