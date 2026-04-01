package org.snoozy.snoozyauth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.snoozy.snoozyauth.dto.GoogleUserInfo;
import org.snoozy.snoozyauth.util.AuthType;
import org.snoozy.snoozyauth.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret-token}")
    private String secretToken;


    private static final long EXPIRATION = 960000000;

    public String generateTokenGoogle(GoogleUserInfo userInfo) {
        return Jwts.builder()
                .subject(userInfo.sub())
                .claim("email", userInfo.email())
                .claim("name", userInfo.name())
                .claim("auth", AuthType.GOOGLE.name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateTokenBasic(User user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("name", user.getUsername())
                .claim("auth", AuthType.BASIC.name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretToken);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
