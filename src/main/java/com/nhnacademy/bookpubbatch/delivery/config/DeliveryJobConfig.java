package com.nhnacademy.bookpubbatch.delivery.config;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 배송관련된 Job 설정입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Configuration
@RequiredArgsConstructor
public class DeliveryJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final DeliveryStepConfig deliveryStepConfig;

    /**
     * 배송준비 -> 배송중으로 변경
     * 배송위치 -> 옥천허브 추가
     *
     * @return the job
     */
    @Bean
    public Job deliveryLocation(){
        return jobBuilderFactory.get("deliveryLocation"+ LocalDateTime.now())
                .start(deliveryStepConfig.deliveryStateInfo())
                .next(deliveryStepConfig.deliveryLocationCreate())
                .next(deliveryStepConfig.deliveryLocationUpdate())
                .next(deliveryStepConfig.orderStateShippingUpdate())
                .build();
    }

    /**
     * 배송중 -> 배송완료 변경
     * 배송위치 -> 요청한 위치로 도착
     *
     * @return the job
     */
    @Bean
    public Job deliveryEnd(){
        return jobBuilderFactory.get("delvieryEnd" + LocalDateTime.now())
                .start(deliveryStepConfig.deliveryStateEnd())
                .next(deliveryStepConfig.deliveryStateEndUpdate())
                .next(deliveryStepConfig.orderStateDoneUpdate())
                .build();
    }

}
