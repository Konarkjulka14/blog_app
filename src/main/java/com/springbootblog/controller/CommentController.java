package com.springbootblog.controller;

import com.springbootblog.payload.CommentDto;
import com.springbootblog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    private ResponseEntity<CommentDto> createComment(@PathVariable long postId, @RequestBody CommentDto commentDto)
    {
        CommentDto commentDto1 = commentService.createComment(postId, commentDto);

        return new ResponseEntity<>(commentDto1, HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    private List<CommentDto> getCommentsByPostId(@PathVariable long postId)
    {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    private CommentDto getCommentById(@PathVariable long commentId, @PathVariable long postId)
    {
        return commentService.getCommentById(commentId, postId);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    private CommentDto updateComment(@PathVariable long commentId, @PathVariable long postId, @RequestBody CommentDto commentDto)
    {
        return commentService.upadteComment(commentId, postId, commentDto);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    private ResponseEntity<String> deleteComment(@PathVariable long commentId, @PathVariable long postId)
    {
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }
}
