package com.biancodavide3.jwt;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RefreshTokenService extends GenericTokenService {

    private final int REFRESH_TOKEN_EXPIRATION = 168;

    public String generateRefreshToken(String subject) {
        return generateToken(subject, REFRESH_TOKEN_EXPIRATION);
    }

    public String generateRefreshToken(String subject, Map<String, Object> claims) {
        return generateToken(subject, REFRESH_TOKEN_EXPIRATION, claims);
    }
}
