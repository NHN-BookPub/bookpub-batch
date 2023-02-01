package com.nhnacademy.bookpubbatch.promotion.writer;

import com.nhnacademy.bookpubbatch.repository.member.dto.MemberPromotionDto;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 회원의 정보를 업데이트 하기위한 Batch 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Configuration
@RequiredArgsConstructor
public class PromotionUpdateWriter {

    private final SqlSessionFactory sessionFactory;

    @Bean
    public MyBatisBatchItemWriter<MemberPromotionDto> memberUpdate() {
        return new MyBatisBatchItemWriterBuilder<MemberPromotionDto>()
                .sqlSessionFactory(sessionFactory)
                .statementId("com.nhnacademy.bookpubbatch.repository.member.MemberMapper.updateMembers")
                .build();
    }
}
