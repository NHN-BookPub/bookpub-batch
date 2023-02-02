package com.nhnacademy.bookpubbatch.coupon.scheduler;

import com.nhnacademy.bookpubbatch.coupon.config.CouponBatchConfig;
import com.nhnacademy.bookpubbatch.coupon.scheduler.exception.CouponBatchSchedulerException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
 * 생일 쿠폰 배치를 위한 스케줄러 입니다.
 *
 * @author : 김서현
 * @since : 1.0
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class CouponBatchScheduler {

    private final JobLauncher jobLauncher;
    private final CouponBatchConfig couponBatchConfig;

    /**
     * 매일 새벽 1시 00분에 쿠폰 배치가 실행됩니다.
     */
    //@Scheduled(cron = "0 0 1 * * *", zone = "Asia/Seoul")
    @Scheduled(cron = "10 2-5 * * *", zone = "Asia/Seoul")
    public void runCouponBatchJob() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("CouponJobTime", dateFormat.format(calendar.getTime()))
                    .toJobParameters();
            log.info("BirthDay Coupon Scheduler : " + jobParameters.getString("CouponJobTime"));
            jobLauncher.run(couponBatchConfig.birthCouponJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            throw new CouponBatchSchedulerException(e.getMessage());
        }
    }

}
