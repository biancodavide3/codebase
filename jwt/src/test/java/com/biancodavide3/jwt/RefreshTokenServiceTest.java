package com.biancodavide3.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RefreshTokenServiceTest {
    private RefreshTokenService underTest;

    @BeforeEach
    void setUp() {
        underTest = new RefreshTokenService();
    }

    @Test
    void itShouldGenerateTokenCorrectly() {
        // given
        String subject = "david";
        // when
        String token = underTest.generateRefreshToken(subject);
        // then
        boolean isTokenValid = underTest.isTokenValid(token, subject);
        assertThat(isTokenValid).isTrue();
    }
}
