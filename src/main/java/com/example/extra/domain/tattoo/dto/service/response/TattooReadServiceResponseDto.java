package com.example.extra.domain.tattoo.dto.service.response;

import com.example.extra.domain.tattoo.entity.Tattoo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record TattooReadServiceResponseDto(
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

    public static TattooReadServiceResponseDto from(Tattoo tattoo) {
        return TattooReadServiceResponseDto.builder()
            .face(tattoo.getFace())
            .chest(tattoo.getChest())
            .arm(tattoo.getArm())
            .leg(tattoo.getLeg())
            .shoulder(tattoo.getShoulder())
            .back(tattoo.getBack())
            .hand(tattoo.getHand())
            .feet(tattoo.getFeet())
            .build();
    }
}
