package com.nhnacademy.bookpubbatch.purchaseconfirmation.config;

import com.nhnacademy.bookpubbatch.listener.LoggingListener;
import com.nhnacademy.bookpubbatch.purchaseconfirmation.reader.PurchaseReader;
import com.nhnacademy.bookpubbatch.purchaseconfirmation.writer.PurchaseWriter;
import com.nhnacademy.bookpubbatch.repository.order.dto.OrderPointDto;
import com.nhnacademy.bookpubbatch.repository.orderproduct.dto.OrderProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DeadlockLoserDataAccessException;

/**
 * 멤버 등급 승급 관련 Step Config 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Slf4j
@Configuration
@RequiredArgsConstructor
public class PurchaseConfirmationStepConfig {
    private final StepBuilderFactory stepBuilderFactory;
    private final PurchaseReader reader;
    private final PurchaseWriter writer;
    private final LoggingListener loggingListener;
    private static final Integer CHUNK_SIZE = 10;

    /**
     * 포인트 내역을 추가하는 메서드입니다.
     *
     * @return the step
     */
    @Bean
    public Step pointHistoryAdd(){
        return stepBuilderFactory.get("회원의 포인트 내역에 추가")
                .<OrderPointDto, OrderPointDto>chunk(CHUNK_SIZE)
                .reader(reader.getOrderPointReader())
                .faultTolerant()
                .retry(DeadlockLoserDataAccessException.class)
                .retryLimit(3)
                .writer(writer.insertPoint())
                .listener(loggingListener)
                .build();
    }

    /**
     * 회원의 포인트를 업데이트하는 Step 입니다.
     *
     * @return the step
     */
    @Bean
    public Step memberPointUpdate(){
        return stepBuilderFactory.get("회원의 포인트 업데이트")
                .<OrderPointDto, OrderPointDto>chunk(CHUNK_SIZE)
                .reader(reader.getOrderPointReader())
                .faultTolerant()
                .retry(DeadlockLoserDataAccessException.class)
                .retryLimit(3)
                .writer(writer.updateMemberPoint())
                .listener(loggingListener)
                .build();
    }

    /**
     * 주문상품 : 상태값 구매확정대기 -> 구매확정
     *
     * @return the step
     */
    @Bean
    public Step orderProductPurchaseConfirmation(){
        return stepBuilderFactory.get("구매확정대기 + 7일 -> 구매확정으로 변경")
                .<OrderProductDto, OrderProductDto>chunk(CHUNK_SIZE)
                .reader(reader.orderProductStateWaitingReader())
                .writer(writer.updateOrderProductToPurchaseConfirmation())
                .faultTolerant()
                .retryLimit(3)
                .retry(DeadlockLoserDataAccessException.class)
                .listener(loggingListener)
                .build();
    }


    /**
     * 주문 : 상태값 배송완료? -> 구매확정
     *
     * @return the step
     */
    @Bean
    public Step orderPurchaseConfirmation() {
        return stepBuilderFactory.get("주문 배송완료 + 7일 -> 구매확정을로 변경")
                .<OrderPointDto, OrderPointDto>chunk(CHUNK_SIZE)
                .reader(reader.getOrderDoneReader())
                .faultTolerant()
                .retryLimit(3)
                .retry(DeadlockLoserDataAccessException.class)
                .writer(writer.updateOrderPurchaseConfirmation())
                .listener(loggingListener)
                .build();

    }
}
