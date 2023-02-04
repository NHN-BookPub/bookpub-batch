package com.nhnacademy.bookpubbatch.repository.delivery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 배송중인 배송정보를 얻어오는 dto
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Getter
@NoArgsConstructor
public class DeliveryShippingResponseDto {
    private Long deliveryNo;
    private String address;
}
