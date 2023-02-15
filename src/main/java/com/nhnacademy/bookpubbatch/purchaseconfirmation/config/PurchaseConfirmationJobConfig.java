package com.nhnacademy.bookpubbatch.purchaseconfirmation.config;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 구매확정으로 변경하는 Job 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Configuration
@RequiredArgsConstructor
public class PurchaseConfirmationJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final PurchaseConfirmationStepConfig stepConfig;

    /**
     * 주문이 배송완료나 구매확정일때 (경과시간 +7일) 주문상품을 구매확정으로 변경.
     * 1. 포인트내역에 회원이 적용할포인트 추가
     * 2. 회원의 포인트 업데이트
     * 3. 해당 주문상품의 상태를 구매확정으로 변경
     * 4. 해당 주문의 상태를 구매확정으로 변경
     *
     * @return the job
     */
    @Bean
    public Job purchaseConfirmation(){
        return jobBuilderFactory.get("purchase-confirmation" + LocalDateTime.now())
                .start(stepConfig.pointHistoryAdd())
                .next(stepConfig.memberPointUpdate())
                .next(stepConfig.orderProductPurchaseConfirmation())
                .next(stepConfig.orderPurchaseConfirmation())
                .build();
    }

}
