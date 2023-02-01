package com.nhnacademy.bookpubbatch.promotion.writer;

import com.nhnacademy.bookpubbatch.repository.tier.dto.TierDto;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

/**
 * step 에서 사용하기위한 writer 클래스입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Slf4j
@StepScope
@Configuration
public class PromotionTierWriter implements ItemWriter<TierDto> {
    private StepExecution stepExecution;

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(List<? extends TierDto> tiers) {
        log.warn("processor 동작");
        ExecutionContext executionContext = this.stepExecution.getExecutionContext();
        executionContext.put("tiers", tiers);
        log.warn("processor 동작 완료");

    }

    /**
     * 스텝이 시작되기전에 Step Context 에서 등급들의 값을 불러오는 메서드입니다.
     *
     * @param stepExecution the step execution
     */
    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
        log.warn("read 실행전에 시작 : {}", stepExecution.getExecutionContext().get("tiers"));
    }
}
