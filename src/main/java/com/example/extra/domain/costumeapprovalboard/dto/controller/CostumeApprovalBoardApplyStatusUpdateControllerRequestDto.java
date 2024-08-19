package com.example.extra.domain.costumeapprovalboard.dto.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CostumeApprovalBoardApplyStatusUpdateControllerRequestDto(
    @NotNull(message = "costumeApprove는 필수 입력 정보입니다")
    @Pattern(
        regexp = "^(APPLIED|REJECTED|APPROVED)$",
        message = "APPLIED, REJECTED, APPROVED 중 하나를 입력해주세요"
    )
    String costumeApprove
) {

}
