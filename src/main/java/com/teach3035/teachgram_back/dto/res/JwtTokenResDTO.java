package com.teach3035.teachgram_back.dto.res;

public record JwtTokenResDTO(
        String type,
        String token,
        Long expiration
) {
}
