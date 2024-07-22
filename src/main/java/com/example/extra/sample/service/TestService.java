package com.example.extra.sample.service;


import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.sample.dto.service.TestCreateServiceRequestDto;
import com.mju.lighthouseai.sample.dto.service.TestReadResponseDto;
import java.util.List;


public interface TestService {

    void create(TestCreateServiceRequestDto testRequestDto, User user);

    TestReadResponseDto get(Long id);

    List<TestReadResponseDto> getAll(int pageNumber);
}
