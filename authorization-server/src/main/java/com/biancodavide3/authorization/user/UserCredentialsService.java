package com.biancodavide3.authorization.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserCredentialsService {

    // just talk to the database no additional validation

    private final UserCredentialsRepository repository;

    public void addCredentials(UserCredentialsDTO dto) {
        UserCredentials creds = UserCredentials.builder()
                .id(UUID.randomUUID())
                .email(dto.email())
                .password(dto.password())
                .role(dto.role())
                .build();
        if (repository.findByEmail(dto.email()).isEmpty())
            repository.save(creds);
    }

    public Optional<UserCredentialsDTO> getCredentials(String email) {
        Optional<UserCredentials> optional = repository.findByEmail(email);
        if (optional.isEmpty())
            return Optional.empty();
        UserCredentials userCredentials = optional.get();
        return Optional.of(
                new UserCredentialsDTO(
                        userCredentials.getEmail(),
                        userCredentials.getPassword(),
                        userCredentials.getRole()
                )
        );
    }
}
