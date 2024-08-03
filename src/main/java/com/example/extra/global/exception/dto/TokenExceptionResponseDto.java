package com.example.extra.global.exception.dto;

import lombok.Builder;

@Builder
public record TokenExceptionResponseDto(
    int status,
    String name,
    String message
) {

}
