package com.biancodavide3.jwt;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RefreshTokenService extends GenericTokenService {
    // todo make this come from vault
    private int refreshTokenExpiration;

    public String generateRefreshToken(String subject) {
        return generateToken(subject, refreshTokenExpiration);
    }

    public String generateRefreshToken(String subject, Map<String, Object> claims) {
        return generateToken(subject, refreshTokenExpiration, claims);
    }
}
