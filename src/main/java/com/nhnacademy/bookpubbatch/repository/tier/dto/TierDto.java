package com.nhnacademy.bookpubbatch.repository.tier.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 등급에 관한 정보를 가져오기위한 Dto 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Getter
@NoArgsConstructor
public class TierDto {
    private Integer tierNo;
    private Long tierPrice;
}
