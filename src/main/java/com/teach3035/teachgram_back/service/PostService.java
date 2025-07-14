package com.teach3035.teachgram_back.service;

import com.teach3035.teachgram_back.dto.req.PostReqDTO;
import com.teach3035.teachgram_back.dto.req.UpdatePostReqDTO;
import com.teach3035.teachgram_back.dto.res.PagePostResDTO;
import com.teach3035.teachgram_back.dto.res.PostResDTO;
import com.teach3035.teachgram_back.exception.custom.PostNotBelongsToUserException;
import com.teach3035.teachgram_back.exception.custom.ResourceNotFoundException;
import com.teach3035.teachgram_back.model.PostModel;
import com.teach3035.teachgram_back.model.UserModel;
import com.teach3035.teachgram_back.repository.PostRepository;
import com.teach3035.teachgram_back.repository.UserRepository;
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
    PostRepository postRepository;
    @Autowired
    UserUtils userUtils;
    @Autowired
    UserRepository userRepository;

    public PostResDTO createPost(String mail, PostReqDTO request) {
        UserModel user = userUtils.getUserByMail(mail);
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

    public PagePostResDTO getFeedPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostModel> posts = postRepository.getFeedPosts(pageable);
        return this.pagePostResDTOBuilder(posts);
    }

    public PagePostResDTO getUserPosts(Long id, int page, int size) {
        UserModel user = userUtils.getUserById(id);
        Pageable pageable = PageRequest.of(page, size);
        Page<PostModel> posts = postRepository.findByUser(user, pageable);
        return this.pagePostResDTOBuilder(posts);
    }

    @Transactional
    public Long like(Long id) {
        PostModel post = this.getPostById(id);
        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);
        return post.getLikes();
    }

    @Transactional
    public PostResDTO updatePost(Long id, UserModel user, UpdatePostReqDTO request) {
        PostModel oldPost = this.getPostById(id);
        this.postBelongsToUser(oldPost, user);
        PostModel updatedPost = this.updatePostFields(oldPost, request);
        postRepository.save(updatedPost);
        return this.postResDTOBuilder(updatedPost);
    }

    public void deletePost(Long id, UserModel user) {
        PostModel post = this.getPostById(id);
        this.postBelongsToUser(post, user);
        postRepository.delete(post);
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
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }

    private PostModel updatePostFields(PostModel post, UpdatePostReqDTO request) {
        Optional.ofNullable(request.title()).ifPresent(post::setTitle);
        Optional.ofNullable(request.description()).ifPresent(post::setDescription);
        Optional.ofNullable(request.photoLink()).ifPresent(post::setPhotoLink);
        Optional.ofNullable(request.videoLink()).ifPresent(post::setVideoLink);
        Optional.ofNullable(request.isPrivate()).ifPresent(post::setIsPrivate);
        return post;
    }

    private void postBelongsToUser(PostModel post, UserModel user) {
        if (!post.getUser().getMail().equals(user.getMail()))
            throw new PostNotBelongsToUserException("Post não pertence a este usuário");
    }

    private PagePostResDTO pagePostResDTOBuilder(Page<PostModel> posts) {
        List<PostResDTO> listPosts = posts.stream().map(this::postResDTOBuilder).toList();
        return new PagePostResDTO(
                listPosts,
                posts.getTotalElements(),
                posts.getTotalPages()
        );
    }
}
