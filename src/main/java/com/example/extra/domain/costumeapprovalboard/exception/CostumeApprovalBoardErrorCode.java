package com.example.extra.domain.costumeapprovalboard.exception;

import com.example.extra.global.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CostumeApprovalBoardErrorCode implements ErrorCode {
    NOT_ABLE_TO_READ_COSTUME_APPROVAL_BOARD(HttpStatus.BAD_REQUEST, "해당 글을 읽을 권한이 없습니다"),
    NOT_FOUND_COSTUME_APPROVAL_BOARD(HttpStatus.NOT_FOUND, "유효하지 않은 의상 승인 게시글입니다");

    private final HttpStatus status;
    private final String message;

    CostumeApprovalBoardErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
