package com.biancodavide3.user.api.security;

import com.biancodavide3.clients.user.UserSecurityInformationRequest;
import com.biancodavide3.clients.user.UserSecurityInformationResponse;
import com.biancodavide3.user.model.UserSecurityInformation;
import com.biancodavide3.user.model.repositories.UserSecurityInformationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSecurityInformationServiceTest {

    @Mock
    private UserSecurityInformationRepository userSecurityInformationRepository;
    @InjectMocks
    private UserSecurityInformationService underTest;

    @Test
    void itShouldGetUserSecurityInformationCorrectly() {
        // given
        UserSecurityInformationRequest request = new UserSecurityInformationRequest("hello@gmail.com", "123");
        given(userSecurityInformationRepository.existsUserSecurityInformationByEmail(request.email())).willReturn(true);
        given(userSecurityInformationRepository.existsUserSecurityInformationByEmailAndPassword(
                request.email(), request.password())).willReturn(true);
        // when
        ResponseEntity<UserSecurityInformationResponse> response = underTest.getUserSecurityInformation(request);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().email()).isEqualTo(request.email());
        assertThat(response.getBody().password()).isEqualTo(request.password());
    }

    @Test
    void itShouldNotGetUserSecurityInformationEmailNotFound() {
        // given
        UserSecurityInformationRequest request = new UserSecurityInformationRequest("hello@gmail.com", "123");
        given(userSecurityInformationRepository.existsUserSecurityInformationByEmail(request.email())).willReturn(false);
        // when
        ResponseEntity<UserSecurityInformationResponse> response = underTest.getUserSecurityInformation(request);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void itShouldNotGetUserSecurityInformationWrongPassword() {
        // given
        UserSecurityInformationRequest request = new UserSecurityInformationRequest("hello@gmail.com", "123");
        given(userSecurityInformationRepository.existsUserSecurityInformationByEmail(request.email())).willReturn(true);
        given(userSecurityInformationRepository.existsUserSecurityInformationByEmailAndPassword(
                request.email(), request.password())).willReturn(false);
        // when
        ResponseEntity<UserSecurityInformationResponse> response = underTest.getUserSecurityInformation(request);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void itShouldAddUserSecurityInformationCorrectly() {
        // given
        UserSecurityInformationRequest request = new UserSecurityInformationRequest("hello@gmail.com", "123");
        given(userSecurityInformationRepository.existsUserSecurityInformationByEmail(request.email())).willReturn(false);
        // when
        ResponseEntity<UserSecurityInformationResponse> response = underTest.addUserSecurityInformation(request);
        // then
        verify(userSecurityInformationRepository, times(1)).save(any(UserSecurityInformation.class));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().email()).isEqualTo(request.email());
        assertThat(response.getBody().password()).isEqualTo(request.password());
    }

    @Test
    void itShouldNotAddUserSecurityInformationEmailExists() {
        // given
        UserSecurityInformationRequest request = new UserSecurityInformationRequest("hello@gmail.com", "123");
        given(userSecurityInformationRepository.existsUserSecurityInformationByEmail(request.email())).willReturn(true);
        // when
        ResponseEntity<UserSecurityInformationResponse> response = underTest.addUserSecurityInformation(request);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isNull();
        verifyNoMoreInteractions(userSecurityInformationRepository);
    }
}