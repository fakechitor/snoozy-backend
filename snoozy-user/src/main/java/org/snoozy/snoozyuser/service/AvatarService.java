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

    public byte[] getAvatar(Long userId) {
        Avatar avatar = findAvatar(userId);
        return fileClient.getAvatar(avatar.getObjectKey());
    }

    private Avatar findAvatar(Long userId) {
        Long avatarId;

        User user = userRepository.findById((userId)).get();

        if (user.getAvatar() == null) {
            avatarId = BASIC_AVATAR_ID;
        }
        else{
            avatarId = user.getAvatar().getId();
        }
        return avatarRepository.findById(avatarId).get();
    }
}
