package com.teach3035.teachgram_back.repository;

import com.teach3035.teachgram_back.model.PostModel;
import com.teach3035.teachgram_back.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostModel, Long> {
    @Query("SELECT p FROM PostModel p WHERE p.isPrivate = false")
    Page<PostModel> getFeedPosts(Pageable pageable);

    Page<PostModel> findByUser(UserModel user, Pageable pageable);
}
