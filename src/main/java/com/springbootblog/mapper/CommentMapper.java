package com.springbootblog.mapper;

import com.springbootblog.entity.Comment;
import com.springbootblog.payload.CommentDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {


    private  ModelMapper modelMapper;

    @Autowired
    public CommentMapper(ModelMapper modelMapper) {

        this.modelMapper = modelMapper;
    }

    public  Comment mapToComment(CommentDto commentDto)
    {
        Comment comment = modelMapper.map(commentDto, Comment.class);

//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setBody(commentDto.getBody());
//        comment.setEmail(commentDto.getEmail());

        return comment;
    }

    public  CommentDto mapToCommentDto(Comment comment)
    {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);

//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setBody(comment.getBody());
//        commentDto.setEmail(comment.getEmail());

        return commentDto;
    }
}
