package com.api.dblog.data.repositories;

import com.api.dblog.data.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
