package com.biancodavide3.authorization.token;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "OpaqueRefreshToken")
@Table(name = "opaque_refresh_token")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpaqueRefreshToken {
    @Id
    @Column(name = "token")
    private UUID token;
    @Column(name = "expiration", nullable = false)
    private LocalDate expiration;
    @Column(name = "subject", nullable = false)
    private String subject;
    @Column(name = "role", nullable = false)
    private String role;
}
