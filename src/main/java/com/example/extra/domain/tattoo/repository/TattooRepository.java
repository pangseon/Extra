package com.example.extra.domain.tattoo.repository;

import com.example.extra.domain.tattoo.dto.controller.TattooCreateControllerRequestDto;
import com.example.extra.domain.tattoo.dto.service.request.TattooCreateServiceRequestDto;
import com.example.extra.domain.tattoo.entity.Tattoo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TattooRepository extends JpaRepository<Tattoo, Long> {
    // TODO - member 부분 dto 구조 수정 시 삭제
    @Query("SELECT t FROM Tattoo t WHERE " +
        "t.face = :#{#dto.face} AND " +
        "t.chest = :#{#dto.chest} AND " +
        "t.arm = :#{#dto.arm} AND " +
        "t.leg = :#{#dto.leg} AND " +
        "t.shoulder = :#{#dto.shoulder} AND " +
        "t.back = :#{#dto.back} AND " +
        "t.hand = :#{#dto.hand} AND " +
        "t.feet = :#{#dto.feet}")
    Optional<Tattoo> findByTattooCreateServiceRequestDto(@Param("dto") TattooCreateServiceRequestDto dto);
    @Query("SELECT t FROM Tattoo t WHERE " +
        "t.face = :#{#dto.face} AND " +
        "t.chest = :#{#dto.chest} AND " +
        "t.arm = :#{#dto.arm} AND " +
        "t.leg = :#{#dto.leg} AND " +
        "t.shoulder = :#{#dto.shoulder} AND " +
        "t.back = :#{#dto.back} AND " +
        "t.hand = :#{#dto.hand} AND " +
        "t.feet = :#{#dto.feet}")
    Optional<Tattoo> findByTattooCreateControllerRequestDto(@Param("dto") TattooCreateControllerRequestDto dto);
}
