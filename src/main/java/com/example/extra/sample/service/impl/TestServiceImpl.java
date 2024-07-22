package com.example.extra.sample.service.impl;

import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.sample.dto.service.TestCreateServiceRequestDto;
import com.mju.lighthouseai.sample.dto.service.TestReadResponseDto;
import com.mju.lighthouseai.sample.entity.Test;
import com.mju.lighthouseai.sample.exception.NotFoundTestException;
import com.mju.lighthouseai.sample.exception.TestErrorCode;
import com.mju.lighthouseai.sample.mapper.entity.TestEntityMapper;
import com.mju.lighthouseai.sample.repository.TestRepository;
import com.mju.lighthouseai.sample.service.TestService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final TestEntityMapper testEntityMapper;

    // TestCreateServiceRequestDto -> Test
    @Override
    public void create(final TestCreateServiceRequestDto testRequestDto, User user) {
        // Mapper로 만들기
        Test test = testEntityMapper.toTest(testRequestDto);

        /* Builder로 만들기
            Test test = Test.builder()
            .name(testRequestDto.name())
            .age(testRequestDto.age())
            .build();
         */

        testRepository.save(test);
    }

    // Test -> TestCreateServiceRequestDto
    @Override
    public TestReadResponseDto get(Long id) {
        Test test = testRepository.findById(id)
            .orElseThrow(() -> new NotFoundTestException(TestErrorCode.NOT_FOUND_TEST));
        return testEntityMapper.toTestReadResponseDto(test);
    }

    // List<Test> -> List<TestCreateServiceRequestDto>
    @Override
    public List<TestReadResponseDto> getAll(int pageNumber) {
        Slice<Test> tests = testRepository.findAllBy(PageRequest.of(pageNumber, 9));
        return testEntityMapper.toTestReadResponseDtos(tests.getContent());
    }
}
