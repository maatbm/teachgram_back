package com.teach_3035.teachgram_back.service;

import com.teach_3035.teachgram_back.dto.req.RegisterUserReqDTO;
import com.teach_3035.teachgram_back.dto.res.RegisterUserResDTO;
import com.teach_3035.teachgram_back.model.UserModel;
import com.teach_3035.teachgram_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public RegisterUserResDTO registerUser(RegisterUserReqDTO request) {
        Optional<UserModel> user = userRepository.findByEmailOrUsernameOrPhoneNumber(request.email(), request.username(), request.phoneNumber());
        if (user.isPresent()) {
            if(user.get().getEmail().equals(request.email()))
                throw new IllegalArgumentException("Email already exists");
            if(user.get().getUsername().equals(request.username()))
                throw new IllegalArgumentException("Username already exists");
            if(user.get().getPhoneNumber().equals(request.phoneNumber()))
                throw new IllegalArgumentException("Phone number already exists");
        }
        UserModel newUser = new UserModel(
                request.name(),
                request.email(),
                request.username(),
                request.description(),
                request.phoneNumber(),
                request.password(),
                request.profilePictureUrl()
        );
        userRepository.save(newUser);
        return new RegisterUserResDTO(newUser.getId(), newUser.getEmail());
    }
}
