package com.example.extra.domain.applicationrequest.repository;

import com.example.extra.domain.applicationrequest.entity.ApplicationRequestCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRequestCompanyRepository extends
    JpaRepository<ApplicationRequestCompany, Long> {

}
