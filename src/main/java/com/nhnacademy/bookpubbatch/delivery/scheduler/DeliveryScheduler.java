package com.nhnacademy.bookpubbatch.delivery.scheduler;

import com.nhnacademy.bookpubbatch.delivery.config.DeliveryJobConfig;
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
 * 배송관련 스케줄러입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryScheduler {
    private final JobLauncher jobLauncher;
    private final DeliveryJobConfig deliveryJobConfig;

    /**
     * 배송준비 -> 배송중 변경
     *
     */
    @Scheduled(cron = "55 10 * * * *", zone = "Asia/Seoul")
    public void runDeliveryState() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .toJobParameters();
            jobLauncher.run(deliveryJobConfig.deliveryLocation(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 배송중 -> 배송완료 변경
     *
     */
    @Scheduled(cron = "56 10 * * * *", zone = "Asia/Seoul")
    public void runDeliveryFinish(){
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .toJobParameters();
            jobLauncher.run(deliveryJobConfig.deliveryEnd(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        }
    }
}
