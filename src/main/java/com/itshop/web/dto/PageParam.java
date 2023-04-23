package com.itshop.web.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Data
@ApiModel(value = "分页查询")
public class PageParam<T>  implements Serializable {
    /**
     * 偏移量
     */
    @ApiModelProperty(value = "偏移量")
    private Integer offset;
    /**
     * 每页大小
     */
    @ApiModelProperty(value = "每页大小")
    private Integer pageSize;
    /**
     * 排序名称
     */
    @ApiModelProperty(value = "排序名称")
    private String sortName;
    /**
     * 排序规则
     */
    @ApiModelProperty(value = "排序规则")
    private Boolean reverse;
    /**
     * 查询当前页
     */
    @ApiModelProperty(value = "查询当前页")
    private Integer pageNum;
    /**
     * 查询条件
     */
    @ApiModelProperty(value = "查询条件")
    private T searchParam;
}

