package com.nhnacademy.bookpubbatch.delivery.writer;

import com.nhnacademy.bookpubbatch.repository.delivery.dto.DeliveryResponseDto;
import com.nhnacademy.bookpubbatch.repository.delivery.dto.DeliveryShippingResponseDto;
import com.nhnacademy.bookpubbatch.repository.delivery.dto.DeliveryUpdateDto;
import com.nhnacademy.bookpubbatch.repository.order.dto.OrderDto;
import com.nhnacademy.bookpubbatch.repository.orderproduct.dto.OrderProductDto;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 배송관련 writer 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Configuration
public class DeliveryWriter {
    private final SqlSessionFactory sqlSessionFactory;
    private final SqlSessionFactory shopSessionFactory;

    public DeliveryWriter(@Qualifier("deliverySqlSessionFactory") SqlSessionFactory sqlSessionFactory,
                          @Qualifier("sqlSessionFactory") SqlSessionFactory shopSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.shopSessionFactory = shopSessionFactory;
    }

    /**
     * 배송준비인 배송테이블정보를 가지고 배송위치 생성
     *
     * @return the my batis batch item writer
     */
    @Bean
    public MyBatisBatchItemWriter<DeliveryResponseDto> insertDeliveryLocationStateReady() {
        return new MyBatisBatchItemWriterBuilder<DeliveryResponseDto>()
                .sqlSessionFactory(sqlSessionFactory)
                .statementId("com.nhnacademy.bookpubbatch.repository.delivery.DeliveryMapper.createDeliveryLocation")
                .build();
    }

    /**
     * 배송상태 변경 writer
     *
     * @return the my batis batch item writer
     */
    @Bean
    public MyBatisBatchItemWriter<DeliveryUpdateDto> updateDeliveryState() {
        return new MyBatisBatchItemWriterBuilder<DeliveryUpdateDto>()
                .assertUpdates(false)
                .sqlSessionFactory(sqlSessionFactory)
                .statementId("com.nhnacademy.bookpubbatch.repository.delivery.DeliveryMapper.updateDeliveryState")
                .build();
    }

    /**
     * 마지막으로 location 생성하는 Writer
     *
     * @return the my batis batch item writer
     */
    @Bean
    public MyBatisBatchItemWriter<DeliveryShippingResponseDto> insertDeliveryLocationStateShipping() {
        return new MyBatisBatchItemWriterBuilder<DeliveryShippingResponseDto>()
                .sqlSessionFactory(sqlSessionFactory)
                .statementId("com.nhnacademy.bookpubbatch.repository.delivery.DeliveryMapper.createDeliveryLocationEnd")
                .build();
    }

    /**
     * 배송준비 -> 배송중으로 업데이트 writer
     *
     * @return the my batis batch item writer
     */
    @Bean
    public MyBatisBatchItemWriter<DeliveryShippingResponseDto> updateDeliveryStateEnd() {
        return new MyBatisBatchItemWriterBuilder<DeliveryShippingResponseDto>()
                .sqlSessionFactory(sqlSessionFactory)
                .assertUpdates(false)
                .statementId("com.nhnacademy.bookpubbatch.repository.delivery.DeliveryMapper.updateDeliveryEnd")
                .build();
    }

    /**
     * 주문 : 배송대기 -> 배송중으로 변경
     *
     * @return the my batis batch item writer
     */
    @Bean
    public MyBatisBatchItemWriter<OrderDto> updateOrderDeliveryReadyToShipping() {
        return new MyBatisBatchItemWriterBuilder<OrderDto>()
                .sqlSessionFactory(shopSessionFactory)
                .assertUpdates(false)
                .statementId("com.nhnacademy.bookpubbatch.repository.order.OrderMapper.updateOrderShipping")
                .build();
    }

    /**
     * 주문 : 배송중 -> 배송완료 변경
     *
     * @return the my batis batch item writer
     */
    @Bean
    public MyBatisBatchItemWriter<OrderDto> updateOrderDeliveryShippingToDone() {
        return new MyBatisBatchItemWriterBuilder<OrderDto>()
                .sqlSessionFactory(shopSessionFactory)
                .assertUpdates(false)
                .statementId("com.nhnacademy.bookpubbatch.repository.order.OrderMapper.updateOrderDone")
                .build();
    }

    /**
     * 주문상품 : 배송대기 -> 배송중 변경
     *
     * @return the my batis batch item writer
     */
    @Bean
    public MyBatisBatchItemWriter<OrderProductDto> updateOrderProductToShipping() {
        return new MyBatisBatchItemWriterBuilder<OrderProductDto>()
                .sqlSessionFactory(shopSessionFactory)
                .assertUpdates(false)
                .statementId("com.nhnacademy.bookpubbatch.repository.orderproduct.OrderProductMapper.orderProductDeliveryReadyToShipping")
                .build();
    }

    /**
     * 주문상품 : 배송중 -> 구매확정 대기
     *
     * @return the my batis batch item writer
     */
    @Bean
    public MyBatisBatchItemWriter<OrderProductDto> updateOrderProductToWaitingPurchase() {
        return new MyBatisBatchItemWriterBuilder<OrderProductDto>()
                .sqlSessionFactory(shopSessionFactory)
                .assertUpdates(false)
                .statementId("com.nhnacademy.bookpubbatch.repository.orderproduct.OrderProductMapper.orderProductDeliveryShippingToWaitingPurchase")
                .build();
    }

}
