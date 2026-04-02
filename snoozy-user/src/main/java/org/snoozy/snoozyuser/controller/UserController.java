package org.snoozy.snoozyuser.controller;

import lombok.RequiredArgsConstructor;
import org.snoozy.snoozyuser.dto.UserResponseDto;
import org.snoozy.snoozyuser.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getUserInfo(@RequestHeader("X-User-Email") String email){
        return ResponseEntity.ok().body(userService.getUserInfo(email));
    }
}
