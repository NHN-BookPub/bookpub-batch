package com.nhnacademy.bookpubbatch.repository.delivery;

import com.nhnacademy.bookpubbatch.repository.delivery.dto.DeliveryResponseDto;
import com.nhnacademy.bookpubbatch.repository.delivery.dto.DeliveryShippingResponseDto;
import com.nhnacademy.bookpubbatch.repository.delivery.dto.DeliveryStateDto;
import java.util.List;

/**
 * 배송관련 Mybatis 설정
 *
 * @author : 유호철
 * @since : 1.0
 **/
public interface DeliveryMapper {

    DeliveryStateDto getDeliveryState();

    List<DeliveryResponseDto> getDeliveryByReady();

    void createDeliveryLocation(DeliveryResponseDto dto);

    List<DeliveryShippingResponseDto> getDeliveryByShipping();

    void createDeliveryLocationEnd(DeliveryShippingResponseDto dto);

    void updateDeliveryEnd(DeliveryShippingResponseDto dto);
}
