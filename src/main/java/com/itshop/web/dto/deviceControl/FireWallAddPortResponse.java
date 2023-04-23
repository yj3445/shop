package com.itshop.web.dto.deviceControl;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 防火墙添加端口响应
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Data
public class FireWallAddPortResponse  implements Serializable {
    /** 更新后的列表 */
    private List<Integer> portlist;
    /** 返回的列表长度 */
    private Integer total;
}
