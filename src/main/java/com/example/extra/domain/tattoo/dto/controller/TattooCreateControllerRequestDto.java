package com.example.extra.domain.tattoo.dto.controller;

public record TattooCreateControllerRequestDto(
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
