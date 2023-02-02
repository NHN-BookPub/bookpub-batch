package com.nhnacademy.bookpubbatch.ordercancel.writer;

import com.nhnacademy.bookpubbatch.repository.coupon.dto.CouponUpdateDto;
import com.nhnacademy.bookpubbatch.repository.order.dto.OrderUpdateDto;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 주문취소 상태로 바꿀때 writer 행위의 대한 내용이 기입됩니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Configuration
@RequiredArgsConstructor
public class OrderCancelWriter {
    private final SqlSessionFactory sqlSessionFactory;

    /**
     * 주문의 정보를 update 하기위한 writer 입니다.
     *
     * @return order Writer
     */
    @Bean
    public MyBatisBatchItemWriter<OrderUpdateDto> updateOrderWriter(){
        return new MyBatisBatchItemWriterBuilder<OrderUpdateDto>()
                .assertUpdates(false)
                .statementId("com.nhnacademy.bookpubbatch.repository.order.OrderMapper.updateOrderCancel")
                .sqlSessionFactory(sqlSessionFactory)
                .build();
    }

    /**
     * coupon 의 정보를 update 하기위한 writer 입니다.
     *
     * @return coupon Writer
     */
    @Bean
    public MyBatisBatchItemWriter<CouponUpdateDto> updateCouponWriter(){
        return new MyBatisBatchItemWriterBuilder<CouponUpdateDto>()
                .assertUpdates(false)
                .statementId("com.nhnacademy.bookpubbatch.repository.coupon.CouponMapper.updateCoupons")
                .sqlSessionFactory(sqlSessionFactory)
                .build();
    }
}
