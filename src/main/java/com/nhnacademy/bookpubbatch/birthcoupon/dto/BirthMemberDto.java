package com.nhnacademy.bookpubbatch.birthcoupon.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 생일 멤버를 위한 dto 입니다.
 *
 * @author : 김서현
 * @since : 1.0
 **/
@Getter
@Setter
@NoArgsConstructor
public class BirthMemberDto {

    private Long memberNo;
    private String birthMonth;

}
