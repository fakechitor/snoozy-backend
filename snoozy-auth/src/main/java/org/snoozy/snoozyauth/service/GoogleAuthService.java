package org.snoozy.snoozyauth.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.snoozy.snoozyauth.dto.AuthResponse;
import org.snoozy.snoozyauth.dto.GoogleLoginRequest;
import org.snoozy.snoozyauth.dto.GoogleUserInfo;
import org.snoozy.snoozyauth.dto.mapper.GoogleAuthMapper;
import org.snoozy.snoozyauth.producer.GoogleAuthProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

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

        return new AuthResponse(jwtService.generateToken(userInfo));
    }
}