package com.example.extra.domain.member.dto.service.response;

import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.tattoo.dto.service.response.TattooReadServiceResponseDto;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record MemberReadServiceResponseDto(
    String name,
    Boolean sex,
    LocalDate birthday,
    String home,
    Float height,
    Float weight,
    String introduction,
    String license,
    String pros,
    String imageUrl,
    TattooReadServiceResponseDto tattoo
) {
    public static MemberReadServiceResponseDto from(Member member){
        return MemberReadServiceResponseDto.builder()
                .name(member.getName())
                .sex(member.getSex())
                .birthday(member.getBirthday())
                .home(member.getHome())
                .height(member.getHeight())
                .weight(member.getWeight())
                .introduction(member.getIntroduction())
                .license(member.getLicense())
                .pros(member.getPros())
                .imageUrl(member.getAccount().getImageUrl())
                .tattoo(TattooReadServiceResponseDto.from(member.getTattoo()))
            .build();
    }
}
