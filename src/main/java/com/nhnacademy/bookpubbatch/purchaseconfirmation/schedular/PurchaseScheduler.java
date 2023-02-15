package com.nhnacademy.bookpubbatch.purchaseconfirmation.schedular;

import com.nhnacademy.bookpubbatch.purchaseconfirmation.config.PurchaseConfirmationJobConfig;
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
 * 구매확정관련 스케줄러입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class PurchaseScheduler {
    private final JobLauncher jobLauncher;
    private final PurchaseConfirmationJobConfig jobConfig;


    /**
     * 매일 23시 30분 0초에 실행.
     * 1. 포인트내역에 회원이 적용할포인트 추가
     * 2. 회원의 포인트 업데이트
     * 3. 해당 주문상품의 상태를 구매확정으로 변경
     * 4. 해당 주문의 상태를 구매확정으로 변경
     */
    @Scheduled(cron = "0 30 23 * * *")
    public void purchaseRunner() {
        try {
            JobParameters jobParameters = new JobParametersBuilder().toJobParameters();
            jobLauncher.run(jobConfig.purchaseConfirmation(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        }
    }
}
