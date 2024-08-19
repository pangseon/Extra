package com.example.extra.domain.tattoo.dto.controller;

import io.swagger.v3.oas.annotations.media.Schema;

public record TattooCreateControllerRequestDto(
    @Schema(defaultValue = "false")
    Boolean face,
    @Schema(defaultValue = "false")
    Boolean chest,
    @Schema(defaultValue = "false")
    Boolean arm,
    @Schema(defaultValue = "false")
    Boolean leg,
    @Schema(defaultValue = "false")
    Boolean shoulder,
    @Schema(defaultValue = "false")
    Boolean back,
    @Schema(defaultValue = "false")
    Boolean hand,
    @Schema(defaultValue = "false")
    Boolean feet
) {

}
