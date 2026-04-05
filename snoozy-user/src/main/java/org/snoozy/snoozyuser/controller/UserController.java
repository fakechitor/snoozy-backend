package org.snoozy.snoozyuser.controller;

import lombok.RequiredArgsConstructor;
import org.snoozy.snoozyuser.dto.PhoneRequestDto;
import org.snoozy.snoozyuser.dto.UserResponseDto;
import org.snoozy.snoozyuser.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getUserInfo(@RequestHeader("X-User-Id") Long userId){
        return ResponseEntity.ok().body(userService.getUserInfo(userId));
    }

    @GetMapping("/phone")
    public ResponseEntity<UserResponseDto> getPhoneNumber(
            @RequestParam String phoneNumber){
        return ResponseEntity.ok().body(userService.getByPhoneNumber(phoneNumber));
    }
}
