package com.example.extra.domain.costumeapprovalboard.mapper.dto;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.costumeapprovalboard.dto.controller.CostumeApprovalBoardExplainCreateControllerRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.controller.CostumeApprovalBoardUpdateControllerRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.request.CostumeApprovalBoardExplainCreateServiceRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.request.CostumeApprovalBoardUpdateServiceRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface CostumeApprovalBoardDtoMapper {

    CostumeApprovalBoardExplainCreateServiceRequestDto toCostumeApprovalBoardExplainCreateServiceRequestDto(
        CostumeApprovalBoardExplainCreateControllerRequestDto costumeApprovalBoardExplainCreateControllerRequestDto
    );

    CostumeApprovalBoardUpdateServiceRequestDto toCostumeApprovalBoardUpdateServiceRequestDto(
        CostumeApprovalBoardUpdateControllerRequestDto costumeApprovalBoardUpdateControllerRequestDto
    );
}

