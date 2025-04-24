package com.biancodavide3.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
        scanBasePackages = {"com.biancodavide3.authentication", "com.biancodavide3.jwt"}
)
@EnableFeignClients(
        basePackages = "com.biancodavide3.clients"
)
public class AuthenticationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApplication.class, args);
    }
}
