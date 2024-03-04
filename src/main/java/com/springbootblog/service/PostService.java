package com.springbootblog.service;

import com.springbootblog.entity.Post;
import com.springbootblog.mapper.PostResponse;
import com.springbootblog.payload.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PostService {

    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sort);

    PostDto getPostById(Long id);

    PostDto updatePostById(PostDto postDto, Long id);

    void deletePost(Long id);
}
