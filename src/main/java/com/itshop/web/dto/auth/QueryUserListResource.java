package com.itshop.web.dto.auth;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
//@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QueryUserListResource extends ApiToken implements Serializable {

    private Integer pageNum;//页号,从1计数
    private Integer pageSize;//每页记录数,建议大于5

    private List<Integer> status;

    private String orderBy;//排序字段
    private String direction;//升序还是降序，asc，desc

    /**
     * 组织id
     */
    private List<Integer> orgId;

}
