package com.biancodavide3.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class GenericTokenService {

    // todo make this come from vault
    private String secretKey;

    // creation

    protected String generateToken(
            String subject,
            int expiration,
            Map<String, Object> extraClaims
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + hoursToMillis(expiration)))
                .signWith(signingKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    protected String generateToken(String subject, int expiration) {
        return generateToken(subject, expiration, new HashMap<>());
    }

    private long hoursToMillis(int expiration) {
        return expiration * 60L * 60L * 1000L;
    }

    private Key signingKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    // validation

    protected <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(signingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    protected boolean isTokenValid(String token, String subject) {
        String extractedSubject = extractClaim(token, Claims::getSubject);
        return extractedSubject.equals(subject) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date extractedDate = extractClaim(token, Claims::getExpiration);
        return extractedDate.before(new Date());
    }

}
