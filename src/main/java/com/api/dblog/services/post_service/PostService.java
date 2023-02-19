package com.api.dblog.services.post_service;

import com.api.dblog.data.dtos.entiies_dto.PostDto;
import com.api.dblog.data.dtos.requests.CreatePostRequest;
import com.api.dblog.data.dtos.responses.CreateResponse;
import com.api.dblog.data.dtos.responses.PostResponse;
import com.api.dblog.data.dtos.responses.UpdateResponse;
import com.api.dblog.data.models.Post;
import com.github.fge.jsonpatch.JsonPatch;

import java.util.List;

public interface PostService {
    CreateResponse createPost(Long userId, CreatePostRequest createPostRequest);

    Post getPost(Long postId);

    List<Post> getAll();

    UpdateResponse updateField(Long postId, JsonPatch patchUpdate);

    UpdateResponse updatePost(Long postId, PostDto postDto);

    List<PostResponse> findAllUserPosts(Long userId);

    UpdateResponse deletePost(Long postId);

    UpdateResponse deleteAll();

    PostResponse getPostByUserId(Long userId, Long postId);

    UpdateResponse deletePostByUserId(Long userId);


}
