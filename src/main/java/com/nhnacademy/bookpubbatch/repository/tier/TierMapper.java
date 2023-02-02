package com.nhnacademy.bookpubbatch.repository.tier;

import com.nhnacademy.bookpubbatch.repository.tier.dto.TierDto;
import java.util.List;

/**
 * 등급값을 다루기위한 Mapper 인터페이스 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
public interface TierMapper {
    List<TierDto> getTiers();
}
