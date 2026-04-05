package org.snoozy.snoozyuser.dto.mapper;

import org.snoozy.snoozyuser.dto.AvatarResponseDto;
import org.snoozy.snoozyuser.dto.UserResponseDto;
import org.snoozy.snoozyuser.model.Avatar;
import org.snoozy.snoozyuser.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDto getUserResponseDto(User user) {
        Avatar avatar = user.getAvatar();
        String avatarUrl = avatar != null ? avatar.getObjectKey() : null;

        return new UserResponseDto(
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                avatarUrl
        );
    }
}
