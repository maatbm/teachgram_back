package com.teach_3035.teachgram_back.controller;

import com.teach_3035.teachgram_back.dto.req.LoginReqDTO;
import com.teach_3035.teachgram_back.dto.req.RegisterUserReqDTO;
import com.teach_3035.teachgram_back.dto.res.LoginResDTO;
import com.teach_3035.teachgram_back.dto.res.RegisterUserResDTO;
import com.teach_3035.teachgram_back.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public RegisterUserResDTO registerUser(@Valid @RequestBody RegisterUserReqDTO request) {
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public LoginResDTO loginUser (@Valid @RequestBody LoginReqDTO request) {
        return userService.loginUser(request);
    }
}
