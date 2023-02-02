package com.nhnacademy.bookpubbatch.ordercancel.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.nhnacademy.bookpubbatch.repository.order.dto.OrderDto;
import com.nhnacademy.bookpubbatch.repository.order.dto.OrderStateDto;
import com.nhnacademy.bookpubbatch.repository.order.dto.OrderUpdateDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * 주문상품 관련정보를 Update 시키는 Processor
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class OrderUpdateProcessor implements ItemProcessor<OrderDto, OrderUpdateDto> {

    private List<OrderStateDto> orderState;

    /**
     * 주문상품에대한 값을 가공시키는 process
     *
     * @param orderDto 주문정보 기입
     * @return 수정할 주문정보
     */
    @Override
    public OrderUpdateDto process(OrderDto orderDto) {
        return OrderUpdateDto
                .builder()
                .orderNo(orderDto.getOrderNo())
                .orderStateNo(orderState.get(0).getOrderStateNo())
                .build();
    }

    /**
     * Step 실행전 Context 에있는값을 불러와서 저장하는 메서드
     *
     * @param stepExecution the step execution
     */
    @BeforeStep
    public void retrieve(StepExecution stepExecution) {
        log.warn("데이터 가공 시작");
        ObjectMapper mapper = new ObjectMapper();
        JobExecution jobExecution = stepExecution.getJobExecution();

        orderState = mapper.convertValue(jobExecution.getExecutionContext().get("orderState"),
                TypeFactory.defaultInstance()
                        .constructCollectionType(List.class, OrderStateDto.class));
        log.warn("가공된 데이터 : {}", orderState.size());
    }
}
