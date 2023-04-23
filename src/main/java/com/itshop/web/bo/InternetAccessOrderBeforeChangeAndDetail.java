package com.itshop.web.bo;

import com.itshop.web.dao.po.TInternetAccessOrderBeforeChange;
import com.itshop.web.dao.po.TInternetAccessOrderDetailBeforeChange;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author liufenglong
 * @date 2022/8/22
 */
@Data
public class InternetAccessOrderBeforeChangeAndDetail  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单历史主表
     */
    private TInternetAccessOrderBeforeChange change;

    /**
     * 订单历史从表
     */
    private List<TInternetAccessOrderDetailBeforeChange> details;
}
