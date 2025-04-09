package com.biancodavide3.user.model.repositories;

import com.biancodavide3.user.model.UserPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserPrimaryKeyRepository extends JpaRepository<UserPrimaryKey, UUID> {
}
