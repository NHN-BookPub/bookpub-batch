package com.nhnacademy.bookpubbatch.repository.delivery.dto;

import lombok.Builder;
import lombok.Getter;


/**
 * 배송준비 -> 배송중으로 변경하기위한 update dto
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Getter
@Builder
public class DeliveryUpdateDto {
    private Long deliveryNo;
    private Integer stateNo;

}
