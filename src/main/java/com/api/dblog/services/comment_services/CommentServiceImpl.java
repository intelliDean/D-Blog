package com.api.dblog.services.comment_services;

import com.api.dblog.data.dtos.entiies_dto.CommentDto;
import com.api.dblog.data.dtos.requests.CommentRequest;
import com.api.dblog.data.dtos.responses.CreateResponse;
import com.api.dblog.data.exceptions.ServiceException;
import com.api.dblog.data.models.Comment;
import com.api.dblog.data.models.Post;
import com.api.dblog.data.repositories.CommentRepository;
import com.api.dblog.data.repositories.PostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.ToDoubleBiFunction;

@Slf4j
@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper mapper;

    // TODO: 17-Feb-23 remember to add code in the UpdateResponse for user and post 
    @Override
    public CreateResponse createComment(CommentRequest commentRequest) {
        Post post = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(()->new ServiceException("Post could not be found"));

        Comment comment = Comment.builder()
                .username(commentRequest.getUsername())
                .content(commentRequest.getContent())
                .createdAt(LocalDateTime.now().toString())
                .build();

        post.getComment().add(comment);

        postRepository.save(post);

        Comment savedComment = commentRepository.save(comment);
        
        return CreateResponse.builder()
                .id(savedComment.getId())
                .code(HttpStatus.CREATED.value())
                .message("Comment created successfully")
                .isPosted(true)
                .build();
    }

    @Override
    public CommentDto getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new ServiceException("Comment could not be found"));
        return mapper.map(comment, CommentDto.class);
    }
}
