package org.snoozy.snoozyauth.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.snoozy.snoozyauth.dto.AuthResponse;
import org.snoozy.snoozyauth.dto.GoogleLoginRequest;
import org.snoozy.snoozyauth.dto.GoogleUserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleAuthService {

    private final GoogleIdTokenVerifier verifier;

    private final String webClientId;

    public GoogleAuthService(@Value("${google.oauth.web-client-id}") String webClientId) {
        this.webClientId = webClientId.trim();
        this.verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance()
        )
                .setAudience(Collections.singletonList(this.webClientId))
                .build();
    }

    public AuthResponse authenticate(GoogleLoginRequest googleLoginRequest) {
        String idTokenString = googleLoginRequest.idToken().trim();
        GoogleUserInfo userInfo = verify(idTokenString);
        return new AuthResponse(
                "",
                "google",
                1L,
                userInfo.email(),
                userInfo.name()
        );
    }

    private GoogleUserInfo verify(String idTokenString) {
        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken == null) {
                throw new IllegalArgumentException("Invalid Google ID token");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();

            String googleSub = payload.getSubject();
            String email = payload.getEmail();
            Boolean emailVerified = (Boolean) payload.get("email_verified");
            String name = (String) payload.get("name");
            String picture = (String) payload.get("picture");

            return new GoogleUserInfo(
                    googleSub,
                    email,
                    Boolean.TRUE.equals(emailVerified),
                    name,
                    picture
            );
        } catch (GeneralSecurityException | IOException e) {
            throw new IllegalStateException("Failed to verify Google token", e);
        }
    }
}