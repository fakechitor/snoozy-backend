package org.snoozy.snoozyuser.dto;

public record UserResponseDto(
        String username,
        String email,
        String phoneNumber,
        String avatarLink
) {
}
