package org.snoozy.snoozyuser.service;

import lombok.RequiredArgsConstructor;
import org.snoozy.snoozyuser.dto.FriendRequestDto;
import org.snoozy.snoozyuser.dto.FriendResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    public FriendResponseDto addFriend(FriendRequestDto friendRequestDto) {
        return null;
    }

    public List<FriendResponseDto> getFriendsList(String email) {
        return null;
    }
}
