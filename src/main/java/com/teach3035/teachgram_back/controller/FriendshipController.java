package com.teach3035.teachgram_back.controller;

import com.teach3035.teachgram_back.model.UserModel;
import com.teach3035.teachgram_back.service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friendship")
public class FriendshipController {
    @Autowired
    FriendshipService friendshipService;

    @PostMapping("/add/{id}")
    public void addFriendsHip(
            @AuthenticationPrincipal UserModel user,
            @PathVariable(value = "id") Long id
    ) {
        friendshipService.addFriend(user.getMail(), id);
    }

    @DeleteMapping("/{id}")
    public void deleteFriendship(
            @AuthenticationPrincipal UserModel user,
            @PathVariable(value = "id") Long id
    ) {
        friendshipService.removeFriend(user.getMail(), id);
    }
}
