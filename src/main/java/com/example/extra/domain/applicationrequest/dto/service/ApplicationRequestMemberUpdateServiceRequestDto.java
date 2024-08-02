package com.example.extra.domain.applicationrequest.dto.service;

import com.example.extra.global.enums.ApplyStatus;

public record ApplicationRequestMemberUpdateServiceRequestDto(
    ApplyStatus applyStatus
) {

}
