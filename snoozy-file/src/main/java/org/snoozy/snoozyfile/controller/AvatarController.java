package org.snoozy.snoozyfile.controller;

import lombok.RequiredArgsConstructor;
import org.snoozy.snoozyfile.service.AvatarMinioService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarMinioService avatarStorageService;

    @PostMapping(value = "/users/{userId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadUserAvatar(
            @PathVariable Long userId,
            @RequestPart("file") MultipartFile file
    ) {
        String objectKey = avatarStorageService.uploadUserAvatar(userId, file);

        return ResponseEntity.ok(
                Map.of(
                        "objectKey", objectKey,
                        "avatarUrl", "/api/v1/files/avatar?key=" + objectKey
                )
        );
    }

    @PostMapping(value = "/groups/{groupId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadGroupAvatar(
            @PathVariable Long groupId,
            @RequestPart("file") MultipartFile file
    ) {
        String objectKey = avatarStorageService.uploadGroupAvatar(groupId, file);

        return ResponseEntity.ok(
                Map.of(
                        "objectKey", objectKey,
                        "avatarUrl", "/api/v1/files/groups/" + groupId + "/avatar"
                )
        );
    }

    @GetMapping("/avatar")
    public ResponseEntity<InputStreamResource> getAvatar(
            @RequestParam("key") String objectKey
    ) {
        InputStream stream = avatarStorageService.getAvatar(objectKey);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(stream));
    }


    @DeleteMapping("/avatar")
    public ResponseEntity<Void> deleteAvatar(
            @RequestParam("key") String objectKey
    ) {
        avatarStorageService.deleteAvatar(objectKey);
        return ResponseEntity.noContent().build();
    }
}
