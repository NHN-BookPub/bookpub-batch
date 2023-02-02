package com.nhnacademy.bookpubbatch.repository.order.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * 주문의 업데이트할 정보들이 기입됩니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Getter
@Builder
public class OrderUpdateDto {
    private Long orderNo;
    private Integer orderStateNo;
}
