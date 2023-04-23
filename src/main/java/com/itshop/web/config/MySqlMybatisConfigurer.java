package com.itshop.web.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.github.pagehelper.PageInterceptor;
import com.itshop.web.support.QueryCostLog;
import com.itshop.web.support.SQLOutInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * mysql mybatis 配置
 *
 * @author liufenglong
 * @date 2020/5/25
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.itshop.web.dao.mysql"}, sqlSessionFactoryRef = "mysqlSqlSessionFactoryBean")
public class MySqlMybatisConfigurer {
    @Bean(name="mysqlDataSource")
    @ConfigurationProperties(prefix = "mysql.datasource")
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "mysqlTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = "mysqlSqlSessionFactoryBean")
    public SqlSessionFactory sqlSessionFactoryBean(@Qualifier("mysqlDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        //设置pagehelper切换为hsqldb
        Interceptor interceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments","true");
        properties.setProperty("params","count=countSql;");
        properties.setProperty("helperDialect", "hsqldb");
        interceptor.setProperties(properties);
        //加入完整sql输出log
        factory.setPlugins(new Interceptor[]{new SQLOutInterceptor(), new QueryCostLog(),interceptor});
        factory.setDataSource(dataSource);
        factory.setTypeAliasesPackage("com.itshop.web.dao.po");
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factory.setMapperLocations(resolver.getResources("classpath:mapper/mysql/*.xml"));
        return factory.getObject();
    }
}

