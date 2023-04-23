package com.itshop.web.dto.auth;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author liufenglong
 * @date 2022/7/5
 */
@Data
public class AppPermissionConfigTargetVO extends  AppPermissionTargetVO{


    private JSONObject config;

}
