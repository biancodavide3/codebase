package com.biancodavide3.authorization.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenService {

    // this one both issues and validates opaque refresh tokens (entirely managed by the authorization server)

    private final OpaqueRefreshTokenRepository repository;

    public UUID generateToken(String subject, String role) {
        UUID token = UUID.randomUUID();
        OpaqueRefreshToken entity = OpaqueRefreshToken.builder()
                .token(token)
                .subject(subject)
                .expiration(LocalDate.now().plusWeeks(1))
                .role(role)
                .build();
        repository.save(entity);
        return token;
    }

    public UUID generateToken(String subject) {
        return generateToken(subject, "USER");
    }


    public boolean isTokenValid(UUID token, String subject, String role) {
        if (repository.findById(token).isEmpty())
            return false;
        OpaqueRefreshToken entity = repository.findById(token).get();
        if (!entity.getRole().equals(role) || !entity.getSubject().equals(subject))
            return false;
        if (entity.getExpiration().isBefore(LocalDate.now())) {
            repository.delete(entity);
            return false;
        }
        return true;
    }

    public boolean isTokenValid(UUID token, String subject) {
        return isTokenValid(token, subject, "USER");
    }
}
