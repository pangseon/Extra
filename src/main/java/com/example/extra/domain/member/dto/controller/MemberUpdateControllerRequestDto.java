package com.example.extra.domain.member.dto.controller;

import com.example.extra.domain.tattoo.dto.controller.TattooCreateControllerRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record MemberUpdateControllerRequestDto(
    @Schema(description = "이름", example = "홍길동")
    @Size(min = 2, max = 10, message = "최소 2글자, 최대 10글자를 입력해주세요")
    @NotBlank(message = "이름은 필수 입력 정보입니다")
    @Pattern(
        regexp = "^[^\s][\\uAC00-\\uD7AFa-zA-Z\\s]*$",
        message = "이름은 한글, 알파벳, 공백만 가능하고, 공백으로 시작할 수 없습니다."
    )
    String name,
    @Schema(description = "전화번호", example = "01055559999")
    @NotBlank(message = "전화번호는 필수 입력 정보입니다")
    @Pattern(
        regexp = "^\\d{10,11}$",
        message = "전화번호 형식을 지켜주세요. '-' 입력 없이 최소 10, 최대 11글자의 숫자만 입력해주세요"
    )
    String phone,
    @Schema(description = "생년월일(생일)", example = "2001-01-01")
    @NotBlank(message = "생년월일은 필수 입력 정보입니다")
    LocalDate birthday,
    @Schema(description = "성별, true(1) : 남자 / false(0) : 여자")
    @NotBlank(message = "성별은 필수 입력 정보입니다")
    boolean sex,
    @Schema(description = "거주지")
    @Size(max = 255, message = "최대 255글자 입니다")
    @NotBlank(message = "거주지는 필수 입력 정보입니다")
    String home,
    @Schema(description = "키", example = "176.4")
    @NotBlank(message = "키는 필수 입력 정보입니다")
    float height,
    @Schema(description = "체중", example = "59.3")
    @NotBlank(message = "체중은 필수 입력 정보입니다")
    float weight,
    @Schema(description = "자기소개", example = "안녕하세요. 외국어를 잘하는 남자 대학생입니다.")
    String introduction,
    @Schema(description = "자격증", example = "JLPT N3, 토익 스피킹 130")
    String license,
    @Schema(description = "장점", example = "외국어를 할 수 있습니다.")
    String pros,
    @Schema(description = "은행", example = "하나")
    @NotBlank(message = "은행은 필수 입력 정보입니다")
    @Size(max = 10, message = "최대 10글자 입니다")
    String bank,
    @NotBlank(message = "계좌 번호는 필수 입력 정보입니다")
    @Schema(description = "계좌 번호", example = "123-456-78901234")
    @Size(max = 30, message = "최대 30글자 입니다")
    String accountNumber,
    @Schema(description = "타투")
    @NotNull(message = "타투는 필수 입력 정보입니다")
    TattooCreateControllerRequestDto tattoo,
    @Schema(description = "이미지 변경 여부", defaultValue = "false")
    @NotBlank(message = "이미지 변경 여부는 필수 입력 정보입니다")
    boolean isImageChange
) {

}
