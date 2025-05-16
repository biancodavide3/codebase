package com.biancodavide3.authorization.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "authorization.token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenProperties {
    private String secretKey;
    private long accessTokenExpiration;
}
