package org.snoozy.snoozyuser.service;

import lombok.RequiredArgsConstructor;
import org.snoozy.snoozyuser.client.FileClient;
import org.snoozy.snoozyuser.dto.AvatarResponseDto;
import org.snoozy.snoozyuser.model.Avatar;
import org.snoozy.snoozyuser.model.User;
import org.snoozy.snoozyuser.repository.AvatarRepository;
import org.snoozy.snoozyuser.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AvatarService {

    private final AvatarRepository avatarRepository;

    private final UserRepository userRepository;

    private final FileClient fileClient;

    private static final Long BASIC_AVATAR_ID = 1L;

    public AvatarResponseDto uploadUserPhoto(Long userId, MultipartFile file) {
        String objectLink = fileClient.uploadUserAvatar(userId, file);

        Avatar avatar = new Avatar();
        avatar.setSizeBytes(file.getSize());
        avatar.setObjectKey(objectLink);
        avatar.setContentType(file.getContentType());

        avatarRepository.save(avatar);

        User user = userRepository.findById(userId).get();
        user.setAvatar(avatar);
        userRepository.flush();

        return new AvatarResponseDto(objectLink);
    }

    public byte[] getAvatar(Long userId) {
        User user = userRepository.findById((userId)).get();
        Avatar avatar = findAvatar(user);
        // TODO change byte[] to link
        return fileClient.getAvatar(avatar.getObjectKey());
    }

    public Avatar findAvatar(User user) {
        Long avatarId;

        if (user.getAvatar() == null) {
            avatarId = BASIC_AVATAR_ID;
        }
        else{
            avatarId = user.getAvatar().getId();
        }
        return avatarRepository.findById(avatarId).get();
    }
}
