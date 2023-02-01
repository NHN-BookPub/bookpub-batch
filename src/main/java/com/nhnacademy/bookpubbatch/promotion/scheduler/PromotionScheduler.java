package com.nhnacademy.bookpubbatch.promotion.scheduler;

import com.nhnacademy.bookpubbatch.promotion.config.PromotionJobConfig;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class PromotionScheduler {

    private final JobLauncher jobLauncher;
    private final PromotionJobConfig promotionJobConfig;

    /**
     * JoLauncher 를 통해 등록된 job 을 특정시간에 실행시킨다.
     * 매월 마지막날 적용 11 시에진행
     * 초 분 시 일 월 주 년
     * 0 0 11 L * ?  <-- 원래넣어야할  값
     * 테스트용으로 매일 12시에 진행
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void runCouponBatchJob() {

        try {
            JobParameters jobParameters = new JobParametersBuilder().toJobParameters();
            log.info("Promotion scheduler : " + jobParameters.getString("time"));
            jobLauncher.run(promotionJobConfig.promotionJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        }
    }

}
