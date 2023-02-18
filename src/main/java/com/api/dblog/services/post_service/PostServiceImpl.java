package com.api.dblog.services.post_service;

import com.api.dblog.data.dtos.entiies_dto.PostDto;
import com.api.dblog.data.dtos.requests.CreatePostRequest;
import com.api.dblog.data.dtos.responses.CreateResponse;
import com.api.dblog.data.dtos.responses.PostResponse;
import com.api.dblog.data.dtos.responses.UpdateResponse;
import com.api.dblog.data.exceptions.ServiceException;
import com.api.dblog.data.models.AppUser;
import com.api.dblog.data.models.Post;
import com.api.dblog.data.repositories.AppUserRepository;
import com.api.dblog.data.repositories.PostRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private  final AppUserRepository appUserRepository;
    private final ModelMapper mapper;

    @Override
    public CreateResponse createPost(CreatePostRequest createPostRequest) {
        Post post = Post.builder()
                .title(createPostRequest.getTitle())
                .content(createPostRequest.getPostContent())
                .comment(new ArrayList<>())
                .localDateTime(LocalDateTime.now().toString())
                .build();

        Post savedPost = postRepository.save(post);
        return CreateResponse.builder()
                .id(savedPost.getId())
                .code(HttpStatus.CREATED.value())
                .message("Post Created successfully")
                .isPosted(true)
                .build();
    }

    @Override
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new ServiceException("Post could not be found"));

        return PostResponse.builder()
                .username(post.getAppUser().getUsername())
                .posts(mapper.map(post, PostDto.class))
                .build();

        //this will only return the username of the user with the post
    }

    @Override
    public List<PostDto> getAll() {
        return postRepository.findAll()
                .stream()
                .map(x->mapper.map(x, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UpdateResponse updateField(Long postId, JsonPatch patchUpdate) {
        ObjectMapper objectMapper = new ObjectMapper();
        Post post = mapper.map(getPost(postId), Post.class);

        JsonNode node = objectMapper.convertValue(post, JsonNode.class);
        try {
            JsonNode updatedNode = patchUpdate.apply(node);
            Post updatedPost = objectMapper.convertValue(updatedNode, Post.class);

            return UpdateResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("Post updated successfully").build();
        } catch (JsonPatchException ex) {
            log.error(ex.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public UpdateResponse deletePost(Long postId) {
        postRepository.deleteById(postId);

        return UpdateResponse.builder().message("Post deleted successfully").build();
    }

    @Override
    public UpdateResponse deleteAll() {
        postRepository.deleteAll();
        return UpdateResponse.builder().message("All Posts deleted successfully").build();
    }

}
