package org.snoozy.snoozygroup.dto;

public record GroupMemberDto(
        Long id,
        String username,
        String avatarUrl
) {
}
