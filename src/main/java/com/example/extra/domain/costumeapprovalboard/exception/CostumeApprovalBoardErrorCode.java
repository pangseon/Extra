package com.example.extra.domain.costumeapprovalboard.exception;

import com.example.extra.global.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CostumeApprovalBoardErrorCode implements ErrorCode {
    // 400
    ALREADY_EXIST_COSTUME_APPROVAL_BOARD(HttpStatus.BAD_REQUEST, "이미 글을 작성했습니다."),
    NOT_MASTER_COSTUME_APPROVAL_BOARD(HttpStatus.BAD_REQUEST, "회원이 작성한 글이 아닙니다."),
    NOT_ABLE_TO_ACCESS_COSTUME_APPROVAL_BOARD(HttpStatus.BAD_REQUEST, "해당 공고에 대한 권한이 없습니다"),
    NOT_ABLE_TO_READ_COSTUME_APPROVAL_BOARD(HttpStatus.BAD_REQUEST, "해당 글을 읽을 권한이 없습니다"),
    NOT_ABLE_TO_DELETE_COSTUME_APPROVAL_BOARD(HttpStatus.BAD_REQUEST, "해당 글을 삭제할 권한이 없습니다"),

    // 404
    NOT_FOUND_COSTUME_APPROVAL_BOARD(HttpStatus.NOT_FOUND, "유효하지 않은 의상 승인 게시글입니다"),
    NOT_FOUND_COSTUME_APPROVAL_BOARD_MEMBER(HttpStatus.NOT_FOUND, "의상 컨펌 작성 이력이 없습니다.");

    private final HttpStatus status;
    private final String message;

    CostumeApprovalBoardErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
