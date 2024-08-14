package com.example.extra.domain.tattoo.service;



import com.example.extra.domain.tattoo.dto.service.request.TattooCreateServiceRequestDto;


public interface TattooService {
    void create(TattooCreateServiceRequestDto requestDto);

}
