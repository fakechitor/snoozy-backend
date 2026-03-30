package org.snoozy.snoozyauth.dto;

public record GoogleUserInfo(
        String sub,
        String email,
        boolean emailVerified,
        String name,
        String pictureUrl
) {
}