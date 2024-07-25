package com.example.extra.domain.applicationrequest.dto.service;

// controller 단에서 mapper에 의해 request body가 얘로 바뀌어 서비스 매개변수로
public record ApplicationRequestDeleteServiceRequestDto(
    Long roleId
) {

}