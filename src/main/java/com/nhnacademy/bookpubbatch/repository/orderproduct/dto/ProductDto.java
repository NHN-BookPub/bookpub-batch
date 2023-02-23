package com.nhnacademy.bookpubbatch.repository.orderproduct.dto;

import lombok.Getter;

/**
 * 상품 번호가 반환됩니다.
 * 베스트 셀러로 변화시에 사용됩니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Getter
public class ProductDto {
    private Long productNo;
    private Long count;
}
