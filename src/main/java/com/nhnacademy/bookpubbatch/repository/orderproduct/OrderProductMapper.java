package com.nhnacademy.bookpubbatch.repository.orderproduct;

import com.nhnacademy.bookpubbatch.repository.orderproduct.dto.OrderProductDto;
import com.nhnacademy.bookpubbatch.repository.orderproduct.dto.ProductDto;
import java.util.List;

/**
 * 주문상품에 대한 mapper 클래스입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
public interface OrderProductMapper {

    /**
     * 주문상품의 배송대기의 상품들을 가져오는 메서드입니다.
     * 이때 주문의 상태는 배송중인 상품들만 가져옵니다.
     *
     * @return 주문상품 번호들을 반환합니다.
     */
    List<OrderProductDto> getOrderProductDeliveryReady();

    /**
     * 주문상품의 배송중의 상품들을 가져오는 메서드입니다.
     * 이때 주문의 상태는 배송완료인 상품들만 가져옵니다.
     *
     * @return 주문상품 번호들을 반환합니다.
     */
    List<OrderProductDto> getOrderProductDeliveryShipping();

    /**
     * 주문상품의 구매확정대기가 7일이상 지속된 값을 가져오는 메서드입니다.
     * 이때 주문의 상태는 배송완료인것만 가져옵니다.
     *
     * @return the order product waiting purchase
     */
    List<OrderProductDto> getOrderProductWaitingPurchase();

    /**
     * 주문상품의 배송대기의 상품들을 배송중인 상태로 변경하는 메서드입니다.
     *
     * @param dto the dto
     */
    void orderProductDeliveryReadyToShipping(OrderProductDto dto);

    /**
     * 주문상품의 배송중인 상품들을 구매확정 대기 상태로 변경하는 메서드입니다.
     *
     * @param dto the dto
     */
    void orderProductDeliveryShippingToWaitingPurchase(OrderProductDto dto);

    /**
     * 주문상품의 구매확정대기인상품들을 구매확정 상태로 변경하는 메서드입니다.
     *
     * @param dto the dto
     */
    void orderProductWaitingPurchaseToDone(OrderProductDto dto);

    /**
     * 배스트 셀러가 될 상품들을 조회합니다.
     *
     * @return the best product
     */
    List<ProductDto> getBestProduct();

    /**
     * 베스트 셀러로 변경합니다.
     *
     * @param productDto 베스트셀러가 될 상품정보가 들어옵니다.
     */
    void updateBestSeller(ProductDto productDto);

}
