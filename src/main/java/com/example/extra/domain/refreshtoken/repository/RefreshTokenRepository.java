package com.example.extra.domain.refreshtoken.repository;

import com.example.extra.domain.refreshtoken.token.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

}
