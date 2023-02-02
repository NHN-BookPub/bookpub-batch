package com.nhnacademy.bookpubbatch.config;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.repository.ExecutionContextSerializer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

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
     * JobRegistry는 필수는 아니지만 context에서 Job을 추적하거나 다른 곳에서 생성된
     * Job을 application context 의 처리에 좋음
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

    /**
     * Mybatis 설정에 의한 BatchConfigurer 을 재정의
     *
     * @param dataSource                 데이터소스 @Primary 지정값 기입
     * @param executionContextSerializer 설정한 Jackson Serializer 형식으로 처리
     * @param transactionManager         Bean 에 올라가있는 Tx manager 가 주입
     * @return the batch configurer      BatchConfigurer 설정값이 반환
     */
    @Bean
    BatchConfigurer myBatchConfigurer(DataSource dataSource,
                                      ExecutionContextSerializer executionContextSerializer,
                                      PlatformTransactionManager transactionManager) {
        return new DefaultBatchConfigurer(dataSource) {
            @Override
            protected JobExplorer createJobExplorer() throws Exception {
                JobExplorerFactoryBean jobExplorerFactoryBean = new JobExplorerFactoryBean();
                jobExplorerFactoryBean.setDataSource(dataSource);
                jobExplorerFactoryBean
                        .setSerializer(executionContextSerializer);
                jobExplorerFactoryBean.afterPropertiesSet();
                return jobExplorerFactoryBean.getObject();
            }

            @Override
            protected JobRepository createJobRepository() throws Exception {
                JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
                jobRepositoryFactoryBean.setDataSource(dataSource);
                jobRepositoryFactoryBean
                        .setSerializer(executionContextSerializer);
                jobRepositoryFactoryBean.setTransactionManager(transactionManager);
                jobRepositoryFactoryBean.afterPropertiesSet();
                return jobRepositoryFactoryBean.getObject();
            }
        };
    }
}
