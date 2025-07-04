package com.teach_3035.teachgram_back.controller;

import com.teach_3035.teachgram_back.dto.req.LoginReqDTO;
import com.teach_3035.teachgram_back.dto.req.RegisterUserReqDTO;
import com.teach_3035.teachgram_back.dto.res.LoginResDTO;
import com.teach_3035.teachgram_back.dto.res.UserResDTO;
import com.teach_3035.teachgram_back.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public UserResDTO registerUser(@Valid @RequestBody RegisterUserReqDTO request) {
        return userService.registerUser(request);
    }

    @PostMapping("/signin")
    public LoginResDTO loginUser (@Valid @RequestBody LoginReqDTO request) {
        return userService.loginUser(request);
    }
}
