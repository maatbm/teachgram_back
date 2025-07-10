package com.teach3035.teachgram_back.controller;

import com.teach3035.teachgram_back.dto.req.PostReqDTO;
import com.teach3035.teachgram_back.dto.req.UpdatePostReqDTO;
import com.teach3035.teachgram_back.dto.res.PostResDTO;
import com.teach3035.teachgram_back.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping
    public PostResDTO createPost(
            @AuthenticationPrincipal UserDetails user,
            @Valid @RequestBody PostReqDTO request
    ) {
        return postService.createPost(user.getUsername(), request);
    }

    @GetMapping("/feed")
    public List<PostResDTO> getFeedPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return postService.getFeedPosts(page, size);
    }

    @GetMapping("/profile")
    public List<PostResDTO> getUserPosts(
            @AuthenticationPrincipal UserDetails user,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return postService.getUserPosts(user.getUsername(), page, size);
    }

    @GetMapping("/like/{id}")
    public Long like(@PathVariable(value = "id") Long id) {
        return postService.like(id);
    }

    @PatchMapping("/{id}")
    public PostResDTO updatePost(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody UpdatePostReqDTO request
    ) {
        return postService.updatePost(id, request);
    }
}
