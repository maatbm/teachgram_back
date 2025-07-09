package com.teach3035.teachgram_back.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.teach3035.teachgram_back.dto.res.JwtTokenResDTO;
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

    public JwtTokenResDTO generateToken(String email) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        Instant expirantionDate = this.getExpirationDate();
        String token = JWT.create()
                .withIssuer(issuer)
                .withSubject(email)
                .withExpiresAt(expirantionDate)
                .sign(algorithm);
        return new JwtTokenResDTO(tokenPrefix + " ", token, expirantionDate.toEpochMilli());
    }

    private Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(expiration).toInstant(ZoneOffset.UTC);
    }

}
