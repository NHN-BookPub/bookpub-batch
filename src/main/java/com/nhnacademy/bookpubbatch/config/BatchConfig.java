package com.nhnacademy.bookpubbatch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Batch 작업을 위한 config 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@RequiredArgsConstructor
@Configuration
public class BatchConfig {
    private final JobRegistry jobRegistry;

    /**
     * JobRegistry는 필수는 아니지만 context에서 Job을 추적하거나 다른 곳에서 생성된 Job을 application context의 중앙에 모을 때 유용하다.
     * BeanPostProcessor 는 빈 후처리기로 조작 이나 다른 객체로 교체를 합니다.
     *
     * @return the bean post processor
     */
    @Bean
    public BeanPostProcessor jobRegistryBeanProcessor() {
        JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();
        postProcessor.setJobRegistry(jobRegistry);
        return postProcessor;
    }

}
