package com.itshop.web.dao;

import com.itshop.web.constants.SnowConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Repository
public class SequenceNumberRepository {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 得到业务编号
     *
     * @return
     */
    public String getBusinessId() {
        String yyyyMMdd = LocalDate.now().format(SnowConstants.DATE_ONLY_NUMBER_FORMATTER);
        String schemeCodePrefix = String.format("ISP%s", yyyyMMdd);
        return getCustomNo(schemeCodePrefix, getIncrementId(SnowConstants.HASH_FIELD_BUSINESS_ID, 1), SnowConstants.INCREMENT_ID_MAX_LENGTH);
    }

    /**
     * 得到互联网接入订单编号
     *
     * @return
     */
    public String getInternetAccessOrderNo(){
        String yyyyMMdd= LocalDate.now().format(SnowConstants.DATE_ONLY_NUMBER_FORMATTER);
        String schemeCodePrefix = String.format("%s",yyyyMMdd);
        return getCustomNo(schemeCodePrefix, getIncrementId(SnowConstants.HASH_FIELD_INTERNET_ACCESS_ORDER,1), SnowConstants.INCREMENT_ID_MAX_LENGTH);
    }

    /**
     * 得到应用加速订单编号
     *
     * @return
     */
    public String getApplicationSpeedUpOrderNo(){
        String yyyyMMdd= LocalDate.now().format(SnowConstants.DATE_ONLY_NUMBER_FORMATTER);
        String schemeCodePrefix = String.format("%s",yyyyMMdd);
        return getCustomNo(schemeCodePrefix, getIncrementId(SnowConstants.HASH_FIELD_APPLICATION_SPEED_UP_ORDER,1), SnowConstants.INCREMENT_ID_MAX_LENGTH);
    }

    /**
     * 得到idc防火墙订单编号
     *
     * @return
     */
    public String getIdcFireWallOrderNo(){
        String yyyyMMdd= LocalDate.now().format(SnowConstants.DATE_ONLY_NUMBER_FORMATTER);
        String schemeCodePrefix = String.format("%s",yyyyMMdd);
        return getCustomNo(schemeCodePrefix, getIncrementId(SnowConstants.HASH_FIELD_IDC_FIREWALL_ORDER,1), SnowConstants.INCREMENT_ID_MAX_LENGTH);
    }

    /**
     * 得到自增id
     *
     * @param hashFiledName 字段名称
     * @return
     */
    private long getIncrementId(String hashFiledName, double delta) {
        String today = LocalDate.now().format(SnowConstants.DATE_WITH_HYPHEN_FORMATTER);
        String hashKey = SnowConstants.INCREMENT_ID_HASH_KEY + today;
        if (!redisTemplate.opsForHash().hasKey(hashKey, hashFiledName)) {
            redisTemplate.opsForHash().put(hashKey, hashFiledName, 0);
        }
        redisTemplate.expire(hashKey, SnowConstants.INCREMENT_ID_HASH_KEY_EXPIRE_SECONDS, TimeUnit.SECONDS);
        return BigDecimal.valueOf(redisTemplate.opsForHash().increment(hashKey, hashFiledName, delta)).longValue();
    }

    /**
     * 得到序号
     *
     * @param prefix 前缀
     * @param number 数
     * @param length 生成序号长度
     * @return
     */
    private String getCustomNo(String prefix, long number, int length) {
        int maxLen = 0;
        while (maxLen <= length) {
            if (number < Math.pow(10, maxLen)) {
                break;
            }
            maxLen++;
        }
        int prefix0len = length - maxLen;
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        for (int i = 0; i < prefix0len; i++) {
            sb.append("0");
        }
        sb.append(number);
        return sb.toString();
    }
}

