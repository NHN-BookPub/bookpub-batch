package com.nhnacademy.bookpubbatch.product.reader;

import com.nhnacademy.bookpubbatch.repository.orderproduct.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 상품관련 reader 설정 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Configuration
@RequiredArgsConstructor
public class ProductReader {
    private final SqlSessionFactory sqlSessionFactory;

    /**
     * 베스트셀러가 될 상품번호를 받아옵니다.
     *
     * @return the my batis paging item reader
     */
    @Bean
    public MyBatisPagingItemReader<ProductDto> bestSellerInfo() {
        return new MyBatisPagingItemReaderBuilder<ProductDto>()
                .queryId("com.nhnacademy.bookpubbatch.repository.orderproduct.OrderProductMapper.getBestProduct")
                .sqlSessionFactory(sqlSessionFactory)
                .build();
    }
}
