package com.example.extra.domain.tattoo.dto.service.request;

public record TattooCreateServiceRequestDto(
    boolean face,
    boolean chest,
    boolean arm,
    boolean leg,
    boolean shoulder,
    boolean back,
    boolean hand,
    boolean feet
) {

}
