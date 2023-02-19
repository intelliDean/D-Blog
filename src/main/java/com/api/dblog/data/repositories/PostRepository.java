package com.api.dblog.data.repositories;

import com.api.dblog.data.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findPostByIdAndAppUser_Id(Long postId, Long userid);

    //Post findPostByAppUser_Id(Long userId);
    List<Post> findAllByAppUser_Id(Long userId);
    void deleteAllByAppUser_Id(Long Id);

}
