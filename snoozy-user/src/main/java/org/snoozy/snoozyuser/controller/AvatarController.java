package org.snoozy.snoozyuser.controller;

import lombok.RequiredArgsConstructor;
import org.snoozy.snoozyuser.dto.AvatarResponseDto;
import org.snoozy.snoozyuser.service.AvatarService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users/avatar")
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarService avatarService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AvatarResponseDto> uploadUserAvatar(
            @RequestHeader("X-User-Id") Long userId,
            @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok().body(avatarService.uploadUserPhoto(userId, file));
    }

    // DEPRECATED
    @GetMapping
    public ResponseEntity<byte[]> getAvatar(@RequestHeader("X-User-Id") Long userId) {
        byte[] image = avatarService.getAvatar(userId);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
    }
}
