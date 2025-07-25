package com.teach3035.teachgram_back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@DynamicUpdate
@SoftDelete
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @Column(nullable = false, unique = true)
    @NonNull
    private String mail;

    @Column(name = "username", nullable = false, unique = true)
    @NonNull
    private String usernameField;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NonNull
    private String description;

    @Column(nullable = false, unique = true)
    @NonNull
    private String phone;

    @Column(nullable = false)
    @NonNull
    private String password;

    @Column(name = "profile_link", nullable = false)
    @NonNull
    private String profileLink;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @Setter(AccessLevel.NONE)
    private List<PostModel> posts = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "friendships",
            joinColumns = @JoinColumn(name = "user_id_1"),
            inverseJoinColumns = @JoinColumn(name = "user_id_2")
    )
    @JsonIgnore
    private List<UserModel> friends = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<SimpleGrantedAuthority>();
    }

    @Override
    public String getUsername() {
        return this.mail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserModel(@NonNull String mail) {
        this.mail = mail;
    }
}
