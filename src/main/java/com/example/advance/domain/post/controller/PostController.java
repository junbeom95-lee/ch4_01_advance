package com.example.advance.domain.post.controller;

import com.example.advance.domain.post.model.dto.PostDto;
import com.example.advance.domain.post.model.dto.PostSummaryDto;
import com.example.advance.domain.post.model.request.CreatePostRequest;
import com.example.advance.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;


    @PostMapping("")
    public ResponseEntity<PostDto> createPost(@AuthenticationPrincipal User user, @RequestBody CreatePostRequest request) {
        return ResponseEntity.ok(postService.createPost(user.getUsername(), request.getContent()));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<PostDto>> getPostListByUsername(@PathVariable String username) {

        return ResponseEntity.ok(postService.getPostListByUsername(username));
    }

    @GetMapping("/user/{username}/detail")
    public ResponseEntity<List<PostSummaryDto>> getPostListDetailByUsername(@PathVariable String username) {
        return ResponseEntity.ok(postService.getPostSummaryListByUsername(username));
    }


}
