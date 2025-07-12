package com.teach3035.teachgram_back.util;

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
}
