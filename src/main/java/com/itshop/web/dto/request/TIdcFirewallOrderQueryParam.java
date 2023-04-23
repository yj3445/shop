package com.itshop.web.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * idc-防火墙-订单-查询-请求参数
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "idc-防火墙-订单-查询-请求参数")
public class TIdcFirewallOrderQueryParam extends OrderQueryParam {

}
