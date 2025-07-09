package com.teach3035.teachgram_back.service;

import com.teach3035.teachgram_back.dto.req.PostReqDTO;
import com.teach3035.teachgram_back.dto.res.PostResDTO;
import com.teach3035.teachgram_back.model.PostModel;
import com.teach3035.teachgram_back.model.UserModel;
import com.teach3035.teachgram_back.repository.PostRepository;
import com.teach3035.teachgram_back.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    private PostResDTO postResDTOBuilder(PostModel post){
        return new PostResDTO(
                post.getId(),
                post.getTitle(),
                post.getDescription(),
                post.getPhotoLink(),
                post.getVideoLink(),
                post.getLikes(),
                post.getCreatedAt()
        );
    }
}
