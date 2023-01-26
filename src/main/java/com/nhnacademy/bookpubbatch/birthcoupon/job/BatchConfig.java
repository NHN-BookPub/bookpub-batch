package com.nhnacademy.bookpubbatch.birthcoupon.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Batch Config.
 *
 * @author : 김서현
 * @since : 1.0
 **/

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final Step birthCouponStep;

    /**
     * 배치를 시작하는 Job 입니다.
     *
     * @return Job.
     */
    @Bean
    public Job BirthCouponJob() {
        return jobBuilderFactory.get("BirthCouponJob")
                .start(birthCouponStep)
                .build();
    }

}
