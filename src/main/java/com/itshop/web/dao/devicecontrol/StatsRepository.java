package com.itshop.web.dao.devicecontrol;

import com.alibaba.fastjson.JSONObject;
import com.itshop.web.dto.deviceControl.DeviceControlResponseResult;
import com.itshop.web.dto.deviceControl.TrendStackRequest;
import com.itshop.web.dto.deviceControl.TrendStackResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

/**
 * @author liufenglong
 * @date 2022/8/5
 */
@Slf4j
@Repository
public class StatsRepository  extends RestBaseRepository {
    public DeviceControlResponseResult<TrendStackResponse> trendStacking(TrendStackRequest trendStackRequest) {
        String url = String.format("%s/api/stats/trend_stacking", apiUrl);
        ParameterizedTypeReference<DeviceControlResponseResult<TrendStackResponse>> responseBodyType = new ParameterizedTypeReference<DeviceControlResponseResult<TrendStackResponse>>() {
        };
        String requestBody = JSONObject.toJSONString(trendStackRequest);
        log.info("url:{},requestBody:{}", url, requestBody);
        DeviceControlResponseResult<TrendStackResponse> result = new DeviceControlResponseResult<TrendStackResponse> ();
        try{
            result = exchange(url, HttpMethod.POST, responseBodyType, requestBody);
        }
        catch (Exception ex) {
            log.error("调用流量监控接口发生异常!", ex);
        }
        return result;
    }
}
