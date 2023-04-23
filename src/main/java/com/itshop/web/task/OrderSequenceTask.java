package com.itshop.web.task;

import com.itshop.web.annotation.RedisLock;
import com.itshop.web.constants.SnowConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class OrderSequenceTask implements Task {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 每晚23:55分生成明日自增id
     */
    @RedisLock
    @Scheduled(cron = "0 55 23 * * ?")
    @Override
    public void process() {
        String tomorrow = LocalDate.now().plusDays(1).format(SnowConstants.DATE_WITH_HYPHEN_FORMATTER);
        String hashKey = SnowConstants.INCREMENT_ID_HASH_KEY + tomorrow;
        redisTemplate.opsForHash().put(hashKey, SnowConstants.HASH_FIELD_BUSINESS_ID, 0);
        redisTemplate.opsForHash().put(hashKey, SnowConstants.HASH_FIELD_INTERNET_ACCESS_ORDER, 0);
        redisTemplate.opsForHash().put(hashKey, SnowConstants.HASH_FIELD_APPLICATION_SPEED_UP_ORDER, 0);
        redisTemplate.opsForHash().put(hashKey, SnowConstants.HASH_FIELD_IDC_FIREWALL_ORDER, 0);
        redisTemplate.expire(hashKey, SnowConstants.INCREMENT_ID_HASH_KEY_EXPIRE_SECONDS, TimeUnit.SECONDS);
    }
}
