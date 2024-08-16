package com.example.extra.domain.costumeapprovalboard.dto.service;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record CostumeApprovalBoardCreateServiceDto(
    String imageExplain,
    MultipartFile multipartFile
) {

}
