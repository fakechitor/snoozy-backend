package org.snoozy.snoozyuser.service;

import lombok.RequiredArgsConstructor;
import org.snoozy.snoozyuser.client.FileClient;
import org.snoozy.snoozyuser.model.Avatar;
import org.snoozy.snoozyuser.model.User;
import org.snoozy.snoozyuser.repository.AvatarRepository;
import org.snoozy.snoozyuser.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvatarService {

    private final AvatarRepository avatarRepository;

    private final UserRepository userRepository;

    private final FileClient fileClient;

    private static final Long BASIC_AVATAR_ID = 1L;

    public byte[] getAvatar(String email) {
        Avatar avatar = findAvatar(email);
        return fileClient.getAvatar(avatar.getObjectKey());
    }

    private Avatar findAvatar(String email) {
        User user = userRepository.findByEmail((email)).get();
        Long id;

        if (user.getAvatar() == null) {
            id = BASIC_AVATAR_ID;
        }
        else{
            id = user.getAvatar().getId();
        }
        return avatarRepository.findById(id).get();
    }
}
