package com.springbootblog.service.impl;

import com.springbootblog.entity.Comment;
import com.springbootblog.entity.Post;
import com.springbootblog.exception.BlogAPIException;
import com.springbootblog.exception.ResourceNotFoundException;
import com.springbootblog.mapper.CommentMapper;
import com.springbootblog.payload.CommentDto;
import com.springbootblog.repository.CommentRepository;
import com.springbootblog.repository.PostRepository;
import com.springbootblog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    private CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public CommentDto createComment(long id, CommentDto commentDto) {

        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        Comment comment = commentMapper.mapToComment(commentDto);

        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);
        return commentMapper.mapToCommentDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long id) {

        List<Comment> comments= commentRepository.findByPostId(id);
        return comments.stream().map(comment -> commentMapper.mapToCommentDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long commentId, long postId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        if(!comment.getPost().getId().equals(postId))
        {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment id does not belongs to Post");
        }

        return commentMapper.mapToCommentDto(comment);
    }

    @Override
    public CommentDto upadteComment(long commentId, long postId, CommentDto commentDto) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        if(!comment.getPost().getId().equals(postId))
        {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment id does not belongs to Post");
        }

        comment.setEmail(commentDto.getEmail());
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());

        Comment savedComment = commentRepository.save(comment);

        return commentMapper.mapToCommentDto(savedComment);
    }

    @Override
    public void deleteComment(long commentId, long postId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        if(!comment.getPost().getId().equals(postId))
        {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment id does not belongs to Post");
        }

        commentRepository.delete(comment);

    }
}
