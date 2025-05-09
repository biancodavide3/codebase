package com.biancodavide3.authentication.api;

import com.biancodavide3.clients.authentication.AuthenticationRequest;
import com.biancodavide3.clients.authentication.AuthenticationResponse;
import com.biancodavide3.clients.user.UserSecurityInformationClient;
import com.biancodavide3.clients.user.UserSecurityInformationRequest;
import com.biancodavide3.authentication.token.AccessTokenService;
import com.biancodavide3.authentication.token.RefreshTokenService;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
        try {
            client.addUserSecurityInformation(request);
        } catch (FeignException.Conflict e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();  // email taken
        }
        String accessToken = accessTokenService.generateAccessToken(email);
        String refreshToken = refreshTokenService.generateRefreshToken(email);
        return ResponseEntity.ok(new AuthenticationResponse(accessToken, refreshToken));
    }

    public ResponseEntity<AuthenticationResponse> login(AuthenticationRequest authenticationRequest) {
        String email = authenticationRequest.email();
        String password = passwordEncoder.encode(authenticationRequest.password());
        UserSecurityInformationRequest request = new UserSecurityInformationRequest(email, password);
        try {
            client.getUserSecurityInformation(request);
        } catch (FeignException.NotFound e) {
            return ResponseEntity.notFound().build();   // email not found
        } catch (FeignException.Conflict e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // wrong password
        }
        String accessToken = accessTokenService.generateAccessToken(email);
        String refreshToken = refreshTokenService.generateRefreshToken(email);
        return ResponseEntity.ok(new AuthenticationResponse(accessToken, refreshToken));
    }
}
