package com.biancodavide3.authentication.api;

import com.biancodavide3.clients.authentication.AuthenticationRequest;
import com.biancodavide3.clients.authentication.AuthenticationResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("signup")
    public ResponseEntity<AuthenticationResponse> signup(
            @RequestBody AuthenticationRequest authenticationRequest) {
        return authenticationService.signup(authenticationRequest);
    }

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest authenticationRequest) {
        return authenticationService.login(authenticationRequest);
    }
}
