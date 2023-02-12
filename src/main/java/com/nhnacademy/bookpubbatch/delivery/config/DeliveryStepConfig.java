package com.nhnacademy.bookpubbatch.delivery.config;

import com.nhnacademy.bookpubbatch.delivery.processor.DeliveryUpdateProcessor;
import com.nhnacademy.bookpubbatch.delivery.reader.DeliveryReader;
import com.nhnacademy.bookpubbatch.delivery.writer.DeliveryStateWriter;
import com.nhnacademy.bookpubbatch.delivery.writer.DeliveryWriter;
import com.nhnacademy.bookpubbatch.listener.LoggingListener;
import com.nhnacademy.bookpubbatch.repository.delivery.dto.DeliveryResponseDto;
import com.nhnacademy.bookpubbatch.repository.delivery.dto.DeliveryShippingResponseDto;
import com.nhnacademy.bookpubbatch.repository.delivery.dto.DeliveryStateDto;
import com.nhnacademy.bookpubbatch.repository.delivery.dto.DeliveryUpdateDto;
import com.nhnacademy.bookpubbatch.repository.order.dto.OrderDto;
import com.nhnacademy.bookpubbatch.repository.orderproduct.dto.OrderProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DeadlockLoserDataAccessException;

/**
 * 배송관련 Step 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Configuration
@RequiredArgsConstructor
public class DeliveryStepConfig {
    private final StepBuilderFactory stepBuilderFactory;
    private final DeliveryReader reader;
    private final DeliveryWriter writer;
    private final DeliveryStateWriter stateWriter;
    private final DeliveryUpdateProcessor processor;
    private final LoggingListener loggingListener;
    private static final Integer CHUNK_SIZE = 10;

    /**
     * 배송 상태정보를 context 에 저장
     * ShopDB 상태가져오기
     *
     * @return the step
     */
    @JobScope
    @Bean
    public Step deliveryStateInfo() {
        return stepBuilderFactory.get("배송 상태정보가져오기")
                .<DeliveryStateDto, DeliveryStateDto>chunk(1)
                .reader(reader.deliveryStateReader())
                .writer(stateWriter)
                .faultTolerant()
                .retryLimit(3)
                .retry(DeadlockLoserDataAccessException.class)
                .listener(deliveryListener())
                .listener(loggingListener)
                .build();
    }

    /**
     * 옥천허브로 가는 배송지역추가
     * DeliveryDB 의 delivery table -> delivery location 에 값추가
     * DeliveryDB
     *
     * @return the step
     */
    @JobScope
    @Bean
    public Step deliveryLocationCreate() {
        return stepBuilderFactory.get("배송 지역 추가")
                .<DeliveryResponseDto, DeliveryResponseDto>chunk(CHUNK_SIZE)
                .reader(reader.deliveryStateReadyReader())
                .writer(writer.insertDeliveryLocationStateReady())
                .faultTolerant()
                .retryLimit(3)
                .retry(DeadlockLoserDataAccessException.class)
                .listener(loggingListener)
                .build();
    }

    /**
     * 배송준비 -> 배송중으로 변경
     * DeliveryDb 의 delivery table 의 상태값변경
     *
     * @return the step
     */
    @JobScope
    @Bean
    public Step deliveryLocationUpdate() {
        return stepBuilderFactory.get("배송 : 배송준비 -> 배송중으로 변경")
                .<DeliveryResponseDto, DeliveryUpdateDto>chunk(CHUNK_SIZE)
                .reader(reader.deliveryStateReadyReader())
                .processor(processor)
                .writer(writer.updateDeliveryState())
                .faultTolerant()
                .retryLimit(3)
                .retry(DeadlockLoserDataAccessException.class)
                .listener(loggingListener)
                .build();
    }

    /**
     * 주문의 배송준비
     * ShopDb 의 order 테이블의 상태를 배송대기 -> 배송중으로 변경
     *
     * @return the step
     */
    @Bean
    public Step orderStateShippingUpdate() {
        return stepBuilderFactory.get("배송시 : 배송대기 -> 배송중으로 변경")
                .<OrderDto, OrderDto>chunk(CHUNK_SIZE)
                .reader(reader.deliveryOrderStateReadyReader())
                .writer(writer.updateOrderDeliveryReadyToShipping())
                .faultTolerant()
                .retryLimit(3)
                .retry(DeadlockLoserDataAccessException.class)
                .listener(loggingListener)
                .build();

    }

    /**
     * 배송중인 주문의 정보를 배송완료로 변경하는 메서드입니다.
     * ShopDb 의 order 테이블의 상태를 배송중 -> 배송완료 로 변경
     *
     * @return the step
     */
    @Bean
    public Step orderStateDoneUpdate(){
        return stepBuilderFactory.get("배송중 : 배송중 -> 배송완료")
                .<OrderDto, OrderDto>chunk(CHUNK_SIZE)
                .reader(reader.deliveryOrderStateDoneReader())
                .writer(writer.updateOrderDeliveryShippingToDone())
                .faultTolerant()
                .retryLimit(3)
                .retry(DeadlockLoserDataAccessException.class)
                .listener(loggingListener)
                .build();
    }

    /**
     * 배송중인 상품의 마지막 도착지 도착완료.
     * DeliveryDB 의 delivery-location table 의 도착지 추가
     *
     * @return the step
     */
    @JobScope
    @Bean
    public Step deliveryStateEnd() {
        return stepBuilderFactory.get("배송 지역 배송완료")
                .<DeliveryShippingResponseDto, DeliveryShippingResponseDto>chunk(CHUNK_SIZE)
                .reader(reader.deliveryStateShippingReader())
                .writer(writer.insertDeliveryLocationStateShipping())
                .faultTolerant()
                .retryLimit(3)
                .retry(DeadlockLoserDataAccessException.class)
                .listener(loggingListener)
                .build();
    }

    /**
     * 배송중-> 배송완료로 변경
     * DeliveryDb 의 delivery table 의 배송중 -> 배송완료기 변경
     *
     * @return the step
     */
    @JobScope
    @Bean
    public Step deliveryStateEndUpdate() {
        return stepBuilderFactory.get("배송 배송완료로 변경")
                .<DeliveryShippingResponseDto, DeliveryShippingResponseDto>chunk(CHUNK_SIZE)
                .reader(reader.deliveryStateShippingReader())
                .writer(writer.updateDeliveryStateEnd())
                .faultTolerant()
                .retryLimit(3)
                .retry(DeadlockLoserDataAccessException.class)
                .listener(loggingListener)
                .build();
    }

    /**
     * 주문상품 : 상태값 배송준비 -> 배송중으로 변경
     *
     * @return the step
     */
    @JobScope
    @Bean
    public Step orderProductStateShippingUpdate(){
        return stepBuilderFactory.get("배송준비 -> 배송중으로 변경")
                .<OrderProductDto, OrderProductDto>chunk(CHUNK_SIZE)
                .reader(reader.deliveryOrderProductStateReadyReader())
                .writer(writer.updateOrderProductToShipping())
                .faultTolerant()
                .retryLimit(3)
                .retry(DeadlockLoserDataAccessException.class)
                .listener(loggingListener)
                .build();
    }

    /**
     * 주문상품 : 상태값 배송중 -> 구매확정대기 로 변경
     *
     * @return the step
     */
    @JobScope
    @Bean
    public Step orderProductStateWaitingPurchase(){
        return stepBuilderFactory.get("배송중 -> 구매확정 대기로 변경")
                .<OrderProductDto, OrderProductDto>chunk(CHUNK_SIZE)
                .reader(reader.deliveryOrderProductStateShipping())
                .writer(writer.updateOrderProductToWaitingPurchase())
                .faultTolerant()
                .retryLimit(3)
                .retry(DeadlockLoserDataAccessException.class)
                .listener(loggingListener)
                .build();
    }

    /**
     * 주문상품 : 상태값 구매확정대기 -> 구매확정
     *
     * @return the step
     */
    @JobScope
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
     * 데이터 공유를 위한 Listener
     *
     * @return 데이터 공유에 필요한 key 값을 가진 ExecutionContextPromotionListener 가 반환 됩니다.
     */
    @JobScope
    @Bean
    public ExecutionContextPromotionListener deliveryListener() {
        ExecutionContextPromotionListener executionContextPromotionListener =
                new ExecutionContextPromotionListener();
        executionContextPromotionListener.setKeys(new String[]{"deliveryState"});
        return executionContextPromotionListener;
    }
}
