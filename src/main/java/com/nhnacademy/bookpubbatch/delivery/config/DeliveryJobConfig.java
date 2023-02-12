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
     * 1. 배송중이 배송상태 받기
     * 2. 배송지역 테이블에 옥천허브인 값 추가
     * 3. 배송의 배송준비-> 배송중으로 변경
     * 4. 주문의 배송준비 -> 배송중으로 변경
     * 5. 주문상품의 배송준비 -> 배송중으로 변경
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
                .next(deliveryStepConfig.orderProductStateShippingUpdate())
                .build();
    }

    /**
     * 1. 배송지역 테이블에 도착지역 값 추가
     * 2. 배송의 배송중 -> 배송완료로 변경
     * 3. 주문의 배송중 -> 배송완료로 변경
     * 4. 주문상품의 배송중 -> 구매확정 대기 상태로 변경
     *
     * @return the job
     */
    @Bean
    public Job deliveryEnd(){
        return jobBuilderFactory.get("delvieryEnd" + LocalDateTime.now())
                .start(deliveryStepConfig.deliveryStateEnd())
                .next(deliveryStepConfig.deliveryStateEndUpdate())
                .next(deliveryStepConfig.orderStateDoneUpdate())
                .next(deliveryStepConfig.orderProductStateWaitingPurchase())
                .build();
    }

    /**
     * 1. 주문상품 -> 구매확정대기 -> 구매확정으로 변경
     *
     * @return the job
     */
    @Bean
    public Job purchaseWaitingToDone(){
        return jobBuilderFactory.get("purchaseWaitingToDone" + LocalDateTime.now())
                .start(deliveryStepConfig.orderProductPurchaseConfirmation())
                .build();
    }

}
