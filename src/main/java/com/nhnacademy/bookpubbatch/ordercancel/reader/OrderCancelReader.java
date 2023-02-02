package com.nhnacademy.bookpubbatch.ordercancel.reader;

import com.nhnacademy.bookpubbatch.repository.coupon.dto.CouponDto;
import com.nhnacademy.bookpubbatch.repository.order.dto.OrderDto;
import com.nhnacademy.bookpubbatch.repository.order.dto.OrderStateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 주문상태가 취소로 바뀔값들을 읽어오는 reader config 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Slf4j
@Configuration
@RequiredArgsConstructor
public class OrderCancelReader {

    private static final Integer PAGE_SIZE = 10;
    private final SqlSessionFactory sqlSessionFactory;

    /**
     * 결제대기인 상태에대한 주문들의 정보를 가져오는 reader 입니다.
     *
     * @return 주문정보
     */
    @Bean
    public MyBatisPagingItemReader<OrderDto> getOrderReader() {
        log.warn("Order reader 시작");
        return new MyBatisPagingItemReaderBuilder<OrderDto>()
                .sqlSessionFactory(sqlSessionFactory)
                .queryId("com.nhnacademy.bookpubbatch.repository.order.OrderMapper.getOrders")
                .pageSize(PAGE_SIZE)
                .build();
    }

    /**
     * 주문상태값 번호를 받아오는 reader 입니다.
     *
     * @return 주문상태값
     */
    @Bean
    public MyBatisPagingItemReader<OrderStateDto> getOrderStateReader() {
        log.warn("Order reader 시작");
        return new MyBatisPagingItemReaderBuilder<OrderStateDto>()
                .sqlSessionFactory(sqlSessionFactory)
                .queryId("com.nhnacademy.bookpubbatch.repository.order.OrderMapper.getOrderStates")
                .pageSize(PAGE_SIZE)
                .build();
    }

    /**
     * 쿠폰의 정보를 받아오는 reader 입니다.
     *
     * @return 쿠폰정보
     */
    @Bean
    public MyBatisPagingItemReader<CouponDto> getCouponReader(){
        log.warn("Copuon Reader 시작");
        return new MyBatisPagingItemReaderBuilder<CouponDto>()
                .sqlSessionFactory(sqlSessionFactory)
                .queryId("com.nhnacademy.bookpubbatch.repository.coupon.CouponMapper.getCoupons")
                .pageSize(PAGE_SIZE)
                .build();

    }
}
