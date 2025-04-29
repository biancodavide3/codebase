package com.biancodavide3.authentication.token;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity(name = "Token")
@Table(name = "token")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    @Id
    @Column(columnDefinition = "BIGSERIAL PRIMARY KEY")
    private Long id;
    @Column(columnDefinition = "VARCHAR(255) UNIQUE NOT NULL")
    private String token;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE NOT NULL")
    private boolean revoked;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE NOT NULL")
    private boolean expired;
}
