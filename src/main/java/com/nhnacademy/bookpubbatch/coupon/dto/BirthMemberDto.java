package com.nhnacademy.bookpubbatch.coupon.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * 생일 멤버를 위한 dto 입니다.
 *
 * @author : 김서현
 * @since : 1.0
 **/
@Getter
@Builder
public class BirthMemberDto {

    private Long memberNo;
    private Integer birthMonth;

}
