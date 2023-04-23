package com.itshop.web.dao.devicecontrol;

import com.alibaba.fastjson.JSONObject;
import com.itshop.web.dto.deviceControl.DeviceControlResponseResult;
import com.itshop.web.dto.deviceControl.FireWallAddPortRequest;
import com.itshop.web.dto.deviceControl.FireWallAddPortResponse;
import com.itshop.web.dto.deviceControl.FireWallEnableRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

/**
 * 防火墙业务
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Repository
public class FireWallRepository extends RestBaseRepository {

    /**
     * 使防火墙生效
     *
     * @param enableRequest 启用参数
     * @return DeviceControlResponseResult
     */
    public DeviceControlResponseResult<String> enable(FireWallEnableRequest enableRequest){
        String url = String.format("%s/api/firewall/enable", apiUrl);
        ParameterizedTypeReference<DeviceControlResponseResult<String>> responseBodyType = new ParameterizedTypeReference<DeviceControlResponseResult<String>>() {
        };
        DeviceControlResponseResult<String> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(enableRequest));
        return result;
    }

    /**
     * 使防火墙关闭
     *
     * @param enableRequest 关闭参数
     * @return DeviceControlResponseResult
     */
    public DeviceControlResponseResult<String> disable(FireWallEnableRequest enableRequest){
        String url = String.format("%s/api/firewall/disable", apiUrl);
        ParameterizedTypeReference<DeviceControlResponseResult<String>> responseBodyType = new ParameterizedTypeReference<DeviceControlResponseResult<String>>() {
        };
        DeviceControlResponseResult<String> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(enableRequest));
        return result;
    }

    /**
     * 增加防火墙开放的下行服务端口（防火墙默认关闭所有下行服务端口）
     *
     * @param addPortRequest 增加端口参数
     * @return DeviceControlResponseResult
     */
    public DeviceControlResponseResult<FireWallAddPortResponse> enableInPort(FireWallAddPortRequest addPortRequest){
        String url = String.format("%s/api/firewall/enable_in_port", apiUrl);
        ParameterizedTypeReference<DeviceControlResponseResult<FireWallAddPortResponse>> responseBodyType = new ParameterizedTypeReference<DeviceControlResponseResult<FireWallAddPortResponse>>() {
        };
        DeviceControlResponseResult<FireWallAddPortResponse> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(addPortRequest));
        return result;
    }

    /**
     * 增加防火墙屏蔽的上行访问端口（内部访问外部，默认均开放）
     *
     * @param addPortRequest 增加端口参数
     * @return DeviceControlResponseResult
     */
    public DeviceControlResponseResult<FireWallAddPortResponse> disableOutPort(FireWallAddPortRequest addPortRequest) {
        String url = String.format("%s/api/firewall/disable_out_port", apiUrl);
        ParameterizedTypeReference<DeviceControlResponseResult<FireWallAddPortResponse>> responseBodyType = new ParameterizedTypeReference<DeviceControlResponseResult<FireWallAddPortResponse>>() {
        };
        DeviceControlResponseResult<FireWallAddPortResponse> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(addPortRequest));
        return result;
    }
}
