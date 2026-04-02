package org.snoozy.snoozyauth.dto;

public record RegisterRequestDto(
    String username,
    String phoneNumber,
    String password,
    String confirmPassword
) {
}
