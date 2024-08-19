package com.example.extra.domain.role.dto.controller;

import com.example.extra.domain.tattoo.dto.controller.TattooCreateControllerRequestDto;
import com.example.extra.global.enums.Season;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record RoleUpdateControllerRequestDto(
    @NotBlank(message = "roleName은 필수 정보 입니다.")
    @Pattern(
        regexp = "^[\\uAC00-\\uD7AFa-zA-Z]{1,10}$",
        message = "roleName은 한글과 영어 알파벳으로만 구성되어야 하며, 길이는 10자 이하이어야 합니다."
    )
    String roleName,
    @NotBlank(message = "costume은 필수 정보 입니다.")
    @Size(max = 50, message = "costume은 최대 50자입니다.")
    String costume,
    @NotNull(message = "sex는 필수 정보 입니다.")
    Boolean sex,
    @NotNull(message = "minAge는 필수 정보 입니다.")
    LocalDate minAge,
    @NotNull(message = "maxAge는 필수 정보 입니다.")
    LocalDate maxAge,
    @NotNull(message = "limitPersonnel은 필수 정보 입니다.")
    @DecimalMin(value = "0", message = "0 이상의 값이어야 합니다.")
    @DecimalMax(value = "65535", message = "65535 이하의 값이어야 합니다.")
    Integer limitPersonnel, // small int unsigned
    @NotNull(message = "currentPersonnel은 필수 정보 입니다.")
    @DecimalMin(value = "0", message = "0 이상의 값이어야 합니다.")
    @DecimalMax(value = "65535", message = "65535 이하의 값이어야 합니다.")
    Integer currentPersonnel, // small int unsigned
    @NotNull(message = "season은 필수 정보입니다")
    @Pattern(
        regexp = "^(SPRING|SUMMER|AUTUMN|WINTER)$\n",
        message = "SPRING, SUMMER, AUTUMN, WINTER 중 하나를 입력해주세요"
    )
    Season season,
    @NotNull(message = "tattoo는 필수 정보 입니다.")
    TattooCreateControllerRequestDto tattoo
) {

}
