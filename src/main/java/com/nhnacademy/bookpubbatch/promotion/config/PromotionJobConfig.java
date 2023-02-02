package com.nhnacademy.bookpubbatch.promotion.config;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 등급을 올리기위한 Job 정보들이 들어있는 Config 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Configuration
@RequiredArgsConstructor
public class PromotionJobConfig {
    private final JobBuilderFactory jobBuilderFactory;

    private final PromotionStepConfig promotionStepConfig;

    @Bean
    public Job promotionJob(){
        return jobBuilderFactory.get("promotion" + LocalDateTime.now())
                .start(promotionStepConfig.promotionTierInfo())
                .next(promotionStepConfig.promotionMemberInfo())
                .build();
    }
}
