package com.teach3035.teachgram_back.service;

import com.teach3035.teachgram_back.model.UserModel;
import com.teach3035.teachgram_back.repository.UserRepository;
import com.teach3035.teachgram_back.util.UserUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendshipService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    UserUtils userUtils;

    @Transactional
    public void addFriend(String userAuthenticatedMail, Long otherUserId) {
        UserModel userAuthenticated = userUtils.getUserByMail(userAuthenticatedMail);
        UserModel otherUser = userUtils.getUserById(otherUserId);
        userAuthenticated.getFriends().add(otherUser);
        otherUser.getFriends().add(userAuthenticated);
        userRepository.save(userAuthenticated);
        userRepository.save(otherUser);
    }

    @Transactional
    public void removeFriend(String userAuthenticatedMail, Long otherUserId) {
        UserModel userAuthenticated = userUtils.getUserByMail(userAuthenticatedMail);
        UserModel otherUser = userUtils.getUserById(otherUserId);
        userAuthenticated.getFriends().remove(otherUser);
        otherUser.getFriends().remove(userAuthenticated);
        userRepository.save(userAuthenticated);
        userRepository.save(otherUser);
    }
}
