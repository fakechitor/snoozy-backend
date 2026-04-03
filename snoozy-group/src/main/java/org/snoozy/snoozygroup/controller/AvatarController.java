package org.snoozy.snoozygroup.controller;

import lombok.RequiredArgsConstructor;
import org.snoozy.snoozygroup.dto.AvatarResponseDto;
import org.snoozy.snoozygroup.service.AvatarService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/groups/avatar")
@RequiredArgsConstructor
public class AvatarController {
    private final AvatarService avatarService;

    @PostMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AvatarResponseDto> uploadGroupAvatar(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable("id") Long groupId,
            @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(avatarService.uploadGroupPhoto(userId, file, groupId));
    }
}
