package com.example.extra.domain.costumeapprovalboard.dto.service;

import com.example.extra.global.enums.ApplyStatus;
import lombok.Builder;

@Builder
public record CostumeApprovalBoardApplyStatusUpdateServiceRequestDto(
    ApplyStatus costumeApprove
) {

}
