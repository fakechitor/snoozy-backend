package org.snoozy.snoozygroup.dto;

import java.util.Set;

public record GroupResponseDto(
        Long id,
        String name,
        Long ownerId,
        String avatarUrl,
        Set<GroupMemberDto> members
) {
}
