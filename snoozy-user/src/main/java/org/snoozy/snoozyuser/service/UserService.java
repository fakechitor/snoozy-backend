package org.snoozy.snoozyuser.service;

import lombok.RequiredArgsConstructor;
import org.snoozy.snoozyuser.consumer.event.GoogleAuthMessage;
import org.snoozy.snoozyuser.model.Avatar;
import org.snoozy.snoozyuser.model.User;
import org.snoozy.snoozyuser.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user){
        return userRepository.save(user);
    }

    public void handleAuthEvent(GoogleAuthMessage authMessage) {
        boolean isExist = userRepository.findByGoogleSub(authMessage.sub()).isPresent();
        if (!isExist){
            Avatar avatar = new Avatar();

            // temp solution
            avatar.setObjectKey(authMessage.pictureUrl());
            avatar.setSizeBytes(123L);
            avatar.setContentType(MediaType.MULTIPART_FORM_DATA_VALUE);

            User user = new User();

            user.setGoogleSub(authMessage.sub());
            user.setEmail(authMessage.email());
            user.setAvatar(avatar);
            user.setUsername(authMessage.name());

            userRepository.save(user);
        }
    }
}
