package org.snoozy.snoozyauth.controller;

import lombok.RequiredArgsConstructor;
import org.snoozy.snoozyauth.dto.AuthResponse;
import org.snoozy.snoozyauth.dto.LoginRequestDto;
import org.snoozy.snoozyauth.dto.RegisterRequestDto;
import org.snoozy.snoozyauth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/basic")
@RequiredArgsConstructor
public class BasicAuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequestDto registerRequestDto) {
        return ResponseEntity.ok(authService.register(registerRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }
}
