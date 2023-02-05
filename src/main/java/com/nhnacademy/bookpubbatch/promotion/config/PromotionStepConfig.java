package com.nhnacademy.bookpubbatch.promotion.config;

import com.nhnacademy.bookpubbatch.listener.LoggingListener;
import com.nhnacademy.bookpubbatch.promotion.exception.MemberNotFoundException;
import com.nhnacademy.bookpubbatch.promotion.processor.PromotionProcessor;
import com.nhnacademy.bookpubbatch.promotion.reader.PromotionReader;
import com.nhnacademy.bookpubbatch.promotion.writer.PromotionTierWriter;
import com.nhnacademy.bookpubbatch.promotion.writer.PromotionUpdateWriter;
import com.nhnacademy.bookpubbatch.repository.member.dto.MemberDto;
import com.nhnacademy.bookpubbatch.repository.member.dto.MemberPromotionDto;
import com.nhnacademy.bookpubbatch.repository.tier.dto.TierDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DeadlockLoserDataAccessException;

/**
 * 멤버 등급 승급 관련 Step Config 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Slf4j
@Configuration
@RequiredArgsConstructor
public class PromotionStepConfig {
    private final StepBuilderFactory stepBuilderFactory;
    private final PromotionTierWriter promotionTierWriter;
    private final PromotionProcessor processor;
    private final PromotionReader reader;
    private final LoggingListener loggingListener;

    private final PromotionUpdateWriter updateWriter;
    private static final Integer CHUNK_SIZE = 10;

    /**
     * 회원의 정보를 가져와서 context 에 있는값과 비교하여 업데이트하는 메서드입니다.
     *
     * @return the step
     */
    @JobScope
    @Bean
    public Step promotionMemberInfo() {
        return stepBuilderFactory.get("회원정보 가져오기")
                .<MemberDto, MemberPromotionDto>chunk(CHUNK_SIZE)
                .reader(reader.memberReader())
                .processor(processor)
                .writer(updateWriter.memberUpdate())
                .faultTolerant()
                .skip(MemberNotFoundException.class)
                .retryLimit(3)
                .retry(DeadlockLoserDataAccessException.class)
                .listener(loggingListener)
                .build();
    }

    /**
     * 등급에 대한 정보를 가져오기 위한 메서드입니다.
     *
     * @return the step
     */
    @JobScope
    @Bean
    public Step promotionTierInfo() {
        return stepBuilderFactory.get("등급 정보가져오기")
                .<TierDto, TierDto>chunk(CHUNK_SIZE)
                .reader(reader.tierReader())
                .writer(promotionTierWriter)
                .faultTolerant()
                .retryLimit(3)
                .retry(DeadlockLoserDataAccessException.class)
                .listener(promotionListener())
                .listener(loggingListener)
                .build();
    }

    /**
     * 데이터 공유를 위한 Listener
     *
     * @return 데이터 공유에 필요한 key 값을 가진 ExecutionContextPromotionListener 가 반환 됩니다.
     */
    @JobScope
    @Bean
    public ExecutionContextPromotionListener promotionListener() {
        ExecutionContextPromotionListener executionContextPromotionListener =
                new ExecutionContextPromotionListener();
        executionContextPromotionListener.setKeys(new String[]{"tiers"});
        return executionContextPromotionListener;
    }
}
