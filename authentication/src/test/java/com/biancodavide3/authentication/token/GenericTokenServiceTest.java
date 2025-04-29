package com.biancodavide3.authentication.token;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.within;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GenericTokenServiceTest {

    private GenericTokenService underTest;


    @BeforeEach
    void setUp() {
        underTest = new GenericTokenService();
    }

    @Test
    void itShouldGenerateTokenCorrectly() {
        // given
        String subject = "david";
        int expiration = 15; // hours
        // when
        String token = underTest.generateToken(subject, expiration);
        // then
        boolean isTokenValid = underTest.isTokenValid(token, subject);
        assertThat(isTokenValid).isTrue();
        // check that the expiration is correct
        LocalDateTime extractedExpiration = underTest.extractClaim(token, Claims::getExpiration)
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        assertThat(extractedExpiration)
                .isCloseTo(now().plusHours(expiration), within(1, ChronoUnit.MINUTES));
    }
}
