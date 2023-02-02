package com.nhnacademy.bookpubbatch.ordercancel.config;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 주문취소와 관련있는 Job Config 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Configuration
@RequiredArgsConstructor
public class OrderCancelJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final OrderCancelStepConfig orderCancelStep;


    /**
     * 주문생성일시를 비교해 하루가 넘어갔을경우 실행합니다.
     * 1. '결제대기' 상태의 주문에관한 쿠폰을 반환
     * 2. '주문상태' 정보를 조회
     * 3.  조회된 주문상태를 통해 주문의 상태값 주문취소로 변경
     *
     * @return the job
     */
    @Bean
    public Job orderCancel(){
        return jobBuilderFactory.get("orderCancel-test" + LocalDateTime.now())
                .start(orderCancelStep.usedCouponChange())
                .next(orderCancelStep.orderStateInfo())
                .next(orderCancelStep.orderUpdate())
                .build();
    }
}
