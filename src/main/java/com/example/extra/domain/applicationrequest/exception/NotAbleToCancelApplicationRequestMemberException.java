package com.example.extra.domain.applicationrequest.exception;


import com.example.extra.global.exception.CustomException;

public class NotAbleToCancelApplicationRequestMemberException extends CustomException {
    // 지원 요청이 승인된 상태에서 지원 요청을 취소하려 할 때
    // front 단에서는 승인 대기 상태로 보여지는데 실제로는 승인이 된 경우 있을 수 있어서 서버에서 검증
    public NotAbleToCancelApplicationRequestMemberException(final ApplicationRequestErrorCode errorCode) {
        super(errorCode);
    }
}