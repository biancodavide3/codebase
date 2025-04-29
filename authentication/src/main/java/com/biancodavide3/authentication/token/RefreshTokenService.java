package com.biancodavide3.authentication.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class RefreshTokenService extends GenericTokenService {

    private final int REFRESH_TOKEN_EXPIRATION = 168;

    private final TokenRepository repository;

    public String generateRefreshToken(String subject) {
        return generateRefreshToken(subject, new HashMap<>());
    }

    public String generateRefreshToken(String subject, Map<String, Object> claims) {
        String token = generateToken(subject, REFRESH_TOKEN_EXPIRATION, claims);
        Token t = Token.builder()
                .token(token)
                .expired(false)
                .revoked(false)
                .build();
        repository.save(t);
        return token;
    }
}
