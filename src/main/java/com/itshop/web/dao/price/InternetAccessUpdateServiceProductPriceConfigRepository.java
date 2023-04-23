package com.itshop.web.dao.price;

import com.alibaba.fastjson.JSONArray;
import com.itshop.web.constants.CacheKeyConstants;
import com.itshop.web.dto.price.InternetAccessUpdateServiceProductPriceConfig;
import com.itshop.web.support.ApplicationContextProvider;
import org.apache.commons.io.IOUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Repository(value = "internetAccessUpdateServiceProductPriceConfigRepository")
public class InternetAccessUpdateServiceProductPriceConfigRepository {
    /**
     * 得到产品价格配置信息
     *
     * @param productId 产品id
     * @return InternetAccessUpdateServiceProductPriceConfig
     */
    public InternetAccessUpdateServiceProductPriceConfig getInternetAccessProductPrice(Integer productId) {
        InternetAccessUpdateServiceProductPriceConfig price = new InternetAccessUpdateServiceProductPriceConfig();
        InternetAccessUpdateServiceProductPriceConfigRepository repository = (InternetAccessUpdateServiceProductPriceConfigRepository) ApplicationContextProvider.getBean("internetAccessUpdateServiceProductPriceConfigRepository");
        List<InternetAccessUpdateServiceProductPriceConfig> list = repository.getInternetAccessProductPrices();
        if (!CollectionUtils.isEmpty(list)) {
            return list.stream()
                    .filter(p -> Objects.equals(p.getProductId(), productId))
                    .findFirst().orElseGet(() -> price);
        }
        return price;
    }

    /**
     * 互联网接入-升级服务-产品价格配置信息
     *
     * @return List<InternetAccessUpdateServiceProductPriceConfig>
     */
    @Cacheable(value = CacheKeyConstants.INTERNET_ACCESS_UPDATE_SERVICE_PRODUCT_PRICE_CONFIG, cacheManager = "caffeineCacheManager")
    public List<InternetAccessUpdateServiceProductPriceConfig> getInternetAccessProductPrices() {
        ClassPathResource resource = new ClassPathResource("price-config/internet-access-update-service-product-price-config.json");
        StringBuilder sb = new StringBuilder();
        try {
            InputStream inputStream = resource.getInputStream();
            for (String str : IOUtils.readLines(inputStream)) {
                sb.append(str);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return JSONArray.parseArray(sb.toString(), InternetAccessUpdateServiceProductPriceConfig.class);
    }
}
