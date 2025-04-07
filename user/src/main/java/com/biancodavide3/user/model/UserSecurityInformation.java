package com.biancodavide3.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "UserSecurityInformation")
@Table(name = "user_security_information")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSecurityInformation {
    @Id
    private UUID id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private UserPrimaryKey userPrimaryKey;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserSecurityInformation that = (UserSecurityInformation) o;
        return Objects.equals(userPrimaryKey, that.userPrimaryKey);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userPrimaryKey);
    }
}
