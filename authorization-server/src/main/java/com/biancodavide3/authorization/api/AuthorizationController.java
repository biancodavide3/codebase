package com.biancodavide3.authorization.api;

import com.biancodavide3.clients.authorization.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @PostMapping("signup")
    public ResponseEntity<AuthenticationResponse> signup(
            @RequestBody AuthenticationRequest authenticationRequest) {
        return authorizationService.signup(authenticationRequest);
    }

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest authenticationRequest) {
        return authorizationService.login(authenticationRequest);
    }

    @PostMapping("refresh")
    public ResponseEntity<RefreshResponse> refresh(
            @RequestBody RefreshRequest refreshRequest) {
        return authorizationService.refresh(refreshRequest);
    }

    @PostMapping("exchange")
    public ResponseEntity<ExchangeResponse> exchange(
            @RequestBody ExchangeRequest exchangeRequest) {
        return authorizationService.exchange(exchangeRequest);
    }
}
