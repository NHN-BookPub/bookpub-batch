package com.nhnacademy.bookpubbatch.repository.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원주문시 point 를 올리기위한 Dto 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Getter
@NoArgsConstructor
public class OrderPointDto {
    private Long point;
    private Long memberNo;
    private Long orderNo;
    private String reason;

    /**
     * null 값인 point 를 0 으로 만들어줌.
     */
    public void zeroPoint() {
        this.point = 0L;
    }
}
