package com.teach3035.teachgram_back.util;

import com.teach3035.teachgram_back.model.UserModel;
import com.teach3035.teachgram_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserUtils {
    @Autowired
    UserRepository userRepository;

    public UserModel getUserByEmail(String email) {
        return userRepository
                .findByMail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
