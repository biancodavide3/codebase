package com.biancodavide3.user.data;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "UserPersonalInformation")
@Table(name = "user_personal_information")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPersonalInformation {
    @Id
    private UUID id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private UserPrimaryKey userPrimaryKey;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "gender")
    private String gender;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserPersonalInformation that = (UserPersonalInformation) o;
        return Objects.equals(userPrimaryKey, that.userPrimaryKey);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userPrimaryKey);
    }
}
