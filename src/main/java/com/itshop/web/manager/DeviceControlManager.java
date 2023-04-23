package com.itshop.web.manager;

import com.alibaba.fastjson.JSONObject;
import com.itshop.web.bo.DeviceControlParam;
import com.itshop.web.dao.mysql.TDeviceControlLogDAO;
import com.itshop.web.dao.po.TDeviceControlLogWithBLOBs;
import com.itshop.web.dto.deviceControl.*;
import com.itshop.web.enums.DeviceControlServiceType;
import com.itshop.web.dao.devicecontrol.BroadBandRepository;
import com.itshop.web.dao.devicecontrol.FireWallRepository;
import com.itshop.web.dao.devicecontrol.UrlSpeedUpRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Supplier;

/**
 * 设备控制业务-管理器
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Slf4j
@Service
public class DeviceControlManager {

    @Value("${deviceControl.enable}")
    protected Boolean enable;

    @Autowired
    BroadBandRepository broadBandRepository;

    @Autowired
    FireWallRepository fireWallRepository;

    @Autowired
    UrlSpeedUpRepository urlSpeedUpService;

    @Autowired
    TDeviceControlLogDAO tDeviceControlLogDAO;

    @Async
    public void addAsync(TDeviceControlLogWithBLOBs record) {
        tDeviceControlLogDAO.insertSelective(record);
    }


    public DeviceControlResponseResult<BandWidthQueryResponse> broadBandQuery(DeviceControlParam<BandWidthQueryRequest> param ) {
        TDeviceControlLogWithBLOBs log = new TDeviceControlLogWithBLOBs();
        log.setServiceType(DeviceControlServiceType.BROAD_BAND.getCode());
        log.setOperationType("query");
        log.setBusinessId(param.getRequest().getBusiness_id());
        log.setCreaterBy(param.getCurrentUserId());
        log.setRequestJson(JSONObject.toJSONString(param.getRequest()));
        return getValueOrDefault(recordCallLog(() -> broadBandRepository.query(param.getRequest()),  log));
    }

    public DeviceControlResponseResult<String> broadBandUpdate(DeviceControlParam<BandWidthUpdateRequest> param) {
        TDeviceControlLogWithBLOBs log = new TDeviceControlLogWithBLOBs();
        log.setServiceType(DeviceControlServiceType.BROAD_BAND.getCode());
        log.setOperationType("update");
        log.setBusinessId(param.getRequest().getBusiness_id());
        log.setCreaterBy(param.getCurrentUserId());
        log.setRequestJson(JSONObject.toJSONString(param.getRequest()));
        return getValueOrDefault(recordCallLog(() -> broadBandRepository.update(param.getRequest()),  log));
    }

    public DeviceControlResponseResult<String> broadBandIspChange(DeviceControlParam<BandWidthIspChangeRequest> param) {
        TDeviceControlLogWithBLOBs log = new TDeviceControlLogWithBLOBs();
        log.setServiceType(DeviceControlServiceType.BROAD_BAND.getCode());
        log.setOperationType("ispChange");
        log.setBusinessId(param.getRequest().getBusiness_id());
        log.setCreaterBy(param.getCurrentUserId());
        log.setRequestJson(JSONObject.toJSONString(param.getRequest()));
        return getValueOrDefault(recordCallLog(() -> broadBandRepository.ispChange(param.getRequest()),  log));
    }

    public DeviceControlResponseResult<String> fireWallEnable(DeviceControlParam<FireWallEnableRequest> param) {
        TDeviceControlLogWithBLOBs log = new TDeviceControlLogWithBLOBs();
        log.setServiceType(DeviceControlServiceType.FIRE_WALL.getCode());
        log.setOperationType("enable");
        log.setBusinessId(param.getRequest().getBusiness_id());
        log.setCreaterBy(param.getCurrentUserId());
        log.setRequestJson(JSONObject.toJSONString(param.getRequest()));
        return getValueOrDefault(recordCallLog(() -> fireWallRepository.enable(param.getRequest()),  log));
    }

    public DeviceControlResponseResult<String> fireWallDisable(DeviceControlParam<FireWallEnableRequest> param) {
        TDeviceControlLogWithBLOBs log = new TDeviceControlLogWithBLOBs();
        log.setServiceType(DeviceControlServiceType.FIRE_WALL.getCode());
        log.setOperationType("disable");
        log.setBusinessId(param.getRequest().getBusiness_id());
        log.setCreaterBy(param.getCurrentUserId());
        log.setRequestJson(JSONObject.toJSONString(param.getRequest()));
        return getValueOrDefault(recordCallLog(() -> fireWallRepository.disable(param.getRequest()),  log));
    }

    /**
     * 增加防火墙开放的下行服务端口（防火墙默认关闭所有下行服务端口）
     * @param param
     * @return
     */
    public DeviceControlResponseResult<FireWallAddPortResponse> fireWallEnableInPort(DeviceControlParam<FireWallAddPortRequest> param) {
        TDeviceControlLogWithBLOBs log = new TDeviceControlLogWithBLOBs();
        log.setServiceType(DeviceControlServiceType.FIRE_WALL.getCode());
        log.setOperationType("enableInPort");
        log.setBusinessId(param.getRequest().getBusiness_id());
        log.setCreaterBy(param.getCurrentUserId());
        log.setRequestJson(JSONObject.toJSONString(param.getRequest()));
        return getValueOrDefault(recordCallLog(() -> fireWallRepository.enableInPort(param.getRequest()),  log));
    }

    /**
     * 增加防火墙屏蔽的上行访问端口（内部访问外部，默认均开放）
     * @param param
     * @return
     */
    public DeviceControlResponseResult<FireWallAddPortResponse> fireWallDisableOutPort(DeviceControlParam<FireWallAddPortRequest> param) {
        TDeviceControlLogWithBLOBs log = new TDeviceControlLogWithBLOBs();
        log.setServiceType(DeviceControlServiceType.FIRE_WALL.getCode());
        log.setOperationType("disableOutPort");
        log.setBusinessId(param.getRequest().getBusiness_id());
        log.setCreaterBy(param.getCurrentUserId());
        log.setRequestJson(JSONObject.toJSONString(param.getRequest()));
        return getValueOrDefault(recordCallLog(() -> fireWallRepository.disableOutPort(param.getRequest()),  log));
    }

    public DeviceControlResponseResult<UrlSpeedUpQueryResponse> urlSpeedUpQuery(DeviceControlParam<UrlSpeedUpQueryRequest> param) {
        TDeviceControlLogWithBLOBs log = new TDeviceControlLogWithBLOBs();
        log.setServiceType(DeviceControlServiceType.URL_SPEED_UP.getCode());
        log.setOperationType("query");
        log.setBusinessId(param.getRequest().getBusiness_id());
        log.setCreaterBy(param.getCurrentUserId());
        log.setRequestJson(JSONObject.toJSONString(param.getRequest()));
        return getValueOrDefault(recordCallLog(() -> urlSpeedUpService.query(param.getRequest()),  log));
    }

    public DeviceControlResponseResult<UrlSpeedUpQueryResponse> urlSpeedUpAdd(DeviceControlParam<UrlSpeedUpAddRequest> param) {
        TDeviceControlLogWithBLOBs log = new TDeviceControlLogWithBLOBs();
        log.setServiceType(DeviceControlServiceType.URL_SPEED_UP.getCode());
        log.setOperationType("add");
        log.setBusinessId(param.getRequest().getBusiness_id());
        log.setCreaterBy(param.getCurrentUserId());
        log.setRequestJson(JSONObject.toJSONString(param.getRequest()));
        return getValueOrDefault(recordCallLog(() -> urlSpeedUpService.add(param.getRequest()),  log));
    }

    public void urlSpeedUpRemove(Integer currentUserId) {
        urlSpeedUpService.remove();
    }

    private DeviceControlResponseResult getValueOrDefault(DeviceControlResponseResult result) {
        if (result == null) {
            DeviceControlResponseResult defaultValue = new DeviceControlResponseResult<>();
            defaultValue.setCode(DeviceControlResponseResult.EXCEPTION);
            //设备控制未启用时,返回成功
            if (BooleanUtils.isFalse(enable)) {
                defaultValue.setCode(DeviceControlResponseResult.SUCCEED);
            }
            return defaultValue;
        }
        return result;
    }


    /**
     * 异步记录日志
     *
     * @param <T>          服务方法返回值类型
     * @param function     服务方法
     * @param logWithBLOBs 调用日志
     */
    private <T> T recordCallLog(Supplier<T> function,  TDeviceControlLogWithBLOBs logWithBLOBs) {
        //设备控制未启用时,返回null
        if (BooleanUtils.isFalse(enable)) {
            return null;
        }
        T result = null;
        long startTime = System.currentTimeMillis();
        Exception ex = null;
        try {
            logWithBLOBs.setRecordType(true);
            result = function.get();
        } catch (Exception e) {
            ex = e;
            logWithBLOBs.setExceptionType(ExceptionUtils.getRootCause(e).getClass().getSimpleName());
            logWithBLOBs.setExceptionDesc(ExceptionUtils.getRootCauseMessage(e));
            logWithBLOBs.setRecordType(false);
        } finally {
            long endTime = System.currentTimeMillis();
            logWithBLOBs.setTimeMillis((int) (endTime - startTime));
            if (result != null) {
                logWithBLOBs.setResponseJson(JSONObject.toJSONString(result));
            }
            logWithBLOBs.setCreateTime(new Date());
        }
        if (ex != null) {
            log.error(JSONObject.toJSONString(logWithBLOBs), ex);
        } else {
            log.info(JSONObject.toJSONString(logWithBLOBs));
        }
        addAsync(logWithBLOBs);
        return result;
    }
}
