package com.biancodavide3.authorization.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OpaqueRefreshTokenRepository extends JpaRepository<OpaqueRefreshToken, UUID> {
}
