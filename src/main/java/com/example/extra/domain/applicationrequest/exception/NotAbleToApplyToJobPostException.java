package com.example.extra.domain.applicationrequest.exception;


import com.example.extra.global.exception.CustomException;

public class NotAbleToApplyToJobPostException extends CustomException {
    // 공고가 모집 마감 되었는데 지원하려 할 때
    // front 단에서는 모집 중 상태로 보여지는데 실제로는 모집 마감이 된 경우 있을 수 있어서 서버에서 검증
    public NotAbleToApplyToJobPostException(final ApplicationRequestErrorCode errorCode) {
        super(errorCode);
    }
}