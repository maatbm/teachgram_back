package com.teach3035.teachgram_back.util;

import com.teach3035.teachgram_back.dto.res.UserResDTO;
import com.teach3035.teachgram_back.exception.custom.ResourceNotFoundException;
import com.teach3035.teachgram_back.model.UserModel;
import com.teach3035.teachgram_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserUtils {
    @Autowired
    UserRepository userRepository;

    public UserModel getUserByMail(String mail) {
        return userRepository
                .findByMail(mail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserModel getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserResDTO userResDTOBuilder(UserModel user, UserModel authenticatedUser) {
        return new UserResDTO(
                user.getId(),
                user.getName(),
                user.getMail(),
                user.getUsernameField(),
                user.getDescription(),
                user.getPhone(),
                user.getProfileLink(),
                user.getPosts().size(),
                user.getFriends().size(),
                authenticatedUser.getFriends().contains(user)
        );
    }

    public UserResDTO userResDTOBuilder(UserModel user) {
        return new UserResDTO(
                user.getId(),
                user.getName(),
                user.getMail(),
                user.getUsernameField(),
                user.getDescription(),
                user.getPhone(),
                user.getProfileLink(),
                user.getPosts() != null ? user.getPosts().size() : 0,
                user.getFriends() != null ? user.getFriends().size() : 0,
                false
        );
    }
}
