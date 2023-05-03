package com.itshop.web.support;


import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.MDC;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 记录SQL
 *
 * @author liufenglong
 * @date 2020/5/25
 */
@Log4j2
@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class,Integer.class})})
public class SQLOutInterceptor implements Interceptor {

    public SQLOutInterceptor() {
    }

    private static ThreadLocal<SimpleDateFormat> dateTimeFormatter = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    private final static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,20,60,TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(1024));

    public final static ThreadLocal<Boolean> needReduction = new ThreadLocal<Boolean>();
    // map中用表名作为key,不能降为的字段用comma分割
    public final static ThreadLocal<List<Map<String, String>>> reductionMap = new ThreadLocal<List<Map<String, String>>>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        dimensionReduction(invocation);
        threadPoolExecutor.execute(new FullSQlRunnable(invocation, MDC.get("traceId"),MDC.get("currentUserName"),MDC.get("reportName")));

        return invocation.proceed();
    }

    /**
     * <br>描 述: 增加对SQL的解析，然后进行表的修改。
     *           解析的方法：1、判断sql参数中needReduction，如果为true的场合，是需要降维的。
     *                     2、承1，解析Sql中是否包含配置的降维表，是可以降维的。
     *                     3、承2，判断Sql中是否包含不能降维的字段，不包含是可以降维。修改Sql中可以降为表，表名增加_reduction
     *                     4、承3，全部修改完后，替换Sql
     *
     *
     * @param invocation
     */
    private void dimensionReduction(Invocation invocation) {
        if (needReduction.get() == null || !needReduction.get()) {
            return;
        }
        if (reductionMap.get() == null || reductionMap.get().isEmpty()) {
            return;
        }
        boolean reductionFlag = false;
        StatementHandler stmtHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStmtHandler = SystemMetaObject.forObject(stmtHandler);
        BoundSql boundSql = (BoundSql) metaStmtHandler.getValue("delegate.boundSql");
        MappedStatement mappedStatement = (MappedStatement)metaStmtHandler.getValue("delegate.mappedStatement");
        String id = mappedStatement.getId();
        String sql = boundSql.getSql().replaceAll("--.*\n", " ").replace("\n", " ").replace("\r", " ").replaceAll("\\s{2,}", " ");
        Map<String, String> replaceMap = getReplaceTableMap(sql);
        for (String key : replaceMap.keySet()) {
            reductionFlag = true;
            sql = sql.replace(key + " ", replaceMap.get(key) + " ");
            // log.info("降维的SqlId:{},替换表:{}--->{}", id, key, replaceMap.get(key));
        }
        if (reductionFlag) {
            Configuration configuration = (Configuration) metaStmtHandler.getValue("delegate.configuration");
            Object parameterObject = metaStmtHandler.getValue("delegate.boundSql.parameterObject");
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            BoundSql newBoundSql = new BoundSql(configuration, sql, parameterMappings, parameterObject);
            for (ParameterMapping parameterMapping : parameterMappings) {
                if (boundSql.hasAdditionalParameter(parameterMapping.getProperty())) {
                    newBoundSql.setAdditionalParameter(parameterMapping.getProperty(), boundSql.getAdditionalParameter(parameterMapping.getProperty()));
                }
            }
            metaStmtHandler.setValue("delegate.boundSql", newBoundSql);
            // log.info("降维的SqlId:{}完成", id);
        }
    }

    private Map<String, String> getReplaceTableMap(String sql) {
        Map<String, String> retMap = Maps.newHashMap();
        List<Map<String, String>> tableNameReductionList = reductionMap.get();
        if (tableNameReductionList == null || tableNameReductionList.isEmpty()) {
            return retMap;
        }
        for (Map<String, String> reductionInfoMap : tableNameReductionList) {
            String tableName = reductionInfoMap.get("tableName");
            String tableNameReduction = reductionInfoMap.get("tableNameReduction");
            String reductionField = reductionInfoMap.get("reductionField");
            if (sql.contains(" " + tableName + " ") || sql.contains("." + tableName + " ")) {
                // 按优先级进行替换，如果存在优先级高的则跳过
                if (retMap.containsKey(tableName)) {
                    continue;
                }
                // 查看是否能进行降维
                boolean reductionFlag = false;
                String[] unReductionFields = reductionField.split(",");
                for (String unReductionField : unReductionFields) {
                    if (sql.contains(unReductionField.trim())) {
                        reductionFlag = false;
                        break;
                    } else {
                        reductionFlag = true;
                    }
                }
                if (reductionFlag) {
                    retMap.put(tableName, tableNameReduction);
                }
            }
        }
        return retMap;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    class FullSQlRunnable implements Runnable {

        //跟踪id
        private String trackId;
        //用户名
        private String userName;
        //报表名称
        private String reportName;

        private Configuration configuration;
        private TypeHandlerRegistry typeHandlerRegistry;
        private Invocation invocation;

        public FullSQlRunnable(Invocation invocation, String trackId, String userName, String reportName) {
            this.invocation = invocation;
            this.trackId = trackId;
            this.userName = userName;
            this.reportName = reportName;
        }

        @Override
        public void run() {
            StatementHandler stmtHandler = (StatementHandler) invocation.getTarget();
            MetaObject metaStmtHandler = SystemMetaObject.forObject(stmtHandler);
            MappedStatement mappedStatement = (MappedStatement)metaStmtHandler.getValue("delegate.mappedStatement");
            String id = mappedStatement.getId();
            BoundSql boundSql = (BoundSql) metaStmtHandler.getValue("delegate.boundSql");
            String sql = boundSql.getSql();
            List<String> parameters = new ArrayList<String>();
            Object parameterObject = metaStmtHandler.getValue("delegate.boundSql.parameterObject");
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            if (parameterMappings != null) {
                configuration = (Configuration) metaStmtHandler.getValue("delegate.configuration");
                MetaObject metaObject = parameterObject == null ? null : configuration
                        .newMetaObject(parameterObject);
                // 获取类型处理器注册器，类型处理器的功能是进行java类型和数据库类型的转换
                typeHandlerRegistry = configuration.getTypeHandlerRegistry();

                for (int i = 0; i < parameterMappings.size(); i++) {
                    ParameterMapping parameterMapping = parameterMappings.get(i);
                    if (parameterMapping.getMode() != ParameterMode.OUT) {
                        // 参数值
                        Object value;
                        String propertyName = parameterMapping.getProperty();
                        // 获取参数名称
                        if (boundSql.hasAdditionalParameter(propertyName)) {
                            // 获取参数值
                            value = boundSql.getAdditionalParameter(propertyName);
                        } else if (parameterObject == null) {
                            value = null;
                        } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                            // 如果是单个值则直接赋值
                            value = parameterObject;
                        } else {
                            value = metaObject == null ? null : metaObject.getValue(propertyName);
                        }

                        if (value instanceof Number) {
                            parameters.add(String.valueOf(value));
                        } else {
                            StringBuilder builder = new StringBuilder();
                            builder.append("'");
                            if (value instanceof Date) {
                                builder.append(dateTimeFormatter.get().format((Date) value));
                            } else if (value instanceof String) {
                                builder.append(value);
                            }
                            builder.append("'");
                            parameters.add(builder.toString());
                        }
                    }
                }
            }

            Long start = System.currentTimeMillis();
            for (String value : parameters) {
                sql = sql.replaceFirst("\\?", value);
            }
            // log.info("["+trackId+""+"] ["+userName+"]    ==>  " + id + ":\n" + sql.replace("\n", "").replaceAll("\\s{2,}"," "));

        }
    }

}

