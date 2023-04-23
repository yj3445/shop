package com.itshop.web.manager;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itshop.web.dao.mysql.TInternetAccessExportConfigDAO;
import com.itshop.web.dao.po.TInternetAccessExportConfig;
import com.itshop.web.dto.PageParam;
import com.itshop.web.dto.response.InternetAccessExportConfigResp;
import com.itshop.web.util.PageInfoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 互联网接入-出口配置
 *
 * @author liufenglong
 * @date 2022/5/13
 */
@Service
public class InternetAccessExportConfigManager {

    @Autowired
    TInternetAccessExportConfigDAO tInternetAccessExportConfigDAO;

    /**
     * 得到URL加速配置列表
     *
     * @param pageParam 分页参数
     * @return PageInfo
     */
    public PageInfo<InternetAccessExportConfigResp> selectByQueryParam(PageParam pageParam) {
        String orderBy;
        if (StringUtils.isEmpty(pageParam.getSortName())) {
            orderBy = "order_num asc ";
        } else {
            orderBy = pageParam.getSortName() + (pageParam.getReverse() ? " desc" : " asc");
        }
        PageInfo<TInternetAccessExportConfig> pageInfo = PageHelper.offsetPage(pageParam.getOffset(), pageParam.getPageSize())
                .setOrderBy(orderBy)
                .doSelectPageInfo(
                        () -> tInternetAccessExportConfigDAO.selectByQueryParam()
                );
        return PageInfoUtil.convert(pageInfo, InternetAccessExportConfigResp.class);
    }
}
