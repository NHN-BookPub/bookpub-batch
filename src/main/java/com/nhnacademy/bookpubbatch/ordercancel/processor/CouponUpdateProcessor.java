package com.nhnacademy.bookpubbatch.ordercancel.processor;

import com.nhnacademy.bookpubbatch.repository.coupon.dto.CouponDto;
import com.nhnacademy.bookpubbatch.repository.coupon.dto.CouponUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * 쿠폰 값을 update 할 coupon 정보로 변경해주는 processor 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@StepScope
@Component
@RequiredArgsConstructor
public class CouponUpdateProcessor  implements ItemProcessor<CouponDto, CouponUpdateDto> {

    /**
     * update 시킬 dto 로 변환시킨다.
     *
     * @param couponDto 쿠폰정보 기입
     * @return 수정할 쿠폰정보
     */
    @Override
    public CouponUpdateDto process(CouponDto couponDto) {
        return CouponUpdateDto.builder()
                .couponNo(couponDto.getCouponNo())
                .build();
    }
}
