package com.example.extra.sample.repository;

import com.mju.lighthouseai.sample.entity.Test;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {

    List<Test> findByName(String name);

    Slice<Test> findAllBy(Pageable pageable);
}
