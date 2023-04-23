package com.itshop.web.dao.mysql;

import com.itshop.web.dto.request.ApplicationSpeedUpOrderQueryParam;
import com.itshop.web.dto.request.InternetAccessOrderQueryParam;
import com.itshop.web.dto.response.ApplicationSpeedUpUnionOrderResp;
import com.itshop.web.dto.response.InternetAccessUnionOrderResp;

import java.util.List;

/**
 * @author liufenglong
 * @date 2022/6/13
 */
public interface UserConsoleDAO {

    /**
     * 查询互联网接入(升级服务)订单列表
     *
     * @param queryParam
     * @return
     */
    List<InternetAccessUnionOrderResp> selectInternetAccessUnionOrder(InternetAccessOrderQueryParam queryParam);

    /**
     * 查询应用加速(URL加速及其他类型加速)订单列表
     *
     * @param queryParam
     * @return
     */
    List<ApplicationSpeedUpUnionOrderResp> selectApplicationSpeedUpUnionOrder(ApplicationSpeedUpOrderQueryParam queryParam);
}
