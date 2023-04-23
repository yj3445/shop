package com.itshop.web.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 互联网接入-升级服务-订单-查询-请求参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "互联网接入-升级服务-订单-查询-请求参数")
public class InternetAccessUpdateServiceOrderQueryParam extends OrderQueryParam {
}
