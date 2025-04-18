package com.biancodavide3.clients;

import com.biancodavide3.authentication.AuthenticationApplication;
import com.biancodavide3.clients.user.UserSecurityInformationClient;
import com.biancodavide3.clients.user.UserSecurityInformationRequest;
import com.biancodavide3.clients.user.UserSecurityInformationResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = {AuthenticationApplication.class})
@ActiveProfiles("test")
public class UserSecurityInformationClientIT {
    @Autowired
    private UserSecurityInformationClient underTest;

    @Test
    @Disabled
    void itShouldGetUserSecurityInformationCorrectly() {
        // given
        String email = "hello@gmail.com";
        String password = "123";
        UserSecurityInformationRequest request = new UserSecurityInformationRequest(email, password);
        // when
        ResponseEntity<UserSecurityInformationResponse> response = underTest.getUserSecurityInformation(request);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
