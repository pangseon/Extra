package com.example.extra.domain.tattoo.dto.controller;

import jakarta.validation.constraints.NotNull;

public record TattooCreateControllerRequestDto(
    @NotNull(message = "face는 필수 정보 입니다.")
    Boolean face,
    @NotNull(message = "chest는 필수 정보 입니다.")
    Boolean chest,
    @NotNull(message = "arm은 필수 정보 입니다.")
    Boolean arm,
    @NotNull(message = "leg는 필수 정보 입니다.")
    Boolean leg,
    @NotNull(message = "shoulder는 필수 정보 입니다.")
    Boolean shoulder,
    @NotNull(message = "back은 필수 정보 입니다.")
    Boolean back,
    @NotNull(message = "hand는 필수 정보 입니다.")
    Boolean hand,
    @NotNull(message = "feet는 필수 정보 입니다.")
    Boolean feet
) {

}
