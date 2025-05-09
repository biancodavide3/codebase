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
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "token_seq"
    )
    @SequenceGenerator(
            name = "token_seq",
            sequenceName = "token_id_seq",
            allocationSize = 1
    )
    private Long id;
    @Column(columnDefinition = "VARCHAR(255) UNIQUE NOT NULL")
    private String token;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE NOT NULL")
    private boolean revoked;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE NOT NULL")
    private boolean expired;
}
