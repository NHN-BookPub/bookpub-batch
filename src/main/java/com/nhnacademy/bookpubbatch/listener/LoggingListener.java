package com.nhnacademy.bookpubbatch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.stereotype.Component;

/**
 * Logging 용 Class
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Slf4j
@Component
public class LoggingListener {
    /**
     * Step 실행전 확인
     *
     * @param execution the execution
     */
    @BeforeStep
    public void beforeStep(StepExecution execution){
        log.info("start : {}", execution.getStepName());
    }

    /**
     * Step 실행 후 확인
     *
     * @param execution the execution
     */
    @AfterStep
    public void afterStep(StepExecution execution) {
        log.info("end : {}", execution.getStepName());

    }
}
