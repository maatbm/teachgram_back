package com.teach3035.teachgram_back.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@SoftDelete
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class PostModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NonNull
    private String description;

    @Column(name = "photo_link")
    private String photoLink;

    @Column(name = "video_link")
    private String videoLink;

    @Column(name = "private", nullable = false)
    @NonNull
    private Boolean isPrivate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @NonNull
    private UserModel user;

    @Column(nullable = false)
    private Long likes;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt;
}
