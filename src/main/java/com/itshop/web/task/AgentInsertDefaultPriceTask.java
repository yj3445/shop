package com.itshop.web.task;

import com.itshop.web.annotation.RedisLock;
import com.itshop.web.manager.AgentPriceManager;
import com.itshop.web.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 代理商新增默认价格序列（客户公司价格）
 */
@Slf4j
@Component
public class AgentInsertDefaultPriceTask implements Task {

    @Autowired
    AgentPriceManager agentPriceManager;

    /**
     * 每隔2小时执行一次
     */
    @RedisLock
    @Scheduled(cron = "0 0 0/2 * * ?")
    @Override
    public void process() {
        Date endTime = DateUtil.localDateTime2Date(LocalDateTime.now());
        Date startTime = DateUtil.localDateTime2Date(LocalDateTime.now().plusHours(-12));
        agentPriceManager.agentCompanyInsertDefaultPrice(startTime, endTime);
    }
}
