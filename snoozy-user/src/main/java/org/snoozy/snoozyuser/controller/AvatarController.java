package org.snoozy.snoozyuser.controller;

import lombok.RequiredArgsConstructor;
import org.snoozy.snoozyuser.service.AvatarService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/avatar")
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarService avatarService;

    @GetMapping
    public ResponseEntity<byte[]> getAvatar(@RequestHeader("X-User-Email") String email) {
        byte[] image = avatarService.getAvatar(email);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
    }
}
