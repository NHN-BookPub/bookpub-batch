package com.nhnacademy.bookpubbatch.repository.coupon;

import com.nhnacademy.bookpubbatch.repository.coupon.dto.CouponDto;
import com.nhnacademy.bookpubbatch.repository.coupon.dto.CouponUpdateDto;
import java.util.List;

/**
 * 쿠폰 정보를 다루는 Mapper 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
public interface CouponMapper {
    /**
     * 회원 정보를 가져오는 메서드입니다.
     *
     * @return the 회원들이 반환됩니다.
     */
    List<CouponDto> getCoupons();

    /**
     * 회원정보를 업데이트하는 메서드입니다.
     *
     * @param dto 업데이트할 회원정보가 기입됩니다.
     */
    void updateCoupons(CouponUpdateDto dto);
}
