package com.itshop.web.dto.deviceControl;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 添加URL查询响应
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Data
public class UrlSpeedUpQueryResponse  implements Serializable {
    /**
     * URL列表
     * url or ip address
     * */
    private List<String> urllist;

    /**
     * 返回的列表长度
     * */
    private Integer total;
}
