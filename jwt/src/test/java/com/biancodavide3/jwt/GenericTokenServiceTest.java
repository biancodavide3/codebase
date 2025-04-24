package com.biancodavide3.jwt;

import com.sun.jdi.IntegerValue;
import io.jsonwebtoken.Claims;
import org.assertj.core.internal.Integers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

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
        int expiration = 1;
        // when
        String token = underTest.generateToken(subject, expiration);
        // then
        boolean isTokenValid = underTest.isTokenValid(token, subject);
        assertThat(isTokenValid).isTrue();
        Date extractedExpiration = underTest.extractClaim(token, Claims::getExpiration);
        String extractedExpirationHour = extractedExpiration.toString().substring(11, 12);
        String currentHour = new Date().toString().substring(11, 12);
        int expirationHour = Integer.parseInt(extractedExpirationHour);
        int hour = Integer.parseInt(currentHour);
        assertThat(expirationHour - hour).isEqualTo(expiration);
    }
}
