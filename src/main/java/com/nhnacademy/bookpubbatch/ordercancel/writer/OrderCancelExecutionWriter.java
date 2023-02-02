package com.nhnacademy.bookpubbatch.ordercancel.writer;

import com.nhnacademy.bookpubbatch.repository.order.dto.OrderStateDto;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

/**
 * 주문상태값을 context 저장하는 writer 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Slf4j
@StepScope
@Configuration
public class OrderCancelExecutionWriter implements ItemWriter<OrderStateDto> {

    private StepExecution stepExecution;

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(List<? extends OrderStateDto> list) {
        ExecutionContext executionContext = this.stepExecution.getExecutionContext();
        executionContext.put("orderState", list);
        log.warn("orderState : {}", list);
    }

    /**
     * context 에 orderState 라는 이름으로 저장
     *
     * @param stepExecution the step execution
     */
    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution){
        this.stepExecution = stepExecution;
        log.warn("orders-save : {}",stepExecution.getExecutionContext().get("orderState"));
    }
}
