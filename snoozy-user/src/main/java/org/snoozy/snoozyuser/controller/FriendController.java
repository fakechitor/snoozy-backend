package org.snoozy.snoozyuser.controller;

import lombok.RequiredArgsConstructor;
import org.snoozy.snoozyuser.dto.FriendResponseDto;import org.snoozy.snoozyuser.service.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @GetMapping
    public ResponseEntity<List<FriendResponseDto>> getFriends(@RequestHeader("X-User-Email") String email) {
        return ResponseEntity.ok().body(friendService.getFriendsList(email));
    }
}
