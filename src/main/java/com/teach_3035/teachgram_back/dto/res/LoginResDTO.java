package com.teach_3035.teachgram_back.dto.res;

public record LoginResDTO(
        String type,
        String token,
        long expirationDate
) {
}
