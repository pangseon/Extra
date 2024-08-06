package com.example.extra.domain.costumeapprovalboard.dto.controller;

import com.example.extra.global.enums.ApplyStatus;
import jakarta.validation.constraints.NotNull;

public record CostumeApprovalBoardApplyStatusUpdateControllerRequestDto(
    @NotNull(message = "유효하지 않은 apply status입니다.")
    ApplyStatus costumeApprove
) {

}
