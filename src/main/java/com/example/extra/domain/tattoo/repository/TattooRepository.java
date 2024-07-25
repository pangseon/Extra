package com.example.extra.domain.tattoo.repository;

import com.example.extra.domain.tattoo.entity.Tattoo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TattooRepository extends JpaRepository<Tattoo, Long> {

}
