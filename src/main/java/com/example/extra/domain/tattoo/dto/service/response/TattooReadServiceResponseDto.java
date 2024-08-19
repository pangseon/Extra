package com.example.extra.domain.tattoo.dto.service.response;

import com.example.extra.domain.tattoo.entity.Tattoo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record TattooReadServiceResponseDto(
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
