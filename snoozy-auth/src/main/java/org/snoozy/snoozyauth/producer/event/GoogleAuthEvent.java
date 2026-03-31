package org.snoozy.snoozyauth.producer.event;

public record GoogleAuthEvent(
        String sub,
        String email,
        boolean emailVerified,
        String name,
        String pictureUrl
) {
}
