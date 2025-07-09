package com.teach3035.teachgram_back.repository;

import com.teach3035.teachgram_back.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByMail(String mail);

    boolean existsByMail(String mail);

    boolean existsByUsernameField(String username);

    boolean existsByPhone(String phone);

    @NativeQuery("SELECT * FROM users WHERE deleted = false")
    Page<UserModel> getAllNonDeleted(Pageable pageable);
}
