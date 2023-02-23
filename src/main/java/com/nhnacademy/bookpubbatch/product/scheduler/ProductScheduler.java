package com.nhnacademy.bookpubbatch.product.scheduler;

import com.nhnacademy.bookpubbatch.product.config.ProductJobConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 상품 스케줄러 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Component
@Slf4j
@RequiredArgsConstructor
public class ProductScheduler {
    private final JobLauncher jobLauncher;
    private final ProductJobConfig productJobConfig;

    /**
     * 매월 마지막날 오후 10시에 실행됩니다.
     *
     */
    @Scheduled(cron = "0 0 10 L * ?")
    public void runProductBestSeller(){
        try {
            JobParameters jobParameters = new JobParametersBuilder().toJobParameters();
            jobLauncher.run(productJobConfig.bestSeller(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        }
    }
}
