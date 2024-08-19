package com.example.extra.domain.costumeapprovalboard.dto.controller;

import com.example.extra.global.enums.ApplyStatus;
import jakarta.validation.constraints.NotNull;

public record CostumeApprovalBoardApplyStatusUpdateControllerRequestDto(
    @NotNull(message = "유효한 costumeApprove 값을 입력해주세요. 가능한 값: 'APPLIED', 'REJECTED', 'APPROVED'")
    ApplyStatus costumeApprove
) {

}
