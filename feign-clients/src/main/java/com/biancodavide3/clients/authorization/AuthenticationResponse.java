package com.biancodavide3.clients.authorization;

import java.util.UUID;

public record AuthenticationResponse (String accessToken, UUID refreshToken){
}
