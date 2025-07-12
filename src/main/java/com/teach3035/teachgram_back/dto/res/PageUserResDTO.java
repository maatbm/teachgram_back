package com.teach3035.teachgram_back.dto.res;

import java.util.List;

public record PageUserResDTO(
        List<UserResDTO> users,
        Long totalItems,
        int totalPages
) {
}
