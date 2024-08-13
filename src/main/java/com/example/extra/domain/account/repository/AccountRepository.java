package com.example.extra.domain.account.repository;

import com.example.extra.domain.account.entity.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findByRefreshToken(String refreshToken);
}
