package com.nhnacademy.bookpubbatch.delivery.writer;

import com.nhnacademy.bookpubbatch.repository.delivery.dto.DeliveryStateDto;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

/**
 * 배송관련 writer 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Slf4j
@StepScope
@Configuration
public class DeliveryStateWriter implements ItemWriter<DeliveryStateDto> {
    private StepExecution stepExecution;

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(List<? extends DeliveryStateDto> deliveryState) {
        ExecutionContext executionContext = this.stepExecution.getExecutionContext();
        executionContext.put("deliveryState", deliveryState);
        log.error("delvieryState : {}", deliveryState.size());
    }

    /**
     * 스텝이 시작되기전에 Step Context 에서 등급들의 값을 불러오는 메서드입니다.
     *
     * @param stepExecution the step execution
     */
    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
        log.warn("deliveryState : {}", stepExecution.getExecutionContext().get("deliveryState"));

    }
}
