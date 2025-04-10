package com.biancodavide3.user.api.security;

import com.biancodavide3.user.UserApplication;
import jakarta.transaction.Transactional;
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

@SpringBootTest(classes = {UserApplication.class})
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserSecurityInformationApiIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void itShouldGetUserSecurityInformationCorrectly() throws Exception {
        // given
        String json = "{\"email\":\"hello@gmail.com\",\"password\":\"123\"}";
        // when
        String content = mockMvc.perform(post("/api/v1/security/check")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        assertThat(content).isEqualTo(json);
    }

    @Test
    void itShouldNotGetUserSecurityInformationEmailNotFound() throws Exception {
        // given
        String json = "{\"email\":\"helloA@gmail.com\",\"password\":\"123\"}";
        // when
        String content = mockMvc.perform(post("/api/v1/security/check")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        // then
        assertThat(content).isNullOrEmpty();
    }

    @Test
    void itShouldNotGetUserSecurityInformationWrongPassword() throws Exception {
        // given
        String json = "{\"email\":\"hello@gmail.com\",\"password\":\"123A\"}";
        // when
        String content = mockMvc.perform(post("/api/v1/security/check")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn().getResponse().getContentAsString();
        // then
        assertThat(content).isNullOrEmpty();
    }

    @Test
    @Transactional
    void itShouldAddUserSecurityInformationCorrectly() throws Exception {
        // given
        String json = "{\"email\":\"hello2@gmail.com\",\"password\":\"1234\"}";
        // when
        String content = mockMvc.perform(post("/api/v1/security/add")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        assertThat(content).isEqualTo(json);
    }

    @Test
    void itShouldNotAddUserSecurityInformationEmailAlreadyExists() throws Exception {
        // given
        String json = "{\"email\":\"hello@gmail.com\",\"password\":\"1234\"}";
        // when
        String content = mockMvc.perform(post("/api/v1/security/add")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn().getResponse().getContentAsString();
        // then
        assertThat(content).isNullOrEmpty();
    }
}
