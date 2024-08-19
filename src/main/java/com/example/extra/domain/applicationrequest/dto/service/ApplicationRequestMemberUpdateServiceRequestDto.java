package com.example.extra.domain.applicationrequest.dto.service;

import com.example.extra.global.enums.ApplyStatus;
import lombok.Builder;

@Builder
public record ApplicationRequestMemberUpdateServiceRequestDto(
    ApplyStatus applyStatus
) {

}
