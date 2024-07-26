package com.example.extra.domain.tattoo.dto.service.request;

public record TattooCreateServiceRequestDto(
    Boolean face,
    Boolean chest,
    Boolean arm,
    Boolean leg,
    Boolean shoulder,
    Boolean back,
    Boolean hand,
    Boolean feet,
    String etc
) {

}
