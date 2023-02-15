package com.nhnacademy.bookpubbatch.repository.member;

import com.nhnacademy.bookpubbatch.repository.member.dto.MemberDto;
import com.nhnacademy.bookpubbatch.repository.member.dto.MemberPromotionDto;
import com.nhnacademy.bookpubbatch.repository.order.dto.OrderPointDto;
import java.util.List;

/**
 * Some description here.
 *
 * @author : 유호철
 * @since : 1.0
 **/
public interface MemberMapper {
    /**
     * 회원 정보를 가져오는 메서드입니다.
     *
     * @return the 회원들이 반환됩니다.
     */
    List<MemberDto> getMembers();

    /**
     * 회원정보를 업데이트하는 메서드입니다.
     *
     * @param dto 업데이트할 회원정보가 기입됩니다.
     */
    void updateMembers(MemberPromotionDto dto);

    /**
     * 회원의 포인트를 업데이트하는 메서드입니다.
     *
     * @param dto the dto
     */
    void updateMemberPoint(OrderPointDto dto);
}
