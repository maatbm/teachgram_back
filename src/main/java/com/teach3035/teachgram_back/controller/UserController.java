package com.teach3035.teachgram_back.controller;

import com.teach3035.teachgram_back.dto.req.SigninReqDTO;
import com.teach3035.teachgram_back.dto.req.SignupUserReqDTO;
import com.teach3035.teachgram_back.dto.req.UpdateUserReqDTO;
import com.teach3035.teachgram_back.dto.res.JwtTokenResDTO;
import com.teach3035.teachgram_back.dto.res.UserResDTO;
import com.teach3035.teachgram_back.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<UserResDTO> getAllNonDeletedUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return userService.getAllNonDeletedUsers(page, size);
    }

    @GetMapping
    public UserResDTO getUserProfile(@AuthenticationPrincipal UserDetails user) {
        return userService.getUserProfile(user.getUsername());
    }

    @PatchMapping
    public UserResDTO updateUser(
            @AuthenticationPrincipal UserDetails user,
            @Valid @RequestBody UpdateUserReqDTO request
    ) {
        return userService.updateUser(user.getUsername(), request);
    }
}
