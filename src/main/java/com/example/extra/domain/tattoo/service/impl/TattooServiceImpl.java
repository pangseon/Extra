package com.example.extra.domain.tattoo.service.impl;


import com.example.extra.domain.tattoo.dto.service.request.TattooCreateServiceRequestDto;
import com.example.extra.domain.tattoo.entity.Tattoo;
import com.example.extra.domain.tattoo.mapper.service.TattooEntityMapper;
import com.example.extra.domain.tattoo.repository.TattooRepository;
import com.example.extra.domain.tattoo.service.TattooService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TattooServiceImpl implements TattooService {

    private final TattooRepository tattooRepository;
    private final TattooEntityMapper tattooEntityMapper;

    @Override
    public void create(final TattooCreateServiceRequestDto requestDto) {
        Tattoo tattoo = tattooEntityMapper.toTattoo(requestDto);
        tattooRepository.save(tattoo);
    }
}
