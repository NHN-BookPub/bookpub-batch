package com.nhnacademy.bookpubbatch.product.writer;

import com.nhnacademy.bookpubbatch.repository.orderproduct.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 상품 관련 writer 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Configuration
@RequiredArgsConstructor
public class ProductWriter {
    private final SqlSessionFactory sqlSessionFactory;


    /**
     * 상품의 판매부수를 확인하여  베스트셀러로 업데이트합니다.
     *
     * @return the my batis batch item writer
     */
    @Bean
    public MyBatisBatchItemWriter<ProductDto> bestsellerUpdateWriter(){
        return new MyBatisBatchItemWriterBuilder<ProductDto>()
                .assertUpdates(false)
                .statementId("com.nhnacademy.bookpubbatch.repository.orderproduct.OrderProductMapper.updateBestSeller")
                .sqlSessionFactory(sqlSessionFactory)
                .build();
    }
}
