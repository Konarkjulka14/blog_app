package com.springbootblog.service.impl;

import com.springbootblog.entity.Post;
import com.springbootblog.exception.ResourceNotFoundException;
import com.springbootblog.mapper.PostMapper;
import com.springbootblog.mapper.PostResponse;
import com.springbootblog.payload.PostDto;
import com.springbootblog.repository.PostRepository;
import com.springbootblog.service.PostService;
import jakarta.persistence.AssociationOverride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private PostMapper postMapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        //convert postDto to post
        Post post = postMapper.mapToPost(postDto);

        //saving post in db
        Post savedPost = postRepository.save(post);

        //convert post to post dto, for returning
        PostDto postDto1 = postMapper.mapToPostDto(savedPost);

        return postDto1;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy) {

        Pageable pageable =  PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Post> postList = postRepository.findAll(pageable);
        List<Post> listOfPost = postList.getContent();

        List<PostDto> postContent=  postList.stream().map(post -> postMapper.mapToPostDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setPostContent(postContent);
        postResponse.setPageNo(postList.getNumber());
        postResponse.setPageSize(postList.getSize());
        postResponse.setTotalElements(postList.getTotalElements());
        postResponse.setTotalPages(postList.getTotalPages());
        postResponse.setLast(postList.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id" ,id));
        return postMapper.mapToPostDto(post);
    }

    @Override
    public PostDto updatePostById(PostDto postDto, Long id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id" ,id));

        post.setTitle(postDto.getTitle());
        post.setDescription((postDto.getDescription()));
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);

        return postMapper.mapToPostDto(updatedPost);
    }

    @Override
    public void deletePost(Long id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id" ,id));

        postRepository.delete(post);

    }
}
