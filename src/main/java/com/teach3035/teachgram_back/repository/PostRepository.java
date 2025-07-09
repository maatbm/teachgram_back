package com.teach3035.teachgram_back.repository;

import com.teach3035.teachgram_back.model.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostModel, Long> {
}
