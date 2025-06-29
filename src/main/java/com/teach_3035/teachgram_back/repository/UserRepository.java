package com.teach_3035.teachgram_back.repository;

import com.teach_3035.teachgram_back.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmailOrUsernameOrPhoneNumber(String email, String username, String phoneNumber);
}
