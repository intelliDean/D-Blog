package com.api.dblog.controllers;

import com.api.dblog.data.dtos.requests.CommentRequest;
import com.api.dblog.data.dtos.responses.CreateResponse;
import com.api.dblog.services.comment_services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comment/")
public class CommentController {
    private final CommentService commentService;
    
    @PostMapping("comment")
    public ResponseEntity<?> createComment(@RequestBody CommentRequest commentRequest) {
        CreateResponse createdComment = commentService.createComment(commentRequest);

        return ResponseEntity.status(createdComment.getCode()).body(createdComment);
    }
}
