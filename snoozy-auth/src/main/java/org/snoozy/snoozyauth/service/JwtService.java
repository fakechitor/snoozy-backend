package org.snoozy.snoozyauth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.snoozy.snoozyauth.dto.GoogleUserInfo;
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

    public String generateToken(GoogleUserInfo userInfo) {
        return Jwts.builder()
                .subject(userInfo.sub())
                .claim("", "")
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
