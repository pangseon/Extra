package com.example.extra.domain.costumeapprovalboard.mapper.dto;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.costumeapprovalboard.dto.controller.CostumeApprovalBoardExplainCreateRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.controller.CostumeApprovalBoardExplainUpdateControllerRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCreateServiceDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardExplainUpdateServiceRequestDto;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = SPRING)
public interface CostumeApprovalBoardDtoMapper {

    CostumeApprovalBoardCreateServiceDto toCostumeApprovalBoardCreateServiceDto(
        CostumeApprovalBoardExplainCreateRequestDto costumeApprovalBoardExplainCreateRequestDto,
        MultipartFile multipartFile
    );

    CostumeApprovalBoardExplainUpdateServiceRequestDto toCostumeApprovalBoardExplainUpdateServiceRequestDto(
        CostumeApprovalBoardExplainUpdateControllerRequestDto costumeApprovalBoardExplainUpdateControllerRequestDto,
        MultipartFile multipartFile
    );
}

