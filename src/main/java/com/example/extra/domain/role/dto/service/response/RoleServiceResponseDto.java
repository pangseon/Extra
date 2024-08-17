package com.example.extra.domain.role.dto.service.response;

import com.example.extra.domain.role.entity.Role;
import com.example.extra.domain.tattoo.dto.controller.TattooCreateControllerRequestDto;
import com.example.extra.domain.tattoo.dto.service.response.TattooReadServiceResponseDto;
import com.example.extra.global.enums.Season;
import lombok.Builder;

@Builder
public record RoleServiceResponseDto(
    Long id,
    String roleName,
    String costume,
    Boolean sex,
    Integer minAge,
    Integer maxAge,
    Integer limitPersonnel,
    Integer currentPersonnel,
    Season season,
    TattooReadServiceResponseDto tattoo
) {
    public static RoleServiceResponseDto from(
        Role role,
        Integer minAge,
        Integer maxAge
    ){
        return RoleServiceResponseDto.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .costume(role.getCostume())
                .sex(role.getSex())
                .minAge(minAge)
                .maxAge(maxAge)
                .limitPersonnel(role.getLimitPersonnel())
                .currentPersonnel(role.getCurrentPersonnel())
                .season(role.getSeason())
                .tattoo(TattooReadServiceResponseDto.from(role.getTattoo()))
            .build();
    }
}
