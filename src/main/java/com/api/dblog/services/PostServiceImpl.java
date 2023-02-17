package com.api.dblog.services;

import com.api.dblog.data.dtos.PostDto;
import com.api.dblog.data.models.Post;
import com.api.dblog.data.repositories.PostRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @Override
    public PostDto savePost(PostDto postDto) {
        Post post = mapToEntity(postDto);

        Post returnedPost = postRepository.save(post);

        return mapToDto(returnedPost);
    }

    @Override
    public List<PostDto> getAll() {
       return postRepository.findAll()
               .stream()
               .map(this :: mapToDto)
               .collect(Collectors.toList());
    }

    private PostDto mapToDto(Post post) {
        PostDto postDto = new PostDto();

        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setPostContent(post.getPostContent());
        postDto.setLocalDateTime(post.getLocalDateTime());
        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setPostContent(postDto.getPostContent());
        post.setTitle(postDto.getTitle());
        post.setLocalDateTime(LocalDateTime.now());
        return post;
    }
}
