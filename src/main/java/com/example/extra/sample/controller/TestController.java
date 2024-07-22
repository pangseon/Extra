package com.example.extra.sample.controller;


import com.example.extra.sample.dto.controller.TestCreateControllerRequestDto;
import com.example.extra.sample.dto.service.TestCreateServiceRequestDto;
import com.example.extra.sample.dto.service.TestReadResponseDto;
import com.example.extra.sample.mapper.dto.TestDtoMapper;
import com.example.extra.sample.service.TestService;
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
        @RequestBody TestCreateControllerRequestDto controllerRequestDto
    ) {
        TestCreateServiceRequestDto serviceRequestDto = testDtoMapper.toTestServiceRequestDto(
            controllerRequestDto);
        testService.create(serviceRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
