package com.example.extra.domain.tattoo.controller;


import com.example.extra.domain.tattoo.dto.controller.TattooCreateControllerRequestDto;
import com.example.extra.domain.tattoo.dto.service.request.TattooCreateServiceRequestDto;
import com.example.extra.domain.tattoo.mapper.dto.TattooDtoMapper;
import com.example.extra.domain.tattoo.service.TattooService;
import com.example.extra.sample.dto.controller.TestCreateControllerRequestDto;
import com.example.extra.sample.dto.service.TestCreateServiceRequestDto;
import com.example.extra.sample.dto.service.TestReadResponseDto;
import com.example.extra.sample.mapper.dto.TestDtoMapper;
import com.example.extra.sample.service.TestService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/tattoos")
@RestController
public class TattooController {

    private final TattooService tattooService;
    private final TattooDtoMapper tattooDtoMapper;

    @PostMapping("")
    public ResponseEntity<Void> create(
        @RequestBody TattooCreateControllerRequestDto requestDto
    ) {
        TattooCreateServiceRequestDto createServiceRequestDto = tattooDtoMapper.toTattooCreateServiceRequestDto(
            requestDto);
        tattooService.create(createServiceRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
