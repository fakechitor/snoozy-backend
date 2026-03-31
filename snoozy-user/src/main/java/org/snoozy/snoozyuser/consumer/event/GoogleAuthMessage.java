package org.snoozy.snoozyuser.consumer.event;

public record GoogleAuthMessage(
        String sub,
        String email,
        boolean emailVerified,
        String name,
        String pictureUrl
) {
}
