package com.nhnacademy.bookpubbatch.ordercancel.config;

import com.nhnacademy.bookpubbatch.listener.LoggingListener;
import com.nhnacademy.bookpubbatch.ordercancel.processor.CouponUpdateProcessor;
import com.nhnacademy.bookpubbatch.ordercancel.processor.OrderUpdateProcessor;
import com.nhnacademy.bookpubbatch.ordercancel.reader.OrderCancelReader;
import com.nhnacademy.bookpubbatch.ordercancel.writer.OrderCancelExecutionWriter;
import com.nhnacademy.bookpubbatch.ordercancel.writer.OrderCancelWriter;
import com.nhnacademy.bookpubbatch.repository.coupon.dto.CouponDto;
import com.nhnacademy.bookpubbatch.repository.coupon.dto.CouponUpdateDto;
import com.nhnacademy.bookpubbatch.repository.order.dto.OrderDto;
import com.nhnacademy.bookpubbatch.repository.order.dto.OrderStateDto;
import com.nhnacademy.bookpubbatch.repository.order.dto.OrderUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 결제대기 상태값들이 주문취소로 변경되었을때 일어나는 상황에대한 Step Config 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OrderCancelStepConfig {
    private final StepBuilderFactory stepBuilderFactory;
    private final OrderCancelReader reader;
    private final OrderCancelWriter writer;
    private final OrderCancelExecutionWriter executionWriter;
    private final OrderUpdateProcessor orderUpdateProcessor;
    private final CouponUpdateProcessor couponUpdateProcessor;
    private final LoggingListener loggingListener;
    private static final Integer CHUNK_SIZE = 10;

    /**
     * 주문정보를 받아오는 STEP
     *
     * @return the step
     */
    @JobScope
    @Bean
    public Step orderStateInfo(){
        return stepBuilderFactory.get("결제대기상태 상태값 조회")
                .<OrderStateDto, OrderStateDto>chunk(CHUNK_SIZE)
                .reader(reader.getOrderStateReader())
                .listener(loggingListener)
                .writer(executionWriter)
                .listener(loggingListener)
                .listener(orderCancelListener())
                .build();
    }

    /**
     * 주문에관한 정보를 update 하는 Step
     *
     * @return the step
     */
    @JobScope
    @Bean
    public Step orderUpdate(){
        return stepBuilderFactory.get("결제대기 상태 변경")
                .<OrderDto, OrderUpdateDto>chunk(CHUNK_SIZE)
                .reader(reader.getOrderReader())
                .listener(loggingListener)
                .processor(orderUpdateProcessor)
                .listener(loggingListener)
                .writer(writer.updateOrderWriter())
                .listener(loggingListener)
                .build();
    }

    /**
     * 쿠폰을 사용한 쿠폰에서 사용가능으로 변경하는 STEP
     *
     * @return the step
     */
    @JobScope
    @Bean
    public Step usedCouponChange(){
        return stepBuilderFactory.get("사용한쿠폰 사용가능으로 변경")
                .<CouponDto,CouponUpdateDto>chunk(CHUNK_SIZE)
                .reader(reader.getCouponReader())
                .listener(loggingListener)
                .processor(couponUpdateProcessor)
                .listener(loggingListener)
                .writer(writer.updateCouponWriter())
                .listener(loggingListener)
                .build();
    }

    /**
     * Exceution context 를 사용하기위해서 사용하는 listener
     *
     * @return the execution context promotion listener
     */
    @JobScope
    @Bean
    public ExecutionContextPromotionListener orderCancelListener(){
        ExecutionContextPromotionListener executionContextPromotionListener =
                new ExecutionContextPromotionListener();
        executionContextPromotionListener.setKeys(new String[]{"orderState"});
        return executionContextPromotionListener;
    }

}
