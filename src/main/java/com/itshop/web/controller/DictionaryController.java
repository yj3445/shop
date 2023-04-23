package com.itshop.web.controller;

import com.github.pagehelper.PageInfo;
import com.itshop.web.dto.PageParam;
import com.itshop.web.dto.RetResult;
import com.itshop.web.dto.RetWrapper;
import com.itshop.web.dto.request.TIdcFirewallOrderQueryParam;
import com.itshop.web.dto.response.DictionaryResp;
import com.itshop.web.dto.response.DictionaryTypeResp;
import com.itshop.web.dto.response.IdcFireWallOrderResp;
import com.itshop.web.manager.DictionaryManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 字典-控制器
 */
@Slf4j
@RestController
@RequestMapping("/dictionary")
@Api(value = "DictionaryController", description = "字典-控制器")
public class DictionaryController {

    @Autowired
    DictionaryManager dictionaryManager;

    /**
     * 根据字典类型id得到字典值
     *
     * @param dictionaryTypeId
     * @return RetResult
     */
    @GetMapping("/type/detail")
    @ApiOperation(value = "根据字典类型id得到字典值")
    public RetResult<DictionaryTypeResp> getOrderDetail(@RequestParam("dictionaryTypeId") Integer dictionaryTypeId) {
        DictionaryTypeResp result = dictionaryManager.getDictionaryTypeById(dictionaryTypeId);
        return RetWrapper.success(result);
    }

    /**
     * 得到字典类型列表
     *
     * @param pageParam
     * @return RetResult
     */
    @PostMapping("/type/list")
    @ApiOperation(value = "得到字典类型列表")
    public RetResult<PageInfo<DictionaryTypeResp>> selectByQueryParam(@RequestBody PageParam pageParam) {
        PageInfo<DictionaryTypeResp> result = dictionaryManager.selectByQueryParam(pageParam);
        return RetWrapper.success(result);
    }
}
