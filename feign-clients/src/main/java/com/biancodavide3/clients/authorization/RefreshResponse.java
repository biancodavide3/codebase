package com.biancodavide3.clients.authorization;

import java.util.UUID;

public record RefreshResponse (String accessToken, UUID refreshToken){
}