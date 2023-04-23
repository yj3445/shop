package com.itshop.web.dao.deviceControl;

import com.alibaba.fastjson.JSONObject;
import com.itshop.web.dto.deviceControl.DeviceControlResponseResult;
import com.itshop.web.dto.deviceControl.UrlSpeedUpAddRequest;
import com.itshop.web.dto.deviceControl.UrlSpeedUpQueryRequest;
import com.itshop.web.dto.deviceControl.UrlSpeedUpQueryResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

/**
 * URL加速业务
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Repository
public class UrlSpeedUpRepository extends RestBaseRepository {

    /**
     * 查询已设置加速的URL列表
     * */
    public DeviceControlResponseResult<UrlSpeedUpQueryResponse> query(UrlSpeedUpQueryRequest queryRequest){
        String url = String.format("%s/api/urlboost/query", apiUrl);
        ParameterizedTypeReference<DeviceControlResponseResult<UrlSpeedUpQueryResponse>> responseBodyType = new ParameterizedTypeReference<DeviceControlResponseResult<UrlSpeedUpQueryResponse>>() {
        };
        DeviceControlResponseResult<UrlSpeedUpQueryResponse> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(queryRequest));
        return result;
    }

    /**
     * 向URL加速列表中添加URL
     * */
    public DeviceControlResponseResult<UrlSpeedUpQueryResponse> add(UrlSpeedUpAddRequest addRequest){
        String url = String.format("%s/api/urlboost/add", apiUrl);
        ParameterizedTypeReference<DeviceControlResponseResult<UrlSpeedUpQueryResponse>> responseBodyType = new ParameterizedTypeReference<DeviceControlResponseResult<UrlSpeedUpQueryResponse>>() {
        };
        DeviceControlResponseResult<UrlSpeedUpQueryResponse> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(addRequest));
        return result;
    }

    /**
     * 删除URL加速列表中的某一项
     * */
    public void  remove() {
        String url = String.format("%s/api/urlboost/remove", apiUrl);
    }
}
