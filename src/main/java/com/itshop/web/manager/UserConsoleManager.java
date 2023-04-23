package com.itshop.web.manager;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itshop.web.bo.UserInfoVO;
import com.itshop.web.dao.auth.AuthorizationRepository;
import com.itshop.web.dao.mysql.UserConsoleDAO;
import com.itshop.web.dto.PageParam;
import com.itshop.web.dto.auth.OrganizationalDetailVO;
import com.itshop.web.dto.request.ApplicationSpeedUpOrderQueryParam;
import com.itshop.web.dto.request.InternetAccessOrderQueryParam;
import com.itshop.web.dto.response.ApplicationSpeedUpUnionOrderResp;
import com.itshop.web.dto.response.InternetAccessUnionOrderResp;
import com.itshop.web.enums.OrderStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author liufenglong
 * @date 2022/6/13
 */
@Service
public class UserConsoleManager {

    @Autowired
    UserConsoleDAO userConsoleDAO;

    /**
     * 查询互联网接入(升级服务)订单列表
     *
     * @param pageParam 分页参数
     * @return PageInfo
     */
    public PageInfo<InternetAccessUnionOrderResp> selectInternetAccessUnionOrder(PageParam<InternetAccessOrderQueryParam> pageParam) {
        String orderBy;
        if (StringUtils.isEmpty(pageParam.getSortName())) {
            orderBy = "create_time desc ";
        } else {
            orderBy = pageParam.getSortName() + (pageParam.getReverse() ? " desc" : " asc");
        }
        PageInfo<InternetAccessUnionOrderResp> result = PageHelper.offsetPage(pageParam.getOffset(), pageParam.getPageSize())
                .setOrderBy(orderBy)
                .doSelectPageInfo(
                        () -> userConsoleDAO.selectInternetAccessUnionOrder(pageParam.getSearchParam())
                );
        if (result != null && !CollectionUtils.isEmpty(result.getList())) {
            result.getList().forEach(c -> {
                c.setStatusDesc(OrderStatusEnum.getDescByValue(c.getStatus()));
                c.setChangeStatusDesc(OrderStatusEnum.getDescByValue(c.getChangeStatus()));
            });
        }
        return result;
    }

    /**
     * 查询应用加速(URL加速及其他类型加速)订单列表
     *
     * @param pageParam
     * @return
     */
    public PageInfo<ApplicationSpeedUpUnionOrderResp> selectApplicationSpeedUpUnionOrder(PageParam<ApplicationSpeedUpOrderQueryParam> pageParam) {
        String orderBy;
        if (StringUtils.isEmpty(pageParam.getSortName())) {
            orderBy = "create_time desc ";
        } else {
            orderBy = pageParam.getSortName() + (pageParam.getReverse() ? " desc" : " asc");
        }
        return PageHelper.offsetPage(pageParam.getOffset(), pageParam.getPageSize())
                .setOrderBy(orderBy)
                .doSelectPageInfo(
                        () -> userConsoleDAO.selectApplicationSpeedUpUnionOrder(pageParam.getSearchParam())
                );
    }
}
