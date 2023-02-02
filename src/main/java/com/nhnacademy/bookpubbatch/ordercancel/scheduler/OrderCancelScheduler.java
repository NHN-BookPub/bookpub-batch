package com.nhnacademy.bookpubbatch.ordercancel.scheduler;

import com.nhnacademy.bookpubbatch.ordercancel.config.OrderCancelJobConfig;
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
 * 주문취소 되었을때의 schedluer 셋팅입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCancelScheduler {
    private final JobLauncher jobLauncher;
    private final OrderCancelJobConfig orderCancelJobConfig;

    /**
     * 매일 23시 58분에 돌아갑니다.
     */
    @Scheduled(cron = "58 23 * * *")
    public void runOrderCancel() {
        try {
            JobParameters jobParameters = new JobParametersBuilder().toJobParameters();
            jobLauncher.run(orderCancelJobConfig.orderCancel(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        }
    }
}
