package com.biancodavide3.authorization.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AccessTokenIssuer {

    // this one only lives within the authorization server as it is the only one who issues tokens

    private final TokenProperties properties;

    public String generateToken(
            String subject,
            Map<String, Object> extraClaims
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + properties.getAccessTokenExpiration()))
                .signWith(signingKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(String subject) {
        return generateToken(subject, new HashMap<>());
    }

    private Key signingKey() {
        byte[] bytes = Decoders.BASE64.decode(properties.getSecretKey());
        return Keys.hmacShaKeyFor(bytes);
    }
}
