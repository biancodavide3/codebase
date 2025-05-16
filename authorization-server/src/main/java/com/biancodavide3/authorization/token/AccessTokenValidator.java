package com.biancodavide3.authorization.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class AccessTokenValidator {

    // this one needs to be included in every microservice as validation is performed locally

    private final TokenProperties properties;

    public boolean isTokenValid(String token, String subject) {
        String extractedSubject = extractClaim(token, Claims::getSubject);
        return extractedSubject.equals(subject) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date extractedDate = extractClaim(token, Claims::getExpiration);
        return extractedDate.before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(signingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    private Key signingKey() {
        byte[] bytes = Decoders.BASE64.decode(properties.getSecretKey());
        return Keys.hmacShaKeyFor(bytes);
    }
}
