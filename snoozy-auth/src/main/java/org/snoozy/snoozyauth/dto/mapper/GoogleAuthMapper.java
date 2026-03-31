package org.snoozy.snoozyauth.dto.mapper;

import org.snoozy.snoozyauth.dto.GoogleUserInfo;
import org.snoozy.snoozyauth.producer.event.GoogleAuthEvent;
import org.springframework.stereotype.Component;

@Component
public class GoogleAuthMapper {

    public GoogleAuthEvent toEvent(GoogleUserInfo userInfo) {
        return new GoogleAuthEvent(
                userInfo.sub(),
                userInfo.email(),
                userInfo.emailVerified(),
                userInfo.name(),
                userInfo.pictureUrl()
        );
    }
}
