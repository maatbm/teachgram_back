package com.teach3035.teachgram_back.controller;

import com.teach3035.teachgram_back.dto.req.PostReqDTO;
import com.teach3035.teachgram_back.dto.req.UpdatePostReqDTO;
import com.teach3035.teachgram_back.dto.res.PagePostResDTO;
import com.teach3035.teachgram_back.dto.res.PostResDTO;
import com.teach3035.teachgram_back.model.UserModel;
import com.teach3035.teachgram_back.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping
    public PostResDTO createPost(
            @AuthenticationPrincipal UserModel user,
            @Valid @RequestBody PostReqDTO request
    ) {
        return postService.createPost(user.getMail(), request);
    }

    @GetMapping("/feed")
    public PagePostResDTO getFeedPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return postService.getFeedPosts(page, size);
    }

    @GetMapping("/profile/{id}")
    public PagePostResDTO getUserPosts(
            @PathVariable(value = "id") Long id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return postService.getUserPosts(id, page, size);
    }

    @PatchMapping("/like/{id}")
    public Long like(@PathVariable(value = "id") Long id) {
        return postService.like(id);
    }

    @PatchMapping("/{id}")
    public PostResDTO updatePost(
            @PathVariable(value = "id") Long id,
            @AuthenticationPrincipal UserModel user,
            @Valid @RequestBody UpdatePostReqDTO request
    ) {
        return postService.updatePost(id, user, request);
    }

    @DeleteMapping("/{id}")
    public void deletePost(
            @PathVariable(value = "id") Long id,
            @AuthenticationPrincipal UserModel user
    ) {
        postService.deletePost(id, user);
    }
}
