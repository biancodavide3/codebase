package com.biancodavide3.clients.authorization;

import java.util.UUID;

public record RefreshRequest(UUID refreshToken, String subject) {
}
