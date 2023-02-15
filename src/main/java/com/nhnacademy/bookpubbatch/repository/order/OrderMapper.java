package com.nhnacademy.bookpubbatch.repository.order;

import com.nhnacademy.bookpubbatch.repository.order.dto.OrderDto;
import com.nhnacademy.bookpubbatch.repository.order.dto.OrderPointDto;
import com.nhnacademy.bookpubbatch.repository.order.dto.OrderStateDto;
import com.nhnacademy.bookpubbatch.repository.order.dto.OrderUpdateDto;
import java.util.List;

/**
 * 주문 Mybatis 와 연동되기위한 Mapper 인터페이스입니다.
 *
 * @author : 유호철
 * @since : 1.0
 */
public interface OrderMapper {

    /**
     * 주문정보를 받기위한 메서드
     *
     * @return the orders
     */
    List<OrderDto> getOrders();

    /**
     * 주문취소 상태값을 받기위한 메서드
     *
     * @return the order states
     */
    OrderStateDto getOrderStates();

    /**
     * 주문취소 상태로 변경
     *
     * @param orderUpdate 업데이트할 정보
     */
    void updateOrderCancel(OrderUpdateDto orderUpdate);

    /**
     * 배송준비상태의 주문을 받는 메서드입니다.
     *
     * @return the order delivery ready
     */
    List<OrderDto> getOrderDeliveryReady();

    /**
     * 배송중인 상태의 주문을 받는 메서드입니다.
     *
     * @return the order delivery shipping
     */
    List<OrderDto> getOrderDeliveryShipping();

    /**
     *  포인트관련 주문 정보들을 반환.
     *
     * @return the order point
     */
    List<OrderPointDto> getOrderPoint();

    /**
     * 배송중인상태로 변경하기위한 메서드.
     *
     * @param dto the dto
     */
    void updateOrderShipping(OrderDto dto);

    /**
     * 배송완료로 변경하기위한 메서드입니다.
     *
     * @param dto the dto
     */
    void updateOrderDone(OrderDto dto);

    /**
     * 구매확정으로 변경하기위한 메서드.
     *
     * @param dto the dto
     */
    void updatePurchaseDone(OrderPointDto dto);

    /**
     * 포인트 내역을 추가하기위한 메서드.
     *
     * @param dto the dto
     */
    void insertPointHistory(OrderPointDto dto);
}
