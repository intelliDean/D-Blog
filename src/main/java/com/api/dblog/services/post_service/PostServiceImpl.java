package com.api.dblog.services.post_service;

import com.api.dblog.data.dtos.entiies_dto.PostDto;
import com.api.dblog.data.dtos.requests.CreatePostRequest;
import com.api.dblog.data.dtos.responses.CreateResponse;
import com.api.dblog.data.dtos.responses.PostResponse;
import com.api.dblog.data.dtos.responses.UpdateResponse;
import com.api.dblog.data.exceptions.ServiceException;
import com.api.dblog.data.models.AppUser;
import com.api.dblog.data.models.Post;
import com.api.dblog.data.repositories.PostRepository;
import com.api.dblog.services.appUser_services.AppUserService;
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
    private final AppUserService appUserService;
    private final ModelMapper mapper;

    @Override
    public CreateResponse createPost(Long userid, CreatePostRequest createPostRequest) {
        AppUser user = appUserService.getUserById(userid);

        Post post = Post.builder()
                .title(createPostRequest.getTitle())
                .content(createPostRequest.getContent())
                .comments(new ArrayList<>())
                .appUser(user)
                .createdAt(LocalDateTime.now().toString())
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
    public Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(()->new ServiceException("Post could not be found"));
    }

    @Override
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    private PostResponse all(Post post) {
        return PostResponse.builder()
                .username(post.getAppUser().getUsername())
                .post(post)
                .build();
    }


    @Override
    public UpdateResponse updateField(Long postId, JsonPatch patchUpdate) {
        ObjectMapper objectMapper = new ObjectMapper();
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new ServiceException("Post could not be found"));

        JsonNode node = objectMapper.convertValue(post, JsonNode.class);
        try {
            JsonNode updatedNode = patchUpdate.apply(node);
            Post updatedPost = objectMapper.convertValue(updatedNode, Post.class);
            updatedPost.setAppUser(post.getAppUser());

            postRepository.save(updatedPost);
            return UpdateResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("Post %s updated successfully").build();
        } catch (JsonPatchException ex) {
            log.error(ex.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public UpdateResponse updatePost(Long postId, PostDto postDto) {

        Post post = postRepository.findById(postId)
                .orElseThrow(()->new ServiceException("Post could not be found"));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCreatedAt(LocalDateTime.now().toString());

        postRepository.save(post);

        return UpdateResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Post updated successfully")
                .build();
    }

    @Override
    public UpdateResponse deletePost(Long postId) {
        postRepository.deleteById(postId);

        return UpdateResponse.builder().message("Post deleted successfully").build();
    }

    @Override
    public UpdateResponse deleteAll() {
        postRepository.deleteAll();
        return UpdateResponse.builder()
                .code(HttpStatus.OK.value())
                .message("All Posts deleted successfully").build();
    }

    @Override
    public PostResponse getPostByUserId(Long userId, Long postId) {
        Post post = postRepository.findPostByIdAndAppUser_Id(postId, userId);
        return PostResponse.builder()
                .username(post.getAppUser().getUsername())
                .post(post)
                .build();
    }

    @Override
    public UpdateResponse deletePostByUserId(Long userId) {
        postRepository.deleteAllByAppUser_Id(userId);

        return UpdateResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Deleted Successfully")
                .build();
    }

    public List<PostResponse> findAllUserPosts(Long userId) {
        return postRepository.findAllByAppUser_Id(userId)
                .stream()
                .map(this :: all)
                .collect(Collectors.toList());
    }


}
