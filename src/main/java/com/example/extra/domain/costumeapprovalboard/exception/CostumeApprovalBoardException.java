package com.example.extra.domain.costumeapprovalboard.exception;

import com.example.extra.global.exception.CustomException;
import com.example.extra.global.exception.ErrorCode;
import lombok.Getter;
import org.springframework.web.service.annotation.GetExchange;

@Getter
public class CostumeApprovalBoardException extends CustomException {

    public CostumeApprovalBoardException(CostumeApprovalBoardErrorCode errorCode) {
        super(errorCode);
    }
}
