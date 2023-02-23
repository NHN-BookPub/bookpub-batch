package com.nhnacademy.bookpubbatch.product.config;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 상품관련된 Job Config 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Configuration
@RequiredArgsConstructor
public class ProductJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final ProductStepConfig productStepConfig;

    /**
     * 금일 날자를 기준으로 30일까지의 판매현황을 집계하여
     * 베스트셀러로 변경합니다.
     *
     * @return the job
     */
    @Bean
    public Job bestSeller(){
        return jobBuilderFactory.get("best-seller" + LocalDateTime.now())
                .start(productStepConfig.productBestSeller())
                .build();
    }
}
