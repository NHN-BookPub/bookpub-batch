package com.nhnacademy.bookpubbatch.birthcoupon.mapper;

import com.nhnacademy.bookpubbatch.birthcoupon.dto.BirthMemberDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 * DB의 생일 회원 조회 쿼리를 BirthMemberDto 에 매핑합니다.
 *
 * @author : 김서현
 * @since : 1.0
 **/
public class BirthMemberDtoMapper implements RowMapper<BirthMemberDto> {


    @Override
    public BirthMemberDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        BirthMemberDto birthMemberDto = new BirthMemberDto();
        birthMemberDto.setMemberNo(rs.getLong("member_number"));
        birthMemberDto.setBirthMonth(rs.getString("member_birth_month"));

        return birthMemberDto;
    }
}
