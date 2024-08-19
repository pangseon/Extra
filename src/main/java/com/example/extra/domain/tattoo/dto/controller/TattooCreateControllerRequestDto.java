package com.example.extra.domain.tattoo.dto.controller;

import io.swagger.v3.oas.annotations.media.Schema;

public record TattooCreateControllerRequestDto(
    @Schema(defaultValue = "false")
    boolean face,
    @Schema(defaultValue = "false")
    boolean chest,
    @Schema(defaultValue = "false")
    boolean arm,
    @Schema(defaultValue = "false")
    boolean leg,
    @Schema(defaultValue = "false")
    boolean shoulder,
    @Schema(defaultValue = "false")
    boolean back,
    @Schema(defaultValue = "false")
    boolean hand,
    @Schema(defaultValue = "false")
    boolean feet

) {

}
