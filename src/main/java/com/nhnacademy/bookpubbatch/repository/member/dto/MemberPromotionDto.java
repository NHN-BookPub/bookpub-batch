package com.nhnacademy.bookpubbatch.repository.member.dto;

import com.nhnacademy.bookpubbatch.repository.tier.dto.TierDto;
import lombok.Getter;

/**
 * 회원의 데이터를 승급시킬 Dto 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Getter
public class MemberPromotionDto {
    private Long memberNo;
    private Integer tierNo;
    private Long point;

    public MemberPromotionDto(TierDto tier, MemberDto member) {
        this.memberNo = member.getMemberNo();
        this.tierNo = tier.getTierNo();
        this.point = tier.getTierPrice();
    }
}
