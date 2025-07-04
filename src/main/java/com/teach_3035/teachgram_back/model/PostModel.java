package com.teach_3035.teachgram_back.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table(name = "posts")
@Entity
@SoftDelete
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class PostModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NonNull
    private String description;

    @Column(name = "photo_url")
    @Setter
    private String photoUrl;

    @Column(name = "video_url")
    @Setter
    private String videoUrl;

    @Column(name = "private", nullable = false)
    @NonNull
    private Boolean privatePost;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @NonNull
    private UserModel user;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
