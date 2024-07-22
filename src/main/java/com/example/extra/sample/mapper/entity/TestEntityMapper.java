package com.example.extra.sample.mapper.entity;


import com.example.extra.sample.dto.service.TestCreateServiceRequestDto;
import com.example.extra.sample.dto.service.TestReadResponseDto;
import com.example.extra.sample.entity.Test;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;


@Mapper(componentModel = ComponentModel.SPRING)
public interface TestEntityMapper {

    Test toTest(TestCreateServiceRequestDto testRequestDto);

    TestReadResponseDto toTestReadResponseDto(Test test);

    List<TestReadResponseDto> toTestReadResponseDtos(List<Test> test);
}
