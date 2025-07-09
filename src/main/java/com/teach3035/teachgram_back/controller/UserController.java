package com.teach3035.teachgram_back.controller;

import com.teach3035.teachgram_back.dto.req.SigninReqDTO;
import com.teach3035.teachgram_back.dto.req.SignupUserReqDTO;
import com.teach3035.teachgram_back.dto.res.SigninResDTO;
import com.teach3035.teachgram_back.dto.res.UserResDTO;
import com.teach3035.teachgram_back.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public SigninResDTO signgin(@Valid @RequestBody SigninReqDTO request) {
        return userService.signin(request);
    }
}
