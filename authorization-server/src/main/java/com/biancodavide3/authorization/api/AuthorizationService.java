package com.biancodavide3.authorization.api;

import com.biancodavide3.authorization.token.AccessTokenIssuer;
import com.biancodavide3.authorization.token.AccessTokenValidator;
import com.biancodavide3.authorization.user.UserCredentialsDTO;
import com.biancodavide3.authorization.user.UserCredentialsService;
import com.biancodavide3.clients.authorization.*;
import com.biancodavide3.authorization.token.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthorizationService {

    private final UserCredentialsService userCredentialsService;
    private final AccessTokenIssuer accessTokenIssuer;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenValidator accessTokenValidator;

    public ResponseEntity<AuthenticationResponse> signup(AuthenticationRequest authenticationRequest) {
        String email = authenticationRequest.email();
        String password = passwordEncoder.encode(authenticationRequest.password());
        userCredentialsService.addCredentials(
                new UserCredentialsDTO(
                        email,
                        password,
                        "USER"
                )
        );
        String accessToken = accessTokenIssuer.generateToken(email, Map.of("token-type", "USER", "role", "USER"));
        UUID refreshToken = refreshTokenService.generateToken(email);
        return ResponseEntity.ok(new AuthenticationResponse(accessToken, refreshToken));
    }

    public ResponseEntity<AuthenticationResponse> login(AuthenticationRequest authenticationRequest) {
        String email = authenticationRequest.email();
        Optional<UserCredentialsDTO> optional = userCredentialsService.getCredentials(email);
        if (optional.isEmpty())
            return ResponseEntity.notFound().build();
        String password = passwordEncoder.encode(authenticationRequest.password());
        UserCredentialsDTO userCredentialsDTO = optional.get();
        if (!userCredentialsDTO.password().equals(password))
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        String accessToken = accessTokenIssuer.generateToken(email, Map.of("token-type", "USER", "role", "USER"));
        UUID refreshToken = refreshTokenService.generateToken(email);
        return ResponseEntity.ok(new AuthenticationResponse(accessToken, refreshToken));
    }

    public ResponseEntity<RefreshResponse> refresh(RefreshRequest refreshRequest) {
        UUID refreshToken = refreshRequest.refreshToken();
        String subject = refreshRequest.subject();
        if (!refreshTokenService.isTokenValid(refreshToken, subject))
            return ResponseEntity.badRequest().build();
        String accessToken = accessTokenIssuer.generateToken(subject, Map.of("token-type", "USER", "role", "USER"));
        RefreshResponse body = new RefreshResponse(accessToken, refreshToken);
        return ResponseEntity.ok(body);
    }

    public ResponseEntity<ExchangeResponse> exchange(ExchangeRequest request) {
        String token = request.accessToken();
        String subject = request.subject();
        if (accessTokenValidator.isTokenValid(token, subject))
            return ResponseEntity.badRequest().build();
        String accessToken = accessTokenIssuer.generateToken(subject, Map.of("token-type", "MICROSERVICE", "role", "USER"));
        ExchangeResponse body = new ExchangeResponse(accessToken);
        return ResponseEntity.ok(body);
    }
}
