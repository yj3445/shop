package com.itshop.web.controller;

import com.itshop.web.annotation.PrintReqResp;
import com.itshop.web.bo.UserInfoVO;
import com.itshop.web.dto.RetResult;
import com.itshop.web.dto.RetWrapper;
import com.itshop.web.dto.request.ProductItemPriceUpdateParam;
import com.itshop.web.enums.OrgTypeEnum;
import com.itshop.web.manager.ProductManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/Product")
@Api(value = "ProductController", description = "产品设置-控制器")
public class ProductController extends BaseController {

    @Autowired
    ProductManager productManager;

    /**
     * 更新产品项价格
     *
     * @param productItemPriceUpdateParam 产品项价格更新参数
     * @return RetResult<Integer>
     */
    @PrintReqResp
    @PostMapping("/updateItemPrice")
    @ApiOperation(value = "更新产品项价格")
    public RetResult<Integer> updateProductItemPrice(@RequestBody ProductItemPriceUpdateParam productItemPriceUpdateParam) {
        UserInfoVO userInfoVO= getUserInfoVO();
        if(Objects.equals(OrgTypeEnum.SERVICE_PROVIDER.getCode(),userInfoVO.getOrgType())){
            return RetWrapper.success(productManager.updateProductItemPrice(productItemPriceUpdateParam));
        }
        else {
            return RetWrapper.failure("您无操作权限，请联系管理员");
        }
    }
}
