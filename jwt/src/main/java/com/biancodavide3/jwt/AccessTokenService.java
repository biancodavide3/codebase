package com.biancodavide3.jwt;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AccessTokenService extends GenericTokenService {

    private final int ACCESS_TOKEN_EXPIRATION = 24;

    public String generateAccessToken(String subject) {
        return generateToken(subject, ACCESS_TOKEN_EXPIRATION);
    }

    public String generateAccessToken(String subject, Map<String, Object> claims) {
        return generateToken(subject, ACCESS_TOKEN_EXPIRATION, claims);
    }
}
