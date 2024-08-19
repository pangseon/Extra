package com.example.extra.domain.tattoo.controller;


import com.example.extra.domain.tattoo.dto.controller.TattooCreateControllerRequestDto;
import com.example.extra.domain.tattoo.dto.service.request.TattooCreateServiceRequestDto;
import com.example.extra.domain.tattoo.mapper.dto.TattooDtoMapper;
import com.example.extra.domain.tattoo.service.TattooService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
