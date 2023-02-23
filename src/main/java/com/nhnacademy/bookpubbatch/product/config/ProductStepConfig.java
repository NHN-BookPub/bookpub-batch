package com.nhnacademy.bookpubbatch.product.config;

import com.nhnacademy.bookpubbatch.listener.LoggingListener;
import com.nhnacademy.bookpubbatch.product.reader.ProductReader;
import com.nhnacademy.bookpubbatch.product.writer.ProductWriter;
import com.nhnacademy.bookpubbatch.repository.orderproduct.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DeadlockLoserDataAccessException;

/**
 * 상품을 베스트셀러로 만듭니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Configuration
@RequiredArgsConstructor
public class ProductStepConfig {
    private final StepBuilderFactory stepBuilderFactory;
    private final LoggingListener loggingListener;
    private final ProductReader reader;
    private final ProductWriter writer;
    private static final Integer CHUNK_SIZE = 10;

    /**
     * 상품을 베스트셀러로 변경합니다.
     *
     * @return the step
     */
    @JobScope
    @Bean
    public Step productBestSeller(){
        return stepBuilderFactory.get("판매부수를 확인하여 상품을 베스트셀러로 변경")
                .<ProductDto,ProductDto>chunk(CHUNK_SIZE)
                .reader(reader.bestSellerInfo())
                .writer(writer.bestsellerUpdateWriter())
                .faultTolerant()
                .retryLimit(3)
                .retry(DeadlockLoserDataAccessException.class)
                .listener(loggingListener)
                .build();

    }
}
