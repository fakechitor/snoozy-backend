package org.snoozy.snoozyauth.service;

import lombok.RequiredArgsConstructor;
import org.snoozy.snoozyauth.dto.AuthResponse;
import org.snoozy.snoozyauth.dto.GoogleLoginRequest;
import org.snoozy.snoozyauth.dto.GoogleUserInfo;
import org.snoozy.snoozyauth.dto.mapper.GoogleAuthMapper;
import org.snoozy.snoozyauth.model.Avatar;
import org.snoozy.snoozyauth.model.User;
import org.snoozy.snoozyauth.producer.GoogleAuthProducer;
import org.snoozy.snoozyauth.repository.AvatarRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleAuthService {

    private final GoogleAuthProducer googleAuthProducer;

    private final GoogleAuthMapper googleAuthMapper;

    private final GoogleVerifierService googleAuthVerifier;

    private final JwtService jwtService;

    private final AvatarRepository avatarRepository;

    public AuthResponse authenticate(GoogleLoginRequest googleLoginRequest) {
        String idTokenString = googleLoginRequest.idToken().trim();
        String clearPhoneNumber = getPhoneNumber(googleLoginRequest.phoneNumber());

        GoogleUserInfo userInfo = googleAuthVerifier.verify(idTokenString);

        Avatar avatar = new Avatar();
        avatar.setObjectKey(userInfo.pictureUrl());
        avatar.setContentType("image/png");
        avatar.setSizeBytes(123L);
        avatarRepository.save(avatar);

        User user = new User();
        user.setPhoneNumber(clearPhoneNumber);
        user.setUsername(userInfo.name());
        user.setEmail(userInfo.email());
        user.setAvatar(avatar);

//        googleAuthProducer.sendGoogleAuthEvent(googleAuthMapper.toEvent(userInfo));

        return new AuthResponse(jwtService.generateTokenGoogle(user));
    }

    private String getPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && phoneNumber.startsWith("+")) {
            return phoneNumber.substring(1);
        }

        return phoneNumber;
    }
}