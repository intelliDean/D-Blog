package com.api.dblog.controllers;

import com.api.dblog.data.dtos.entiies_dto.PostDto;
import com.api.dblog.data.dtos.requests.CreatePostRequest;
import com.api.dblog.data.dtos.responses.CreateResponse;
import com.api.dblog.data.dtos.responses.PostResponse;
import com.api.dblog.data.dtos.responses.UpdateResponse;
import com.api.dblog.data.models.Post;
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

    @PostMapping("/{userid}/create")
    public ResponseEntity<?> createPost(@PathVariable Long userid, @RequestBody CreatePostRequest createPostRequest) {
        CreateResponse createResponse = postService.createPost(userid, createPostRequest);

        return ResponseEntity.status(createResponse.getCode()).body(createResponse);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok(postService.getAll());
    }

    @GetMapping("{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId) {
        Post post = postService.getPost(postId);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/userPost/{userId}&{postId}")
    public ResponseEntity<?> getPostByUserId(@PathVariable Long userId, @PathVariable Long postId) {
        PostResponse response = postService.getPostByUserId(userId, postId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping(value = "{postId}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updatePostField(@PathVariable Long postId, @RequestBody JsonPatch updatePatch) {
        try {
        var response = postService.updateField(postId, updatePatch);
        return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostDto postDto) {
        UpdateResponse response = postService.updatePost(postId, postDto);

        return ResponseEntity.ok(response);
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

    @GetMapping("userPosts/{userId}")
    public ResponseEntity<?> getAllUserPosts(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.findAllUserPosts(userId));
    }
    @DeleteMapping("/userPosts/{userId}")
    public ResponseEntity<?> deleteAllUserPosts(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.deletePostByUserId(userId));
    }
}