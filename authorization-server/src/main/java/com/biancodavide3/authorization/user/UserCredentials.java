package com.biancodavide3.authorization.user;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "UserCredentials")
@Table(name = "user_credentials")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserCredentials {
    @Id
    private UUID id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String role;
}
