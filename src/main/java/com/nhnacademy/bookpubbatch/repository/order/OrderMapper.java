package com.nhnacademy.bookpubbatch.repository.order;

import com.nhnacademy.bookpubbatch.repository.order.dto.OrderDto;
import com.nhnacademy.bookpubbatch.repository.order.dto.OrderStateDto;
import com.nhnacademy.bookpubbatch.repository.order.dto.OrderUpdateDto;
import java.util.List;

/**
 * 주문 Mybatis 와 연동되기위한 Mapper 인터페이스입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
public interface OrderMapper {

    List<OrderDto> getOrders();

    OrderStateDto getOrderStates();

    void updateOrderCancel(OrderUpdateDto orderUpdate);

    List<OrderDto> getOrderDeliveryReady();

    List<OrderDto> getOrderDeliveryShipping();

    void updateOrderShipping(OrderDto dto);

    void updateOrderDone(OrderDto dto);
}
