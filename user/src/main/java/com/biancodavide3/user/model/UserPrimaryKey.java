package com.biancodavide3.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Getter
@Entity(name = "UserPrimaryKey")
@Table(name = "user_primary_key")
@NoArgsConstructor
public class UserPrimaryKey {
    @Id
    @Column(name = "id")
    private final UUID id = UUID.randomUUID();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserPrimaryKey that = (UserPrimaryKey) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
