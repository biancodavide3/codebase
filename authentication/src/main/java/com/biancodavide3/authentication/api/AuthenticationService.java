package com.biancodavide3.authentication.api;

import com.biancodavide3.clients.authentication.AuthenticationRequest;
import com.biancodavide3.clients.authentication.AuthenticationResponse;
import com.biancodavide3.clients.user.UserSecurityInformationClient;
import com.biancodavide3.clients.user.UserSecurityInformationRequest;
import com.biancodavide3.clients.user.UserSecurityInformationResponse;
import com.biancodavide3.authentication.token.AccessTokenService;
import com.biancodavide3.authentication.token.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserSecurityInformationClient client;
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<AuthenticationResponse> signup(AuthenticationRequest authenticationRequest) {
        String email = authenticationRequest.email();
        String password = passwordEncoder.encode(authenticationRequest.password());
        UserSecurityInformationRequest request = new UserSecurityInformationRequest(email, password);
        ResponseEntity<UserSecurityInformationResponse> response = client.addUserSecurityInformation(request);
        if (response.getStatusCode().isSameCodeAs(HttpStatus.CONFLICT))
            return ResponseEntity.status(HttpStatus.CONFLICT).build();  // email taken
        String accessToken = accessTokenService.generateAccessToken(email);
        String refreshToken = refreshTokenService.generateRefreshToken(email);
        return ResponseEntity.ok(new AuthenticationResponse(accessToken, refreshToken));
    }

    public ResponseEntity<AuthenticationResponse> login(AuthenticationRequest authenticationRequest) {
        String email = authenticationRequest.email();
        String password = passwordEncoder.encode(authenticationRequest.password());
        UserSecurityInformationRequest request = new UserSecurityInformationRequest(email, password);
        ResponseEntity<UserSecurityInformationResponse> response = client.getUserSecurityInformation(request);
        HttpStatusCode statusCode = response.getStatusCode();
        if (statusCode.isSameCodeAs(HttpStatus.NOT_FOUND))
            return ResponseEntity.notFound().build();   // email not found
        if (statusCode.isSameCodeAs(HttpStatus.CONFLICT))
            return new ResponseEntity<>(HttpStatus.CONFLICT);   // wrong password
        String accessToken = accessTokenService.generateAccessToken(email);
        String refreshToken = refreshTokenService.generateRefreshToken(email);
        return ResponseEntity.ok(new AuthenticationResponse(accessToken, refreshToken));
    }
}
