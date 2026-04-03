package org.snoozy.snoozygroup.dto;

import java.util.Set;

public record GroupRequestDto(
        String name,
        Set<Long> membersId
) {
}
