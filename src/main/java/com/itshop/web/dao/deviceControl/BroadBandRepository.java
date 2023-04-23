package com.itshop.web.dao.deviceControl;

import com.alibaba.fastjson.JSONObject;
import com.itshop.web.dto.deviceControl.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

/**
 * 宽带业务
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Repository
public class BroadBandRepository extends RestBaseRepository {

    /**
     * 查询上网业务的带宽
     *
     * @param queryRequest 查询参数
     * @return DeviceControlResponseResult
     */
    public DeviceControlResponseResult<BandWidthQueryResponse> query(BandWidthQueryRequest queryRequest) {
        String url = String.format("%s/api/bandwidth/query", apiUrl);
        ParameterizedTypeReference<DeviceControlResponseResult<BandWidthQueryResponse>> responseBodyType = new ParameterizedTypeReference<DeviceControlResponseResult<BandWidthQueryResponse>>() {
        };
        DeviceControlResponseResult<BandWidthQueryResponse> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(queryRequest));
        return result;
    }

    /**
     * 修改上网业务的带宽
     *
     * @param updateRequest 更新参数
     * @return DeviceControlResponseResult
     */
    public DeviceControlResponseResult<String> update(BandWidthUpdateRequest updateRequest){
        String url = String.format("%s/api/bandwidth/update", apiUrl);
        ParameterizedTypeReference<DeviceControlResponseResult<String>> responseBodyType = new ParameterizedTypeReference<DeviceControlResponseResult<String>>() {
        };
        DeviceControlResponseResult<String> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(updateRequest));
        return result;
    }

    /**
     * 变更业务出口
     *
     * @param ispChangeRequest
     * @apiNote 变更上网业务的出口（运营商），对应前端 "互联网接入"的"出口"选项
     * @return
     */
    public DeviceControlResponseResult<String> ispChange(BandWidthIspChangeRequest ispChangeRequest){
        String url = String.format("%s/api/isp/change", apiUrl);
        ParameterizedTypeReference<DeviceControlResponseResult<String>> responseBodyType = new ParameterizedTypeReference<DeviceControlResponseResult<String>>() {
        };
        DeviceControlResponseResult<String> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(ispChangeRequest));
        return result;
    }
}
