package com.example.extra.domain.costumeapprovalboard.dto.service;

import org.springframework.web.multipart.MultipartFile;

public record CostumeApprovalBoardExplainUpdateServiceRequestDto(
    String imageExplain,
    Boolean imageChange)
{

}
