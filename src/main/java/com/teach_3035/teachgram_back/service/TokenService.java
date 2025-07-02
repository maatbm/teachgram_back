package com.teach_3035.teachgram_back.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.teach_3035.teachgram_back.dto.res.LoginResDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${security.jwt.secret}")
    private String secret;
    @Value("${security.jwt.issuer}")
    private String issuer;
    @Value("${security.jwt.expiration}")
    private int expiration;
    @Value("${security.jwt.token-prefix}")
    private String tokenPrefix;

    public LoginResDTO generateToken(String username) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        Instant expirationDate = this.getExpirationDate();
        String token = JWT.create()
                .withIssuer(issuer)
                .withSubject(username)
                .withExpiresAt(expirationDate)
                .sign(algorithm);
        return new LoginResDTO((tokenPrefix + " "), token, expirationDate.toEpochMilli());
    }

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token)
                .getSubject();
    }

    private Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(expiration).toInstant(ZoneOffset.UTC);
    }
}
