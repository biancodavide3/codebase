package com.biancodavide3.authentication.api;

import com.biancodavide3.authentication.token.AccessTokenService;
import com.biancodavide3.authentication.token.RefreshTokenService;
import com.biancodavide3.clients.authentication.AuthenticationRequest;
import com.biancodavide3.clients.authentication.AuthenticationResponse;
import com.biancodavide3.clients.user.UserSecurityInformationClient;
import com.biancodavide3.clients.user.UserSecurityInformationRequest;
import com.biancodavide3.clients.user.UserSecurityInformationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService underTest;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private AccessTokenService accessTokenService;
    @Mock
    private RefreshTokenService refreshTokenService;
    @Mock
    private UserSecurityInformationClient client;

    @Test
    void itShouldSignupCorrectly() {
        // given
        String email = "hello@gmail.com";
        String password = "123";
        String encodedPassword = "abc123";
        String accessToken = "access";
        String refreshToken = "refresh";

        UserSecurityInformationRequest clientRequest = new UserSecurityInformationRequest(email, encodedPassword);
        ResponseEntity<UserSecurityInformationResponse> clientResponse =
                ResponseEntity.ok(new UserSecurityInformationResponse(email, encodedPassword));
        AuthenticationRequest request = new AuthenticationRequest(email, password);

        given(encoder.encode(password)).willReturn(encodedPassword);
        given(accessTokenService.generateAccessToken(email)).willReturn(accessToken);
        given(refreshTokenService.generateRefreshToken(email)).willReturn(refreshToken);
        given(client.addUserSecurityInformation(clientRequest)).willReturn(clientResponse);
        // when
        ResponseEntity<AuthenticationResponse> response = underTest.signup(request);
        // then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertNotNull(response.getBody());
        assertThat(response.getBody().accessToken()).isEqualTo(accessToken);
        assertNotNull(response.getBody().refreshToken());
        assertThat(response.getBody().refreshToken()).isEqualTo(refreshToken);
    }

    @Test
    void itShouldNotSignupConflict() {
        // given
        String email = "hello@gmail.com";
        String password = "123";
        String encodedPassword = "abc123";

        UserSecurityInformationRequest clientRequest = new UserSecurityInformationRequest(email, encodedPassword);
        ResponseEntity<UserSecurityInformationResponse> clientResponse =
                new ResponseEntity<>(new UserSecurityInformationResponse(email, encodedPassword), HttpStatus.CONFLICT);
        AuthenticationRequest request = new AuthenticationRequest(email, password);

        given(encoder.encode(password)).willReturn(encodedPassword);
        given(client.addUserSecurityInformation(clientRequest)).willReturn(clientResponse);
        // when
        ResponseEntity<AuthenticationResponse> response = underTest.signup(request);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void itShouldLogin() {
        // given
        String email = "hello@gmail.com";
        String password = "123";
        String encodedPassword = "abc123";
        String accessToken = "access";
        String refreshToken = "refresh";

        UserSecurityInformationRequest clientRequest = new UserSecurityInformationRequest(email, encodedPassword);
        ResponseEntity<UserSecurityInformationResponse> clientResponse =
                ResponseEntity.ok(new UserSecurityInformationResponse(email, encodedPassword));
        AuthenticationRequest request = new AuthenticationRequest(email, password);

        given(encoder.encode(password)).willReturn(encodedPassword);
        given(accessTokenService.generateAccessToken(email)).willReturn(accessToken);
        given(refreshTokenService.generateRefreshToken(email)).willReturn(refreshToken);
        given(client.getUserSecurityInformation(clientRequest)).willReturn(clientResponse);
        // when
        ResponseEntity<AuthenticationResponse> response = underTest.login(request);
        // then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertNotNull(response.getBody());
        assertThat(response.getBody().accessToken()).isEqualTo(accessToken);
        assertNotNull(response.getBody().refreshToken());
        assertThat(response.getBody().refreshToken()).isEqualTo(refreshToken);
    }

    @Test
    void itShouldNotLoginEmailNotFound() {
        // given
        String email = "hello@gmail.com";
        String password = "123";
        String encodedPassword = "abc123";

        UserSecurityInformationRequest clientRequest = new UserSecurityInformationRequest(email, encodedPassword);
        ResponseEntity<UserSecurityInformationResponse> clientResponse =
                new ResponseEntity<>(new UserSecurityInformationResponse(email, encodedPassword), HttpStatus.NOT_FOUND);
        AuthenticationRequest request = new AuthenticationRequest(email, password);

        given(encoder.encode(password)).willReturn(encodedPassword);
        given(client.getUserSecurityInformation(clientRequest)).willReturn(clientResponse);
        // when
        ResponseEntity<AuthenticationResponse> response = underTest.login(request);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void itShouldNotLoginWrongPassword() {
        // given
        String email = "hello@gmail.com";
        String password = "123";
        String encodedPassword = "abc123";

        UserSecurityInformationRequest clientRequest = new UserSecurityInformationRequest(email, encodedPassword);
        ResponseEntity<UserSecurityInformationResponse> clientResponse =
                new ResponseEntity<>(new UserSecurityInformationResponse(email, encodedPassword), HttpStatus.CONFLICT);
        AuthenticationRequest request = new AuthenticationRequest(email, password);

        given(encoder.encode(password)).willReturn(encodedPassword);
        given(client.getUserSecurityInformation(clientRequest)).willReturn(clientResponse);
        // when
        ResponseEntity<AuthenticationResponse> response = underTest.login(request);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isNull();
    }
}