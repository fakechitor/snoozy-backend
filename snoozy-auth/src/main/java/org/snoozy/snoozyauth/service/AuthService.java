package org.snoozy.snoozyauth.service;

import lombok.RequiredArgsConstructor;
import org.snoozy.snoozyauth.dto.AuthResponse;
import org.snoozy.snoozyauth.dto.LoginRequestDto;
import org.snoozy.snoozyauth.dto.RegisterRequestDto;
import org.snoozy.snoozyauth.exception.UserAlreadyExistsException;
import org.snoozy.snoozyauth.model.CustomUserDetails;
import org.snoozy.snoozyauth.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    public AuthResponse login(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(request.email());
        User user =  userDetails.getUser();

        String accessToken = jwtService.generateTokenBasic(user);
        return new AuthResponse(accessToken);
    }

    public AuthResponse register(RegisterRequestDto request) throws UserAlreadyExistsException {
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user = userService.save(user);

        String accessToken = jwtService.generateTokenBasic(user);
        return new AuthResponse(accessToken);
    }
}
