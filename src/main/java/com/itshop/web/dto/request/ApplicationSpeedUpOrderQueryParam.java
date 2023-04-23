package com.itshop.web.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 应用加速-订单-查询-请求参数
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Data
@ApiModel(value = "应用加速-订单-查询-请求参数")
@EqualsAndHashCode(callSuper = true)
public class ApplicationSpeedUpOrderQueryParam extends OrderQueryParam {

}