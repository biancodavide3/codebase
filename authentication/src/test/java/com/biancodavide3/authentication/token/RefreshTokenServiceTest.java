package com.biancodavide3.authentication.token;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenServiceTest {
    @InjectMocks
    private RefreshTokenService underTest;
    @Mock
    private TokenRepository repository;
    @Captor
    private ArgumentCaptor<Token> captor;

    @Test
    void itShouldGenerateTokenCorrectly() {
        // given
        String subject = "david";
        // when
        String token = underTest.generateRefreshToken(subject);
        // then
        boolean isTokenValid = underTest.isTokenValid(token, subject);
        assertThat(isTokenValid).isTrue();
        Mockito.verify(repository).save(captor.capture());
        Token capturedToken = captor.getValue();
        assertThat(capturedToken.getToken()).isEqualTo(token);
        assertThat(capturedToken.isExpired()).isFalse();
        assertThat(capturedToken.isRevoked()).isFalse();
    }
}
