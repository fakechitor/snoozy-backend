package org.snoozy.snoozyauth.dto;

public record AuthResponse(
        String accessToken,
        String tokenType,
        Long userId,
        String email,
        String name
) {
}