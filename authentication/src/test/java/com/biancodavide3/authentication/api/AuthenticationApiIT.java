package com.biancodavide3.authentication.api;

import com.biancodavide3.authentication.AuthenticationApplication;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WireMockTest(httpPort = 8080)
@AutoConfigureMockMvc
@SpringBootTest(classes = {AuthenticationApplication.class})
@ActiveProfiles("test")
public class AuthenticationApiIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void itShouldSignupCorrectly() throws Exception {
        // given
        String json = "{\"email\":\"hello@gmail.com\",\"password\":\"123\"}";
        WireMock.stubFor(WireMock.post("/api/v1/security/add")
                .willReturn(WireMock.ok()));
        // when
        String content = mockMvc.perform(post("/api/v1/auth/signup")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        assertThat(content.contains("refresh")).isTrue();
        assertThat(content.contains("access")).isTrue();
    }

    @Test
    void itShouldNotSignupEmailTaken() throws Exception {
        // given
        String json = "{\"email\":\"hello@gmail.com\",\"password\":\"123\"}";
        WireMock.stubFor(WireMock.post("/api/v1/security/add")
                .willReturn(WireMock.aResponse().withStatus(409))); // CONFLICT
        // when
        String content = mockMvc.perform(post("/api/v1/auth/signup")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn().getResponse().getContentAsString();
        // then
        assertThat(content).isEmpty();
    }

    @Test
    void itShouldLoginCorrectly() throws Exception {
        // given
        String json = "{\"email\":\"hello1@gmail.com\",\"password\":\"123\"}";
        WireMock.stubFor(WireMock.post("/api/v1/security/check")
                .willReturn(WireMock.ok()));
        // when
        String content = mockMvc.perform(post("/api/v1/auth/login")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        assertThat(content.contains("refresh")).isTrue();
        assertThat(content.contains("access")).isTrue();
    }

    @Test
    void itShouldNotLoginEmailNotFound() throws Exception {
        // given
        String json = "{\"email\":\"hello@gmail.com\",\"password\":\"123\"}";
        WireMock.stubFor(WireMock.post("/api/v1/security/check")
                .willReturn(WireMock.notFound()));
        // when
        String content = mockMvc.perform(post("/api/v1/auth/login")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        // then
        assertThat(content).isEmpty();
    }

    @Test
    void itShouldNotLoginWrongPassword() throws Exception {
        // given
        String json = "{\"email\":\"hello@gmail.com\",\"password\":\"123\"}";
        WireMock.stubFor(WireMock.post("/api/v1/security/check")
                .willReturn(WireMock.aResponse().withStatus(409)));
        // when
        String content = mockMvc.perform(post("/api/v1/auth/login")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn().getResponse().getContentAsString();
        // then
        assertThat(content).isEmpty();
    }
}

