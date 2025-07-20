package com.teach3035.teachgram_back.dto.res;

public record UserResDTO(
        Long id,
        String name,
        String mail,
        String username,
        String description,
        String phone,
        String profileLink,
        Integer totalPosts,
        Integer totalFriends,
        boolean isFriend
) {
}
