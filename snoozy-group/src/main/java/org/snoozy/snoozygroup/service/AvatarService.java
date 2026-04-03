package org.snoozy.snoozygroup.service;

import lombok.RequiredArgsConstructor;
import org.snoozy.snoozygroup.client.FileClient;
import org.snoozy.snoozygroup.dto.AvatarResponseDto;
import org.snoozy.snoozygroup.model.Avatar;
import org.snoozy.snoozygroup.model.Group;
import org.snoozy.snoozygroup.repository.AvatarRepository;
import org.snoozy.snoozygroup.repository.GroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AvatarService {
    private final FileClient fileClient;

    private final AvatarRepository avatarRepository;
    private final GroupRepository groupRepository;

    public AvatarResponseDto uploadGroupPhoto(Long userId, MultipartFile file, Long groupId) {
        String urlAvatar = fileClient.uploadGroupAvatar(userId, file);

        Avatar avatar = new Avatar();
        avatar.setContentType(file.getContentType());
        avatar.setSizeBytes(file.getSize());
        avatar.setObjectKey(urlAvatar);

        avatarRepository.save(avatar);

        Group group = groupRepository.findById(groupId).get();
        group.setAvatar(avatar);
        groupRepository.flush();

        return new AvatarResponseDto(urlAvatar);
    }
}
