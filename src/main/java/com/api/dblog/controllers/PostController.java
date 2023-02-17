package com.api.dblog.controllers;

import com.api.dblog.data.dtos.PostDto;
import com.api.dblog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class PostController {
    private PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("create")
    public ResponseEntity<PostDto> createPost(PostDto postDto) {
        return ResponseEntity.ok(postService.savePost(postDto));
    }
}