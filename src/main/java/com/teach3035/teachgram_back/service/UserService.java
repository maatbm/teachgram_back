package com.teach3035.teachgram_back.service;

import com.teach3035.teachgram_back.dto.req.SigninReqDTO;
import com.teach3035.teachgram_back.dto.req.SignupUserReqDTO;
import com.teach3035.teachgram_back.dto.req.UpdateUserReqDTO;
import com.teach3035.teachgram_back.dto.res.JwtTokenResDTO;
import com.teach3035.teachgram_back.dto.res.UserResDTO;
import com.teach3035.teachgram_back.exception.custom.DuplicateFieldException;
import com.teach3035.teachgram_back.exception.custom.ResourceNotFoundException;
import com.teach3035.teachgram_back.model.UserModel;
import com.teach3035.teachgram_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
        String encryptedPassword = passwordEncoder.encode(request.password());
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

    public List<UserResDTO> getAllNonDeletedUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserModel> users = userRepository.getAllNonDeleted(pageable);
        return users.stream().map(this::userResDTOBuilder).toList();
    }

    public UserResDTO getUserProfile(UserModel user) {
        return this.userResDTOBuilder(user);
    }

    public UserResDTO updateUser(String mail, UpdateUserReqDTO request) {
        this.validateUniqueFields(request.mail(), request.username(), request.phone());
        UserModel oldUser = this.getUserByMail(mail);
        UserModel updatedUser = this.updateUserFields(request, oldUser);
        userRepository.save(updatedUser);
        return this.userResDTOBuilder(updatedUser);
    }

    public void deleteUser(UserModel user) {
        userRepository.delete(user);
    }

    private void validateUniqueFields(String mail, String username, String phone) {
        if (userRepository.existsByMail(mail))
            throw new DuplicateFieldException("Email already exists");
        else if (userRepository.existsByUsernameField(username))
            throw new DuplicateFieldException("Username already exists");
        else if (userRepository.existsByPhone(phone))
            throw new DuplicateFieldException("Phone already exists");
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

    private UserModel updateUserFields(UpdateUserReqDTO request, UserModel user) {
        Optional.ofNullable(request.name()).ifPresent(user::setName);
        Optional.ofNullable(request.mail()).ifPresent(user::setMail);
        Optional.ofNullable(request.username()).ifPresent(user::setUsernameField);
        Optional.ofNullable(request.description()).ifPresent(user::setDescription);
        Optional.ofNullable(request.phone()).ifPresent(user::setPhone);
        Optional.ofNullable(request.password()).ifPresent(pw -> user.setPassword(passwordEncoder.encode(pw)));
        Optional.ofNullable(request.profileLink()).ifPresent(user::setProfileLink);
        return user;
    }

    private UserModel getUserByMail(String mail) {
        return userRepository
                .findByMail(mail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }
}
