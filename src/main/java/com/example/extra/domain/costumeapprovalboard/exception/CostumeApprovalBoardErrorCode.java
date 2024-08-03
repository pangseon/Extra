package com.example.extra.domain.costumeapprovalboard.exception;

import com.example.extra.global.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CostumeApprovalBoardErrorCode implements ErrorCode {
    ;

    private final HttpStatus status;
    private final String message;

    CostumeApprovalBoardErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
