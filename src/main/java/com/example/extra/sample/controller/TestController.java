package com.example.extra.sample.controller;

import com.mju.lighthouseai.global.security.UserDetailsImpl;
import com.mju.lighthouseai.sample.dto.controller.TestCreateControllerRequestDto;
import com.mju.lighthouseai.sample.dto.service.TestCreateServiceRequestDto;
import com.mju.lighthouseai.sample.dto.service.TestReadResponseDto;
import com.mju.lighthouseai.sample.mapper.dto.TestDtoMapper;
import com.mju.lighthouseai.sample.service.TestService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class TestController {

    private final TestService testService;
    private final TestDtoMapper testDtoMapper;

    @GetMapping("/tests/{testId}")
    public ResponseEntity<TestReadResponseDto> get(
        @PathVariable(name = "testId") Long testId
    ) {
        TestReadResponseDto responseDto = testService.get(testId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/tests")
    public ResponseEntity<List<TestReadResponseDto>> getAll(
        @RequestParam("page") Integer pageNumber
    ) {
        List<TestReadResponseDto> responseDto = testService.getAll(pageNumber);
        return ResponseEntity.ok(responseDto);
    }



    @PostMapping("/tests")
    public ResponseEntity<Void> create(
        @RequestBody TestCreateControllerRequestDto controllerRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
        TestCreateServiceRequestDto serviceRequestDto = testDtoMapper.toTestServiceRequestDto(
            controllerRequestDto);
        testService.create(serviceRequestDto, userDetailsImpl.user());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
