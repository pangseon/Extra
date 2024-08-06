package com.example.extra.domain.costumeapprovalboard.dto.controller;

import com.example.extra.global.enums.ApplyStatus;
import jakarta.validation.constraints.NotNull;

public record CostumeApprovalBoardApplyStatusUpdateControllerRequestDto(
    @NotNull ApplyStatus costumeApprove
) {

}
