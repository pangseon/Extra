package com.example.extra.global.exception;

import com.example.extra.global.exception.dto.FieldErrorResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomValidationException extends RuntimeException {

    private final FieldErrorResponseDto error;
}
