package com.nhnacademy.bookpubbatch.delivery.reader;

import com.nhnacademy.bookpubbatch.repository.delivery.dto.DeliveryResponseDto;
import com.nhnacademy.bookpubbatch.repository.delivery.dto.DeliveryShippingResponseDto;
import com.nhnacademy.bookpubbatch.repository.delivery.dto.DeliveryStateDto;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 배송관련 reader 설정입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Configuration
public class DeliveryReader {
    private final SqlSessionFactory sqlSessionFactory;
    private static final Integer PAGE_SIZE = 10;

    public DeliveryReader(@Qualifier("deliverySqlSessionFactory") SqlSessionFactory sqlSessionFactoryBean) {
        this.sqlSessionFactory = sqlSessionFactoryBean;
    }

    /**
     * 배송중인 상태값을 읽어오는 메서드입니다.
     *
     * @return the my batis paging item reader
     */
    @Bean
    public MyBatisPagingItemReader<DeliveryStateDto> deliveryStateReader(){
        return new MyBatisPagingItemReaderBuilder<DeliveryStateDto>()
                .sqlSessionFactory(sqlSessionFactory)
                .queryId("com.nhnacademy.bookpubbatch.repository.delivery.DeliveryMapper.getDeliveryState")
                .pageSize(1)
                .build();
    }

    /**
     * 배송준비인 배송테이블 정보를 읽어오는 reader
     *
     * @return the my batis paging item reader
     */
    @Bean
    public MyBatisPagingItemReader<DeliveryResponseDto> deliveryStateReadyReader(){
        return new MyBatisPagingItemReaderBuilder<DeliveryResponseDto>()
                .sqlSessionFactory(sqlSessionFactory)
                .queryId("com.nhnacademy.bookpubbatch.repository.delivery.DeliveryMapper.getDeliveryByReady")
                .pageSize(PAGE_SIZE)
                .build();
    }

    /**
     * 배송중인 배송테이블 정보를 읽어오는 Reader
     *
     * @return the my batis paging item reader
     */
    @Bean
    public MyBatisPagingItemReader<DeliveryShippingResponseDto> deliveryStateShippingReader() {
        return new MyBatisPagingItemReaderBuilder<DeliveryShippingResponseDto>()
                .sqlSessionFactory(sqlSessionFactory)
                .queryId("com.nhnacademy.bookpubbatch.repository.delivery.DeliveryMapper.getDeliveryByShipping")
                .pageSize(PAGE_SIZE)
                .build();
    }
}
