package com.example.extra.sample.service;



import com.example.extra.sample.dto.service.TestCreateServiceRequestDto;
import com.example.extra.sample.dto.service.TestReadResponseDto;
import java.util.List;


public interface TestService {

    void create(TestCreateServiceRequestDto testRequestDto);

    TestReadResponseDto get(Long id);

    List<TestReadResponseDto> getAll(int pageNumber);
}
