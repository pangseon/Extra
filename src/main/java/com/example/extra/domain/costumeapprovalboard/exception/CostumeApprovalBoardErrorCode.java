package com.example.extra.domain.costumeapprovalboard.exception;

import com.example.extra.global.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CostumeApprovalBoardErrorCode implements ErrorCode {
    // 400
    ALREADY_EXIST_COSTUME_APPROVAL_BOARD(HttpStatus.BAD_REQUEST, "이미 글을 작성했습니다."),
    NOT_MASTER_COSTUME_APPROVAL_BOARD(HttpStatus.BAD_REQUEST, "회원이 작성한 글이 아닙니다."),

    // 404
    NOT_FOUND_COSTUME_APPROVAL_BOARD(HttpStatus.NOT_FOUND, "작성된 글이 없습니다."),
    ;

    private final HttpStatus status;
    private final String message;

    CostumeApprovalBoardErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
