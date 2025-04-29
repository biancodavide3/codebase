package com.biancodavide3.authentication.token;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AccessTokenServiceTest {

    private AccessTokenService underTest;

    @BeforeEach
    void setUp() {
        underTest = new AccessTokenService();
    }

    @Test
    void itShouldGenerateTokenCorrectly() {
        // given
        String subject = "david";
        // when
        String token = underTest.generateAccessToken(subject);
        // then
        boolean isTokenValid = underTest.isTokenValid(token, subject);
        assertThat(isTokenValid).isTrue();
    }
}
