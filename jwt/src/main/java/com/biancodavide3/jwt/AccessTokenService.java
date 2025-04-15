package com.biancodavide3.jwt;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AccessTokenService extends GenericTokenService {

    // todo make this come from vault
    private int accessTokenExpiration;

    public String generateAccessToken(String subject) {
        return generateToken(subject, accessTokenExpiration);
    }

    public String generateAccessToken(String subject, Map<String, Object> claims) {
        return generateToken(subject, accessTokenExpiration, claims);
    }
}
