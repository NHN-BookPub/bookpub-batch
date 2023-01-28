package com.nhnacademy.bookpubbatch.coupon.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Batch Config.
 *
 * @author : 김서현
 * @since : 1.0
 **/

@Configuration
@RequiredArgsConstructor
public class CouponBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final Step sendBirthCouponStep;

    /**
     * 배치를 실행 하기 위한 BirthCouponJob 입니다.
     *
     * @return Job.
     */
    @Bean
    public Job birthCouponJob() {
        return jobBuilderFactory.get("birthCouponJob")
                .start(sendBirthCouponStep)
                .build();
    }

}
