package com.teach3035.teachgram_back.service;

import com.teach3035.teachgram_back.dto.req.SigninReqDTO;
import com.teach3035.teachgram_back.dto.req.SignupUserReqDTO;
import com.teach3035.teachgram_back.dto.req.UpdateUserReqDTO;
import com.teach3035.teachgram_back.dto.res.JwtTokenResDTO;
import com.teach3035.teachgram_back.dto.res.PageUserResDTO;
import com.teach3035.teachgram_back.dto.res.UserResDTO;
import com.teach3035.teachgram_back.exception.custom.DuplicateFieldException;
import com.teach3035.teachgram_back.model.UserModel;
import com.teach3035.teachgram_back.repository.UserRepository;
import com.teach3035.teachgram_back.util.UserUtils;
import jakarta.transaction.Transactional;
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
    @Autowired
    UserUtils userUtils;

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

    public PageUserResDTO getAllNonDeletedUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserModel> users = userRepository.getAllNonDeleted(pageable);
        return this.pageUserResDTOBuilder(users);
    }

    public UserResDTO getUserProfile(Long id) {
        UserModel user = userUtils.getUserById(id);
        return this.userResDTOBuilder(user);
    }

    public UserResDTO updateUser(String mail, UpdateUserReqDTO request) {
        UserModel oldUser = userUtils.getUserByMail(mail);
        this.validateUniqueFields(request.mail(), request.username(), request.phone(), oldUser.getId());
        UserModel updatedUser = this.updateUserFields(request, oldUser);
        userRepository.save(updatedUser);
        return this.userResDTOBuilder(updatedUser);
    }

    @Transactional
    public void deleteUser(String mail) {
        userRepository.deleteByMail(mail);
    }

    private void validateUniqueFields(String mail, String username, String phone) {
        if (userRepository.existsByMailIncludingDeleted(mail))
            throw new DuplicateFieldException("Email already exists");
        if (userRepository.existsByUsernameFieldIncludingDeleted(username))
            throw new DuplicateFieldException("Username already exists");
        if (userRepository.existsByPhoneIncludingDeleted(phone))
            throw new DuplicateFieldException("Phone already exists");
    }

    private void validateUniqueFields(String mail, String username, String phone, Long id) {
        if (mail != null && userRepository.existsByMailAndIdNot(mail, id))
            throw new DuplicateFieldException("Email already exists");
        if (username != null && userRepository.existsByUsernameFieldAndIdNot(username, id))
            throw new DuplicateFieldException("Username already exists");
        if (phone != null && userRepository.existsByPhoneAndIdNot(phone, id))
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

    private PageUserResDTO pageUserResDTOBuilder(Page<UserModel> users) {
        List<UserResDTO> listUsers = users.stream().map(this::userResDTOBuilder).toList();
        return new PageUserResDTO(
                listUsers,
                users.getTotalElements(),
                users.getTotalPages()
        );
    }
}
