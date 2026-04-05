package org.snoozy.snoozyuser.dto;

public record UserResponseDto(
        Long id,
        String username,
        String email,
        String phoneNumber,
        String avatarLink
) {
}
