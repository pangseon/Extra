package com.example.extra.domain.applicationrequest.dto.service;

import com.example.extra.global.enums.ApplyStatus;

// controller 단에서 mapper에 의해 request body가 얘로 바뀌어 서비스 매개변수로
public record ApplicationRequestCreateServiceRequestDto(
    ApplyStatus applyStatus,
    Long roleId
) {

}