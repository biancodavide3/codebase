package com.biancodavide3.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
        scanBasePackages = {"com.biancodavide3.authorization", "com.biancodavide3.jwt"}
)
@EnableFeignClients(
        basePackages = "com.biancodavide3.clients"
)
public class AuthorizationServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }
}
