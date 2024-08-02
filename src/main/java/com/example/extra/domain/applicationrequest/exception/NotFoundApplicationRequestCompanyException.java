package com.example.extra.domain.applicationrequest.exception;


import com.example.extra.global.exception.CustomException;

public class NotFoundApplicationRequestCompanyException extends CustomException {
    // 이미 지원자 측에서 취소한 요청에 대해서 지원 승인을 하려 할 때
    // front 단에서는 취소하지 않은 상태로 보여지는데 실제로는 DB에서 삭제된 경우 있을 수 있어서 서버에서 검증
    public NotFoundApplicationRequestCompanyException(final ApplicationRequestErrorCode errorCode) {
        super(errorCode);
    }
}