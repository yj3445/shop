package com.itshop.web.task;

import com.itshop.web.annotation.RedisLock;
import com.itshop.web.constants.SnowConstants;
import com.itshop.web.manager.StatementBillManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * 月结算单任务
 */
@Slf4j
@Component
public class MonthlyStatementTask implements Task {

    @Autowired
    StatementBillManager statementBillManager;

    /**
     * 每月的1日的凌晨2点执行任务
     */
    @RedisLock
    @Scheduled(cron = "0 0 2 1 * ?")
    @Override
    public void process() {
        String lastMonth = LocalDate.now().plusMonths(-1).format(SnowConstants.YYYYMM);
        statementBillManager.SaveInternetAccessOrderStatement(lastMonth);
    }
}
