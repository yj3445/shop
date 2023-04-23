package com.itshop.web.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 应用实例组-请求参数
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Data
@ApiModel(value = "应用实例组-请求参数")
public class ApplicationGroupParam  implements Serializable {
    /**
     * 应用组
     */
    @ApiModelProperty(value = "应用组")
    private String applicationGroup;
}
