package com.api.dblog.services.comment_services;

import com.api.dblog.data.dtos.entiies_dto.CommentDto;
import com.api.dblog.data.dtos.requests.CommentRequest;
import com.api.dblog.data.dtos.responses.CreateResponse;
import com.api.dblog.data.exceptions.ServiceException;
import com.api.dblog.data.models.AppUser;
import com.api.dblog.data.models.Comment;
import com.api.dblog.data.models.Post;
import com.api.dblog.data.repositories.AppUserRepository;
import com.api.dblog.data.repositories.CommentRepository;
import com.api.dblog.data.repositories.PostRepository;
import com.api.dblog.services.appUser_services.AppUserService;
import com.api.dblog.services.post_service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final AppUserService appUserService;
    private final ModelMapper mapper;

    // TODO: 17-Feb-23 remember to add code in the UpdateResponse for user and post 
    @Override
    public CreateResponse createComment(Long userId, Long postId, CommentRequest commentRequest) {
        AppUser user = appUserService.getUserById(userId);
        Post post = postService.getPost(postId);

        Comment comment = Comment.builder()
                .username(user.getUsername())
                .content(commentRequest.getContent())
                .appUser(user)
                .post(post)
                .createdAt(LocalDateTime.now().toString())
                .build();

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
