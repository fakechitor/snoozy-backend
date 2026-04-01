package org.snoozy.snoozyauth.dto;

public record LoginRequestDto(
    String email,
    String password
) {
}
