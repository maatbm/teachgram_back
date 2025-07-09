package com.teach3035.teachgram_back.service;

import com.teach3035.teachgram_back.dto.req.SigninReqDTO;
import com.teach3035.teachgram_back.dto.req.SignupUserReqDTO;
import com.teach3035.teachgram_back.dto.res.JwtTokenResDTO;
import com.teach3035.teachgram_back.dto.res.UserResDTO;
import com.teach3035.teachgram_back.model.UserModel;
import com.teach3035.teachgram_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenService tokenService;

    public UserResDTO signup(SignupUserReqDTO request) {
        this.validateUniqueFields(request.mail(), request.username(), request.phone());
        String encryptedPassword = this.encryptPassword(request.password());
        UserModel newUser = new UserModel(
                request.name(),
                request.mail(),
                request.username(),
                request.description(),
                request.phone(),
                encryptedPassword,
                request.profileLink()
        );
        userRepository.save(newUser);
        return this.userResDTOBuilder(newUser);
    }

    public JwtTokenResDTO signin(SigninReqDTO request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.mail(), request.password());
        authenticationManager.authenticate(token);
        return tokenService.generateToken(request.mail());
    }

    private void validateUniqueFields(String mail, String username, String phone) {
        Optional<UserModel> user = userRepository.findByMailOrUsernameFieldOrPhone(mail, username, phone);
        if (user.isPresent()) {
            if (user.get().getMail().equals(mail))
                throw new RuntimeException("Email already exsists");
            if (user.get().getUsernameField().equals(username))
                throw new RuntimeException("Username already exsists");
            if (user.get().getPhone().equals(phone))
                throw new RuntimeException("Phone already exsists");
        }
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private UserResDTO userResDTOBuilder(UserModel user) {
        return new UserResDTO(
                user.getId(),
                user.getName(),
                user.getMail(),
                user.getUsernameField(),
                user.getDescription(),
                user.getPhone(),
                user.getProfileLink()
        );
    }
}
