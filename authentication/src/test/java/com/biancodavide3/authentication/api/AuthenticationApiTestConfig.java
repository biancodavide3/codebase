package com.biancodavide3.authentication.api;

import com.biancodavide3.authentication.token.TokenRepository;
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
public class AuthenticationApiTestConfig {

    private final TokenRepository repository;

    @Bean
    @Transactional
    public CommandLineRunner initMockData() {
        return args -> {
            log.info("Deleting all refresh tokens from the database to prepare for testing...");
            repository.deleteAll();
            log.info("Successfully deleted all refresh tokens");
        };
    }
}
