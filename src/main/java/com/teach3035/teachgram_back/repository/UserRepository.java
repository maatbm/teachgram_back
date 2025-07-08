package com.teach3035.teachgram_back.repository;

import com.teach3035.teachgram_back.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByMailOrUsernameFieldOrPhone(String mail, String username, String phone);
}
