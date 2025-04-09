package com.biancodavide3.user.model.repositories;

import com.biancodavide3.user.model.UserSecurityInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserSecurityInformationRepository extends JpaRepository<UserSecurityInformation, UUID> {
    boolean existsUserSecurityInformationByEmail(String email);
    boolean existsUserSecurityInformationByEmailAndPassword(String email, String password);
}
