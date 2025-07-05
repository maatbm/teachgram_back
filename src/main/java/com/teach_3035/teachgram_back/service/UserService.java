package com.teach_3035.teachgram_back.service;

import com.teach_3035.teachgram_back.dto.req.LoginReqDTO;
import com.teach_3035.teachgram_back.dto.req.RegisterUserReqDTO;
import com.teach_3035.teachgram_back.dto.req.UpdateUserReqDTO;
import com.teach_3035.teachgram_back.dto.res.LoginResDTO;
import com.teach_3035.teachgram_back.dto.res.UserResDTO;
import com.teach_3035.teachgram_back.exception.custom.UserAlreadyExistsException;
import com.teach_3035.teachgram_back.exception.custom.UserNotFoundException;
import com.teach_3035.teachgram_back.model.UserModel;
import com.teach_3035.teachgram_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    public UserResDTO registerUser(RegisterUserReqDTO request) {
        Optional<UserModel> user = userRepository.findByEmailOrUsernameOrPhoneNumber(request.email(), request.username(), request.phoneNumber());
        if (user.isPresent()) {
            if (user.get().getEmail().equals(request.email()))
                throw new UserAlreadyExistsException("Email already exists");
            if (user.get().getUsername().equals(request.username()))
                throw new UserAlreadyExistsException("Username already exists");
            if (user.get().getPhoneNumber().equals(request.phoneNumber()))
                throw new UserAlreadyExistsException("Phone number already exists");
        }
        String encryptdedPassword = passwordEncoder.encode(request.password());
        UserModel newUser = new UserModel(
                request.name(),
                request.email(),
                request.username(),
                request.description(),
                request.phoneNumber(),
                encryptdedPassword,
                request.profilePictureUrl()
        );
        userRepository.save(newUser);
        return new UserResDTO(
                newUser.getId(),
                newUser.getName(),
                newUser.getEmail(),
                newUser.getUsername(),
                newUser.getDescription(),
                newUser.getPhoneNumber()
        );
    }

    public LoginResDTO loginUser(LoginReqDTO request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        authenticationManager.authenticate(token);
        return tokenService.generateToken(request.email());
    }

    public UserResDTO updateUser(Long id, UpdateUserReqDTO request) {
        UserModel user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        Optional.ofNullable(request.name()).ifPresent(user::setName);
        Optional.ofNullable(request.email()).ifPresent(user::setEmail);
        Optional.ofNullable(request.username()).ifPresent(user::setUsername);
        Optional.ofNullable(request.description()).ifPresent(user::setDescription);
        Optional.ofNullable(request.phoneNumber()).ifPresent(user::setPhoneNumber);
        Optional.ofNullable(request.password()).ifPresent(pw -> user.setPassword(passwordEncoder.encode(pw)));
        Optional.ofNullable(request.profilePictureUrl()).ifPresent(user::setProfilePictureUrl);
        userRepository.save(user);
        return new UserResDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.getDescription(),
                user.getPhoneNumber()
        );
    }

    public void deleteUser(Long id) {
        UserModel user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }
}
