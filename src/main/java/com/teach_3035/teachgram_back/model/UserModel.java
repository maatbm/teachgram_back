package com.teach_3035.teachgram_back.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@SoftDelete
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @Column(nullable = false, unique = true)
    @NonNull
    private String email;

    @Column(nullable = false, unique = true)
    @NonNull
    private String username;

    @Column(nullable = false)
    @NonNull
    private String description;

    @Column(name = "phone_number", nullable = false, unique = true)
    @NonNull
    private String phoneNumber;

    @Column(nullable = false)
    @NonNull
    private String password;

    @Column(name = "profile_picture", nullable = false)
    @NonNull
    private String profilePictureUrl;

}
