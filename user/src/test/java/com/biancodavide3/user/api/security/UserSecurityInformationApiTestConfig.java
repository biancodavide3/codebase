package com.biancodavide3.user.api.security;

import com.biancodavide3.user.model.UserPrimaryKey;
import com.biancodavide3.user.model.UserSecurityInformation;
import com.biancodavide3.user.model.repositories.UserSecurityInformationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
@AllArgsConstructor
@Slf4j
public class UserSecurityInformationApiTestConfig {

    private final UserSecurityInformationRepository userSecurityInformationRepository;

    @Bean
    @Transactional
    public CommandLineRunner initMockData() {
        return args -> {
            UserPrimaryKey userPrimaryKey = new UserPrimaryKey();
            String email = "hello@gmail.com";
            String password = "123";
            UserSecurityInformation userSecurityInformation = UserSecurityInformation.builder()
                    .userPrimaryKey(userPrimaryKey)
                    .email(email)
                    .password(password).build();
            if (userSecurityInformationRepository.existsUserSecurityInformationByEmail(email)) {
                log.info("User Security Information already exists in the Database: {}", userSecurityInformation);
                return;
            }
            log.info("Saving Mock User Security Information in the Database for Testing...");
            userSecurityInformationRepository.save(userSecurityInformation);
            log.info("Saved Mock UserSecurityInformation: {}", userSecurityInformation);
        };
    }
}
