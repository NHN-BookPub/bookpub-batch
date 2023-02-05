package com.nhnacademy.bookpubbatch.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 데이터소스의 Config.
 *
 * @author : 유호철
 * @since : 1.0
**/
@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

    private final BatchPropertiesConfig batchProperties;
    private final ShopPropertiesConfig shopProperties;
    private final DeliveryPropertiesConfig deliveryProperties;

    /**
     * spring batch에 대한 데이터소스입니다.
     *
     * @return the data source
     */
    @Primary
    @Bean("batchDb")
    DataSource springBatchDb() {
        return batchProperties.batchDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    /**
     * api 서버에대한 데이터소스입니다.
     *
     * @return the data source
     */
    @Bean("shopDb")
    DataSource shopDb() {
        return shopProperties.shopDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean("deliveryDb")
    DataSource deliveryDb(){
        return deliveryProperties.deliveryProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }
}
