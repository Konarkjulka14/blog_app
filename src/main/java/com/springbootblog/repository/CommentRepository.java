package com.springbootblog.repository;

import com.springbootblog.entity.Comment;
import com.springbootblog.payload.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(long postId);
}
