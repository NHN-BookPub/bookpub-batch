package com.nhnacademy.bookpubbatch.delivery.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.nhnacademy.bookpubbatch.repository.delivery.dto.DeliveryStateDto;
import com.nhnacademy.bookpubbatch.repository.delivery.dto.DeliveryResponseDto;
import com.nhnacademy.bookpubbatch.repository.delivery.dto.DeliveryUpdateDto;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * 배송을 업데이트하기위한 Processor 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Slf4j
@StepScope
@Component
public class DeliveryUpdateProcessor implements ItemProcessor<DeliveryResponseDto, DeliveryUpdateDto> {
    private List<DeliveryStateDto> state;

    /**
     * 배송에대한 정보를 가공하는 프로세서.
     *
     * @param dto 주문정보 기입
     * @return 수정할 주문정보
     */
    @Override
    public DeliveryUpdateDto process(DeliveryResponseDto dto) {
        return DeliveryUpdateDto.builder()
                .deliveryNo(dto.getDeliveryNo())
                .stateNo(state.get(0).getDeliveryStateNo())
                .build();
    }

    /**
     * Step 실행전 Context 에있는값을 불러와서 저장하는 메서드
     *
     * @param stepExecution the step execution
     */
    @BeforeStep
    public void retrieve(StepExecution stepExecution) {
        ObjectMapper mapper = new ObjectMapper();
        JobExecution jobExecution = stepExecution.getJobExecution();

        state = mapper.convertValue(jobExecution.getExecutionContext().get("deliveryState"),
                TypeFactory.defaultInstance()
                        .constructCollectionType(List.class, DeliveryStateDto.class));
        log.warn("state : {}",state.size());
    }

}
