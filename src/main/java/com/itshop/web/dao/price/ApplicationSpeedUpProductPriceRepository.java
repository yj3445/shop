package com.itshop.web.dao.price;

import com.alibaba.fastjson.JSONArray;
import com.itshop.web.constants.CacheKeyConstants;
import com.itshop.web.dto.price.ApplicationSpeedUpProductPriceConfig;
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

@Repository("applicationSpeedUpProductPriceRepository")
public class ApplicationSpeedUpProductPriceRepository {

    /**
     * 得到产品价格配置信息
     *
     * @param productId 产品id
     * @return ApplicationSpeedUpProductPriceConfig
     */
    public ApplicationSpeedUpProductPriceConfig getApplicationSpeedUpProductPrice(Integer productId) {
        ApplicationSpeedUpProductPriceConfig price = new ApplicationSpeedUpProductPriceConfig();
        ApplicationSpeedUpProductPriceRepository repository = (ApplicationSpeedUpProductPriceRepository) ApplicationContextProvider.getBean("applicationSpeedUpProductPriceRepository");
        List<ApplicationSpeedUpProductPriceConfig> list = repository.getApplicationSpeedUpProductPrices();
        if (!CollectionUtils.isEmpty(list)) {
            return list.stream()
                    .filter(p -> Objects.equals(p.getProductId(), productId))
                    .findFirst().orElseGet(() -> price);
        }
        return price;
    }

    /**
     * 应用加速产品价格配置信息
     * @return List<ApplicationSpeedUpProductPriceConfig>
     */
    @Cacheable(value = CacheKeyConstants.APPLICATION_SPEED_UP_PRODUCT_PRICE_CONFIG, cacheManager = "caffeineCacheManager")
    public List<ApplicationSpeedUpProductPriceConfig> getApplicationSpeedUpProductPrices() {
        ClassPathResource resource = new ClassPathResource("price-config/application-speed-up-product-price-config.json");
        StringBuilder sb = new StringBuilder();
        try {
            InputStream inputStream = resource.getInputStream();
            for (String str : IOUtils.readLines(inputStream)) {
                sb.append(str);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return JSONArray.parseArray(sb.toString(), ApplicationSpeedUpProductPriceConfig.class);
    }
}
