package com.example.extra.domain.member.dto.service.response;

import lombok.Builder;

@Builder
public record MemberUrlReadServiceResponseDto(
    String url
) {
}
