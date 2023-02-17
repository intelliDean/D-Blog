package com.api.dblog.services;

import com.api.dblog.data.dtos.PostDto;
import com.api.dblog.data.models.Post;

import java.util.List;

public interface PostService {
   PostDto savePost(PostDto postDto);
   List<PostDto> getAll();

}
