package com.api.dblog.controllers;

import com.api.dblog.data.dtos.entiies_dto.PostDto;
import com.api.dblog.data.dtos.requests.CreatePostRequest;
import com.api.dblog.data.dtos.responses.CreateResponse;
import com.api.dblog.data.dtos.responses.PostResponse;
import com.api.dblog.data.dtos.responses.UpdateResponse;
import com.api.dblog.services.post_service.PostService;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody CreatePostRequest createPostRequest) {
        CreateResponse createResponse = postService.createPost(createPostRequest);

        return ResponseEntity.status(createResponse.getCode()).body(createResponse);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok(postService.getAll());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId) {
        PostResponse postResponse = postService.getPost(postId);
        return ResponseEntity.ok(postResponse);
    }

    @PatchMapping(value = "/{postId}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody JsonPatch updatePatch) {
        return ResponseEntity.ok(postService.updateField(postId, updatePatch));
    }
    @DeleteMapping("{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        UpdateResponse response = postService.deletePost(postId);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllPosts() {
        UpdateResponse updateResponse = postService.deleteAll();
        return ResponseEntity.ok(updateResponse);
    }
}