package org.snoozy.snoozyauth.dto;

public record GoogleLoginRequest(
        String idToken,
        String phoneNumber
) {
}
