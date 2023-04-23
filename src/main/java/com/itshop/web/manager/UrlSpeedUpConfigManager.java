package com.itshop.web.manager;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itshop.web.dao.mysql.TUrlSpeedUpConfigDAO;
import com.itshop.web.dao.po.TUrlSpeedUpConfig;
import com.itshop.web.dto.PageParam;
import com.itshop.web.dto.request.UrlSpeedUpConfigQueryParam;
import com.itshop.web.dto.response.UrlSpeedUpConfigResp;
import com.itshop.web.util.PageInfoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * URL加速列表服务
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Service
public class UrlSpeedUpConfigManager {

    @Autowired
    TUrlSpeedUpConfigDAO tUrlSpeedUpConfigDAO;

    /**
     * 得到URL加速配置列表
     *
     * @param pageParam 分页参数
     * @return PageInfo
     */
    public PageInfo<UrlSpeedUpConfigResp> selectByQueryParam(PageParam<UrlSpeedUpConfigQueryParam> pageParam) {
        String orderBy;
        if (StringUtils.isEmpty(pageParam.getSortName())) {
            orderBy = "order_num asc ";
        } else {
            orderBy = pageParam.getSortName() + (pageParam.getReverse() ? " desc" : " asc");
        }
        PageInfo<TUrlSpeedUpConfig> pageInfo = PageHelper.offsetPage(pageParam.getOffset(), pageParam.getPageSize())
                .setOrderBy(orderBy)
                .doSelectPageInfo(
                        () -> tUrlSpeedUpConfigDAO.selectByQueryParam(pageParam.getSearchParam())
                );
        return PageInfoUtil.convert(pageInfo, UrlSpeedUpConfigResp.class);
    }
}
