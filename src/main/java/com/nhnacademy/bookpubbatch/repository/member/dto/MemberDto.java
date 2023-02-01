package com.nhnacademy.bookpubbatch.repository.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Some description here.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Getter
@NoArgsConstructor
public class MemberDto {
    private Long memberNo;
    private Long memberPoint;
    private Long totalPrice;
}
