package com.api.dblog.controllers;

import com.api.dblog.data.dtos.requests.CommentRequest;
import com.api.dblog.data.dtos.responses.CreateResponse;
import com.api.dblog.services.comment_services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{userId}/{postId}/comment")
    public ResponseEntity<?> createComment(
            @PathVariable Long userId, @PathVariable Long postId, @RequestBody CommentRequest commentRequest) {
        CreateResponse createdComment = commentService.createComment(userId, postId, commentRequest);
        return ResponseEntity.ok(createdComment);
    }
}
