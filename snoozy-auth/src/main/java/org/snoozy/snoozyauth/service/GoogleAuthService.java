package org.snoozy.snoozyauth.service;

import lombok.RequiredArgsConstructor;
import org.snoozy.snoozyauth.dto.AuthResponse;
import org.snoozy.snoozyauth.dto.GoogleLoginRequest;
import org.snoozy.snoozyauth.dto.GoogleUserInfo;
import org.snoozy.snoozyauth.dto.mapper.GoogleAuthMapper;
import org.snoozy.snoozyauth.producer.GoogleAuthProducer;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleAuthService {

    private final GoogleAuthProducer googleAuthProducer;

    private final GoogleAuthMapper googleAuthMapper;

    private final GoogleVerifierService googleAuthVerifier;

    private final JwtService jwtService;

    public AuthResponse authenticate(GoogleLoginRequest googleLoginRequest) {
        String idTokenString = googleLoginRequest.idToken().trim();
        GoogleUserInfo userInfo = googleAuthVerifier.verify(idTokenString);

        googleAuthProducer.sendGoogleAuthEvent(googleAuthMapper.toEvent(userInfo));

        return new AuthResponse(jwtService.generateTokenGoogle(userInfo));
    }
}