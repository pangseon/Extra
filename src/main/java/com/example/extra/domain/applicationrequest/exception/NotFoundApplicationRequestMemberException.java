package com.example.extra.domain.applicationrequest.exception;


import com.example.extra.global.exception.CustomException;

public class NotFoundApplicationRequestMemberException extends CustomException {
    // 이미 회사 측에서 삭제한 역할에 대해서 지원 취소를 하려 할 때
    // front 단에서는 지원한 상태로 보여지는데 실제로는 DB에서 삭제된 경우 있을 수 있어서 서버에서 검증
    public NotFoundApplicationRequestMemberException(final ApplicationRequestErrorCode errorCode) {
        super(errorCode);
    }
}