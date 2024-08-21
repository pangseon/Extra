package com.example.extra.domain.member.dto.service.request;

import com.example.extra.domain.tattoo.dto.controller.TattooCreateControllerRequestDto;
import java.time.LocalDate;

public record MemberUpdateServiceRequestDto(
    String name,
    boolean sex,
    LocalDate birthday,
    String home,
    float height,
    float weight,
    String phone,
    String introduction,
    String license,
    String pros,
    String bank,
    String accountNumber,
    TattooCreateControllerRequestDto tattoo,
    boolean isImageUpdate,
    boolean isImageDelete,
    String imageUrl
) {

}
