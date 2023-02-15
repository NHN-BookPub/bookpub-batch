package com.nhnacademy.bookpubbatch.purchaseconfirmation.writer;

import com.nhnacademy.bookpubbatch.repository.order.dto.OrderPointDto;
import com.nhnacademy.bookpubbatch.repository.orderproduct.dto.OrderProductDto;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 구매확정시 일어날 일들에대한 writer 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Configuration
@RequiredArgsConstructor
public class PurchaseWriter {

    private final SqlSessionFactory sessionFactory;


    /**
     * 주문상품 point 를 통해 포인트 내역 생성
     *
     * @return the my batis batch item writer
     */
    @Bean
    public MyBatisBatchItemWriter<OrderPointDto> insertPoint(){
        return new MyBatisBatchItemWriterBuilder<OrderPointDto>()
                .sqlSessionFactory(sessionFactory)
                .statementId("com.nhnacademy.bookpubbatch.repository.order.OrderMapper.insertPointHistory")
                .build();
    }

    /**
     * 회원의 포인트를 업데이트하는 메서드입니다.
     *
     * @return the my batis batch item writer
     */
    @Bean
    public MyBatisBatchItemWriter<OrderPointDto> updateMemberPoint() {
        return new MyBatisBatchItemWriterBuilder<OrderPointDto>()
                .sqlSessionFactory(sessionFactory)
                .statementId("com.nhnacademy.bookpubbatch.repository.member.MemberMapper.updateMemberPoint")
                .assertUpdates(false)
                .build();
    }

    /**
     * 주문상품 : 구매확정대기 -> 구매확정
     *
     * @return the my batis batch item writer
     */
    @Bean
    public MyBatisBatchItemWriter<OrderProductDto> updateOrderProductToPurchaseConfirmation() {
        return new MyBatisBatchItemWriterBuilder<OrderProductDto>()
                .sqlSessionFactory(sessionFactory)
                .assertUpdates(false)
                .statementId("com.nhnacademy.bookpubbatch.repository.orderproduct.OrderProductMapper.orderProductWaitingPurchaseToDone")
                .build();
    }

    /**
     * 주문 : 배송완료 -> 구매확정
     *
     * @return the my batis batch item writer
     */
    @Bean
    public MyBatisBatchItemWriter<OrderPointDto> updateOrderPurchaseConfirmation() {
        return new MyBatisBatchItemWriterBuilder<OrderPointDto>()
                .sqlSessionFactory(sessionFactory)
                .assertUpdates(false)
                .statementId("com.nhnacademy.bookpubbatch.repository.order.OrderMapper.updatePurchaseDone")
                .build();
    }

}
