package com.api.dblog.data.repositories;

import com.api.dblog.data.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
