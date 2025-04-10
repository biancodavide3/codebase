package com.biancodavide3.user.api.security;

import com.biancodavide3.clients.user.UserSecurityInformationRequest;
import com.biancodavide3.clients.user.UserSecurityInformationResponse;
import com.biancodavide3.user.model.UserPrimaryKey;
import com.biancodavide3.user.model.UserSecurityInformation;
import com.biancodavide3.user.model.repositories.UserSecurityInformationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserSecurityInformationService {

    private final UserSecurityInformationRepository userSecurityInformationRepository;

    public ResponseEntity<UserSecurityInformationResponse> getUserSecurityInformation(
            UserSecurityInformationRequest request) {
        if (!userSecurityInformationRepository.existsUserSecurityInformationByEmail(request.email()))
            return ResponseEntity.notFound().build();
        if (!userSecurityInformationRepository.existsUserSecurityInformationByEmailAndPassword(request.email(), request.password()))
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        return ResponseEntity.ok(new UserSecurityInformationResponse(request.email(), request.password()));
    }

    @Transactional
    public ResponseEntity<UserSecurityInformationResponse> addUserSecurityInformation(
            UserSecurityInformationRequest request) {
        if (userSecurityInformationRepository.existsUserSecurityInformationByEmail(request.email()))
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        persistNewUserSecurityInformation(request);
        return ResponseEntity.ok(new UserSecurityInformationResponse(request.email(), request.password()));
    }

    @Transactional
    public void persistNewUserSecurityInformation(UserSecurityInformationRequest request) {
        UserPrimaryKey userPrimaryKey = new UserPrimaryKey();
        UserSecurityInformation userSecurityInformation = UserSecurityInformation.builder()
                .userPrimaryKey(userPrimaryKey)
                .email(request.email())
                .password(request.password())
                .build();
        userSecurityInformationRepository.save(userSecurityInformation);
    }
}
