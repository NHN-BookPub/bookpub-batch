package com.nhnacademy.bookpubbatch.config;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 데이터소스의 Config
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {
    private final BatchPropertiesConfig batchProperties;
    private final ShopPropertiesConfig shopProperties;

    /**
     * spring batch에 대한 데이터소스입니다.
     *
     * @return the data source
     */
    @Bean
    @Primary
    DataSource springBatchDb() {
        return batchProperties.batchDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    /**
     * api 서버에대한 데이터소스입니다.
     *
     * @return the data source
     */
    @Bean
    DataSource shopDb() {
        return shopProperties.shopDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    /**
     * 배치에대한 Tx manager 입니다.
     *
     * @return the platform transaction manager
     */
    @Bean
    public PlatformTransactionManager batchTransactionManager() {
        return new DataSourceTransactionManager(springBatchDb());
    }

    /**
     * Api 서버에대한 Tx Manager 입니다.
     *
     * @return the platform transaction manager
     */
    @Bean
    public PlatformTransactionManager shopTransactionManager() {
        return new DataSourceTransactionManager(shopDb());
    }
}
