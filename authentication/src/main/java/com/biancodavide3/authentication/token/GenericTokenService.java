package com.biancodavide3.authentication.token;

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

    // todo make this come from vault so that every microservice has access to it
    private final String SECRET_KEY = "7249cc81f2a4531f6ce0a472a3aaf283723fa469919a7dda4ba7fe43191e5ea25e8f341a1376da0aea796ff95af7ea7bbc9cdd22135e328164e77c5360cafa5d9a052c12f0d8cc012156b99e050829bf58700579c9fb13db71419f4fff7b6314adac7ec16667ee2a9ee508660288167f1e851d583f7de5811e28fac818604f891389fb08e1ad6dc5721466df0e28ae7fc09b9d3945b40e6a85ae0e3e3dcd546b09f12e43b66d45d568a3de69f52ae8c01f6e3f8761cb6f166da858ce288a1f7148c39cb0492b0b40ee155be64f6a85a72c1a29f1b211479de50e47a2984a8e2b6ba5ccffef9f62cb4c2e9dbee16e9e9aeb8b8bfd06b481f979f8e6c25989a709";

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
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
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
