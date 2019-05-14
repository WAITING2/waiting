package com.xing.paper.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * @author xiumin.lu
 * @create 2018-9-20
 **/
@Configuration("dataSourceConfig")
public class DataSourceConfig {
    @Value("${spring.primary.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.primary.datasource.driver-class-name}")
    private String driverClass;

    @Value("${spring.primary.datasource.username}")
    private String username;

    @Value("${spring.primary.datasource.password}")
    private String password;

    @Value("${spring.primary.datasource.initialPoolSize}")
    private int initialPoolSize;

    @Value("${spring.primary.datasource.minPoolSize}")
    private int minPoolSize;

    @Value("${spring.primary.datasource.maxPoolSize}")
    private int maxPoolSize;

    @Value("${spring.primary.datasource.maxIdleTime}")
    private int maxIdleTime;

    @Value("${spring.primary.datasource.acquireIncrement}")
    private int acquireIncrement;

    @Value("${spring.primary.datasource.maxStatements}")
    private int maxStatements;

    @Value("${spring.primary.datasource.breakAfterAcquireFailure}")
    private boolean breakAfterAcquireFailure;

    @Value("${spring.primary.datasource.testConnectionOnCheckin}")
    private boolean testConnectionOnCheckin;

    @Value("${spring.primary.datasource.testConnectionOnCheckout}")
    private boolean testConnectionOnCheckout;

    @Value("${spring.primary.datasource.idleConnectionTestPeriod}")
    private int idleConnectionTestPeriod;

    @Value("${spring.primary.datasource.acquireRetryAttempts}")
    private int acquireRetryAttempts;

    @Value("${spring.primary.datasource.acquireRetryDelay}")
    private int acquireRetryDelay;

    @Value("${spring.primary.datasource.checkoutTimeout}")
    private int checkoutTimeout;

    //@Primary
    @Bean(name = "dataSource")
    public DataSource dataSource(){
        ComboPooledDataSource ds = new ComboPooledDataSource();
        try {
            ds.setDriverClass(driverClass);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        ds.setJdbcUrl(jdbcUrl);
        ds.setUser(username);
        ds.setPassword(password);
        ds.setInitialPoolSize(initialPoolSize);
        ds.setMinPoolSize(minPoolSize);
        ds.setMaxPoolSize(maxPoolSize);
        ds.setAcquireIncrement(acquireIncrement);
        ds.setMaxStatements(maxStatements);
        ds.setBreakAfterAcquireFailure(breakAfterAcquireFailure);
        ds.setTestConnectionOnCheckin(testConnectionOnCheckin);
        ds.setTestConnectionOnCheckout(testConnectionOnCheckout);
        ds.setIdleConnectionTestPeriod(idleConnectionTestPeriod);
        ds.setAcquireRetryAttempts(acquireRetryAttempts);
        ds.setAcquireRetryDelay(acquireRetryDelay);
        ds.setCheckoutTimeout(checkoutTimeout);
        return ds;
    }
}
