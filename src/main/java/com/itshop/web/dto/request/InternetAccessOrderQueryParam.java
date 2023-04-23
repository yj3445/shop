package com.itshop.web.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 互联网接入-自定义-订单-查询-请求参数
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "互联网接入-自定义-订单-查询-请求参数")
public class InternetAccessOrderQueryParam extends OrderQueryParam {

}
