package com.nhnacademy.bookpubbatch.purchaseconfirmation.processor;

import com.nhnacademy.bookpubbatch.repository.order.dto.OrderPointDto;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * null 값이 들어올경우 0 으로 변경해주는 processor 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@StepScope
@Component
@RequiredArgsConstructor
public class PurchaseProcessor implements ItemProcessor<OrderPointDto, OrderPointDto> {

    /**
     * null 값이 들어오면 변경한다.
     *
     * @param orderPointDto 포인트 정보 기입.
     * @return 수정할 포인트 정보
     */
    @Override
    public OrderPointDto process(OrderPointDto orderPointDto) {
        if (Objects.isNull(orderPointDto.getPoint())) {
            orderPointDto.zeroPoint();
        }
        return orderPointDto;
    }
}
