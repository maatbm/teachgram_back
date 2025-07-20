package com.teach3035.teachgram_back.controller;

import com.teach3035.teachgram_back.dto.req.SigninReqDTO;
import com.teach3035.teachgram_back.dto.req.SignupUserReqDTO;
import com.teach3035.teachgram_back.dto.req.UpdateUserReqDTO;
import com.teach3035.teachgram_back.dto.res.JwtTokenResDTO;
import com.teach3035.teachgram_back.dto.res.PageUserResDTO;
import com.teach3035.teachgram_back.dto.res.UserResDTO;
import com.teach3035.teachgram_back.model.UserModel;
import com.teach3035.teachgram_back.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public UserResDTO signup(@Valid @RequestBody SignupUserReqDTO request) {
        return userService.signup(request);
    }

    @PostMapping("/signin")
    public JwtTokenResDTO signgin(@Valid @RequestBody SigninReqDTO request) {
        return userService.signin(request);
    }

    @GetMapping("/all")
    public PageUserResDTO getAllNonDeletedUsers(
            @AuthenticationPrincipal UserModel user,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return userService.getAllNonDeletedUsers(user.getMail(), page, size);
    }

    @GetMapping("/profile/{id}")
    public UserResDTO getUserProfile(
            @AuthenticationPrincipal UserModel user,
            @PathVariable(value = "id") Long id
    ) {
        return userService.getUserProfileById(user.getMail(), id);
    }

    @GetMapping("/profile")
    public UserResDTO getAuthenticatedUserProfile(@AuthenticationPrincipal UserModel user) {
        return userService.getAuthenticatedUserProfile(user.getMail());
    }

    @PatchMapping
    public UserResDTO updateUser(
            @AuthenticationPrincipal UserModel user,
            @Valid @RequestBody UpdateUserReqDTO request
    ) {
        return userService.updateUser(user.getMail(), request);
    }

    @DeleteMapping
    public void deleteUser(@AuthenticationPrincipal UserModel user) {
        userService.deleteUser(user.getMail());
    }

    @GetMapping("/friends")
    public PageUserResDTO getFriends(
            @AuthenticationPrincipal UserModel user,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return userService.getUserFriends(user.getMail(), page, size);
    }
}
