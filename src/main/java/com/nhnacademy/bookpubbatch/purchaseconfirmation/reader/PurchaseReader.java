package com.nhnacademy.bookpubbatch.purchaseconfirmation.reader;

import com.nhnacademy.bookpubbatch.repository.order.dto.OrderPointDto;
import com.nhnacademy.bookpubbatch.repository.orderproduct.dto.OrderProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 구매확정
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Slf4j
@Configuration
@RequiredArgsConstructor
public class PurchaseReader {
    private static final Integer PAGE_SIZE = 10;

    private final SqlSessionFactory sqlSessionFactoryBean;

    /**
     * 주문상품의 point 를 읽어오는 reader 입니다.
     *
     * @return the order point reader
     */
    @Bean
    public MyBatisPagingItemReader<OrderPointDto> getOrderPointReader() {
        return new MyBatisPagingItemReaderBuilder<OrderPointDto>()
                .sqlSessionFactory(sqlSessionFactoryBean)
                .pageSize(PAGE_SIZE)
                .queryId("com.nhnacademy.bookpubbatch.repository.order.OrderMapper.getOrderPoint")
                .build();
    }

    /**
     * 구매확정 대기 + 7일 주문상품 읽어오기.
     *
     * @return the my batis paging item reader
     */
    @Bean
    public MyBatisPagingItemReader<OrderProductDto> orderProductStateWaitingReader() {
        return new MyBatisPagingItemReaderBuilder<OrderProductDto>()
                .sqlSessionFactory(sqlSessionFactoryBean)
                .queryId("com.nhnacademy.bookpubbatch.repository.orderproduct.OrderProductMapper.getOrderProductWaitingPurchase")
                .pageSize(PAGE_SIZE)
                .build();
    }

    /**
     * 구매확정 대기 + 7일 주문 읽어오기.
     *
     * @return the order done reader
     */
    @Bean
    public MyBatisPagingItemReader<OrderPointDto> getOrderDoneReader() {
        return new MyBatisPagingItemReaderBuilder<OrderPointDto>()
                .sqlSessionFactory(sqlSessionFactoryBean)
                .queryId("com.nhnacademy.bookpubbatch.repository.order.OrderMapper.getOrderDone")
                .pageSize(PAGE_SIZE)
                .build();
    }
}
