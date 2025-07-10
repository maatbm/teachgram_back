package com.teach3035.teachgram_back.service;

import com.teach3035.teachgram_back.dto.req.PostReqDTO;
import com.teach3035.teachgram_back.dto.req.UpdatePostReqDTO;
import com.teach3035.teachgram_back.dto.res.PostResDTO;
import com.teach3035.teachgram_back.model.PostModel;
import com.teach3035.teachgram_back.model.UserModel;
import com.teach3035.teachgram_back.repository.PostRepository;
import com.teach3035.teachgram_back.util.UserUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    UserUtils userUtils;
    @Autowired
    PostRepository postRepository;

    public PostResDTO createPost(String email, PostReqDTO request) {
        UserModel user = userUtils.getUserByEmail(email);
        PostModel post = new PostModel(
                request.title(),
                request.description(),
                request.photoLink(),
                request.videoLink(),
                request.isPrivate(),
                user
        );
        postRepository.save(post);
        return this.postResDTOBuilder(post);
    }

    public List<PostResDTO> getFeedPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostModel> posts = postRepository.getFeedPosts(pageable);
        return posts.stream().map(this::postResDTOBuilder).toList();
    }

    public List<PostResDTO> getUserPosts(String email, int page, int size) {
        UserModel user = userUtils.getUserByEmail(email);
        Pageable pageable = PageRequest.of(page, size);
        Page<PostModel> posts = postRepository.findByUser(user, pageable);
        return posts.stream().map(this::postResDTOBuilder).toList();
    }

    @Transactional
    public Long like(Long id) {
        PostModel post = this.getPostById(id);
        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);
        return post.getLikes();
    }

    @Transactional
    public PostResDTO updatePost(Long id, UpdatePostReqDTO request) {
        PostModel oldPost = this.getPostById(id);
        PostModel updatedPost = this.updatePostFields(oldPost, request);
        postRepository.save(updatedPost);
        return this.postResDTOBuilder(updatedPost);
    }

    private PostResDTO postResDTOBuilder(PostModel post) {
        return new PostResDTO(
                post.getId(),
                post.getTitle(),
                post.getDescription(),
                post.getPhotoLink(),
                post.getVideoLink(),
                post.getIsPrivate(),
                post.getLikes(),
                post.getCreatedAt()
        );
    }

    private PostModel getPostById(Long id) {
        return postRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    private PostModel updatePostFields(PostModel post, UpdatePostReqDTO request) {
        Optional.ofNullable(request.title()).ifPresent(post::setTitle);
        Optional.ofNullable(request.description()).ifPresent(post::setDescription);
        Optional.ofNullable(request.photoLink()).ifPresent(post::setPhotoLink);
        Optional.ofNullable(request.videoLink()).ifPresent(post::setVideoLink);
        Optional.ofNullable(request.isPrivate()).ifPresent(post::setIsPrivate);
        return post;
    }
}
