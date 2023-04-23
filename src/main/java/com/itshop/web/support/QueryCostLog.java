package com.itshop.web.support;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;

import java.sql.Statement;
import java.util.Properties;

/**
 * 打印sql执行时长
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class})})
public class QueryCostLog implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long start = System.currentTimeMillis();
        Object o = invocation.proceed();
        long end = System.currentTimeMillis();
        StatementHandler sh = (StatementHandler)invocation.getTarget();
        MetaObject stmtHandlerMo = SystemMetaObject.forObject(sh);
        MappedStatement ms = (MappedStatement)stmtHandlerMo.getValue("h.target.delegate.mappedStatement");
        log.info("sql:{} query cost:{}ms", ms.getId(), (end - start));
        return  o;
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}

