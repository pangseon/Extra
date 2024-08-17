package com.example.extra.domain.tattoo.dto.service.response;

import com.example.extra.domain.tattoo.entity.Tattoo;
import lombok.Builder;

@Builder
public record TattooReadServiceResponseDto(
    Boolean face,
    Boolean chest,
    Boolean arm,
    Boolean leg,
    Boolean shoulder,
    Boolean back,
    Boolean hand,
    Boolean feet
) {
    public static TattooReadServiceResponseDto from(Tattoo tattoo){
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
