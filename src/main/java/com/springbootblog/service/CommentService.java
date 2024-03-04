package com.springbootblog.service;

import com.springbootblog.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long id, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long id);

    CommentDto getCommentById(long commentId, long postId);

    CommentDto upadteComment(long commentId, long postId, CommentDto commentDto);

    void deleteComment(long commentId, long postId);
}
