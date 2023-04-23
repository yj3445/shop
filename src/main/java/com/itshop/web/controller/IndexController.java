package com.itshop.web.controller;

import com.google.common.collect.Maps;
import com.itshop.web.dto.RetResult;
import com.itshop.web.dto.RetWrapper;
import com.itshop.web.manager.DeviceControlManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 首页控制器
 *
 * @description 首页控制器
 * @author liufenglong
 * @date 2022/5/7
 */
@Slf4j
@RestController
@RequestMapping("/index")
@Api(value = "indexController", description = "首页控制器")
public class IndexController  {

    /**
     * index
     *
     * @description 这是一行说明
     */
    @GetMapping("/hello")
    @ApiOperation(value = "hello")
    public RetResult<Map> hello() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("key", "value");
        return RetWrapper.success(map);
    }
}
