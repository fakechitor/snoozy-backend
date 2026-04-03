package org.snoozy.snoozyuser.dto.mapper;

import org.snoozy.snoozyuser.dto.UserResponseDto;
import org.snoozy.snoozyuser.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDto getUserResponseDto(User user) {
        return new UserResponseDto(
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAvatar().getObjectKey()
        );
    }
}
