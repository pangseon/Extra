package com.example.extra.sample.mapper.dto;


import com.example.extra.sample.dto.controller.TestCreateControllerRequestDto;
import com.example.extra.sample.dto.service.TestCreateServiceRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface TestDtoMapper {

    TestCreateServiceRequestDto toTestServiceRequestDto(
        TestCreateControllerRequestDto controllerRequestDto);
}
