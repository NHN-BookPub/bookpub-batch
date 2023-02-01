package com.nhnacademy.bookpubbatch.promotion.reader;

import com.nhnacademy.bookpubbatch.repository.member.dto.MemberDto;
import com.nhnacademy.bookpubbatch.repository.tier.dto.TierDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 회원정보를 읽어오는 Reader
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Slf4j
@Configuration
@RequiredArgsConstructor
public class PromotionReader {
    private static final Integer PAGE_SIZE = 10;

    private final SqlSessionFactory sqlSessionFactoryBean;

    @Bean
    public MyBatisPagingItemReader<MemberDto> memberReader() {
        log.warn("reader 시작");
        return new MyBatisPagingItemReaderBuilder<MemberDto>()
                .sqlSessionFactory(sqlSessionFactoryBean)
                .queryId("com.nhnacademy.bookpubbatch.repository.member.MemberMapper.getMembers")
                .pageSize(PAGE_SIZE)
                .build();
    }


    @Bean
    public MyBatisPagingItemReader<TierDto> tierReader() {
        log.warn("tier Reader 시작");
        return new MyBatisPagingItemReaderBuilder<TierDto>()
                .sqlSessionFactory(sqlSessionFactoryBean)
                .queryId("com.nhnacademy.bookpubbatch.repository.tier.TierMapper.getTiers")
                .pageSize(PAGE_SIZE)
                .build();
    }
}
