package com.api.dblog.services.comment_services;

import com.api.dblog.data.dtos.entiies_dto.CommentDto;
import com.api.dblog.data.dtos.requests.CommentRequest;
import com.api.dblog.data.dtos.responses.CreateResponse;

import java.util.List;

public interface CommentService {
    CreateResponse createComment(Long userid, Long postId, CommentRequest commentRequest);
    CommentDto getComment(Long commentId);
}
