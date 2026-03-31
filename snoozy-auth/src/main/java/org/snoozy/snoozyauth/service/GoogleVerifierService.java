package org.snoozy.snoozyauth.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.snoozy.snoozyauth.dto.GoogleUserInfo;
import org.snoozy.snoozyauth.dto.mapper.GoogleAuthMapper;
import org.snoozy.snoozyauth.producer.GoogleAuthProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleVerifierService  {

    private final GoogleIdTokenVerifier verifier;

    private final String webClientId;

    public GoogleVerifierService(@Value("${google.oauth.web-client-id}") String webClientId, GoogleAuthProducer googleAuthProducer, GoogleAuthMapper googleAuthMapper) {
        this.webClientId = webClientId.trim();
        this.verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance()
        )
                .setAudience(Collections.singletonList(this.webClientId))
                .build();
    }

    public GoogleUserInfo verify(String token) {
        try {
            GoogleIdToken idToken = verifier.verify(token);

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
