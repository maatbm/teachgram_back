package com.teach3035.teachgram_back.repository;

import com.teach3035.teachgram_back.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByMail(String mail);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM users WHERE mail = :mail)", nativeQuery = true)
    boolean existsByMailIncludingDeleted(@Param("mail") String mail);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM users WHERE username = :username)", nativeQuery = true)
    boolean existsByUsernameFieldIncludingDeleted(@Param("username") String username);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM users WHERE phone = :phone)", nativeQuery = true)
    boolean existsByPhoneIncludingDeleted(@Param("phone") String phone);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM users WHERE mail = :mail AND id != :id)", nativeQuery = true)
    boolean existsByMailAndIdNot(@Param("mail") String mail, @Param("id") Long id);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM users WHERE username = :username AND id != :id)", nativeQuery = true)
    boolean existsByUsernameFieldAndIdNot(@Param("username") String username, @Param("id") Long id);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM users WHERE phone = :phone AND id != :id)", nativeQuery = true)
    boolean existsByPhoneAndIdNot(@Param("phone") String phone, @Param("id") Long id);

    void deleteByMail(String mail);

    @Query("SELECT f FROM UserModel u JOIN u.friends f WHERE u.id = :userId")
    Page<UserModel> findFriendsByUserId(@Param("userId") Long userId, Pageable pageable);
}
