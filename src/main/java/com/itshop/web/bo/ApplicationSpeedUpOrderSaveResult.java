package com.itshop.web.bo;

import lombok.Data;

import java.util.List;

@Data
public class ApplicationSpeedUpOrderSaveResult extends OrderSaveResult {

    /**
     * 旧url列表
     */
    private List<String> oldUrls;

    /**
     * 新url列表
     */
    private List<String> newUrls;

}