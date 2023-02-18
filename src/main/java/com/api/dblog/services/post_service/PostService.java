package com.api.dblog.services.post_service;

import com.api.dblog.data.dtos.entiies_dto.PostDto;
import com.api.dblog.data.dtos.requests.CreatePostRequest;
import com.api.dblog.data.dtos.responses.CreateResponse;
import com.api.dblog.data.dtos.responses.PostResponse;
import com.api.dblog.data.dtos.responses.UpdateResponse;
import com.github.fge.jsonpatch.JsonPatch;

import java.util.List;

public interface PostService {
   CreateResponse createPost(CreatePostRequest createPostRequest);
   PostResponse getPost(Long postId);
   List<PostDto> getAll();
   UpdateResponse updateField(Long postId, JsonPatch patchUpdate);
   UpdateResponse deletePost(Long postId);
   UpdateResponse deleteAll();

}
