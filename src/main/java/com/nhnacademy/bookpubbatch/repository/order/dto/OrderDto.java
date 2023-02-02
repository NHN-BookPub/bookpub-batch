package com.nhnacademy.bookpubbatch.repository.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 일자를 비교해서 가져온 결제대기상태의 주문
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Getter
@NoArgsConstructor
public class OrderDto {
    private Long orderNo;
}
