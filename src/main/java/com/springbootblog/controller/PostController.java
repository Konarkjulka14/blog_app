package com.springbootblog.controller;

import com.springbootblog.entity.Post;
import com.springbootblog.mapper.PostResponse;
import com.springbootblog.payload.PostDto;
import com.springbootblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping
    private ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto)
    {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    private ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sort
    )
    {
        return new ResponseEntity<>(postService.getAllPosts(pageNo, pageSize, sort), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<PostDto> getPostById(@PathVariable Long id)
    {
        return ResponseEntity.ok(postService.getPostById(id));
    }


    @PutMapping("/{id}")
    private ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto, @PathVariable Long id)
    {
        PostDto postDto1 = postService.updatePostById(postDto, id);

        return ResponseEntity.ok(postDto1);
    }


    @DeleteMapping("/{id}")
    private ResponseEntity deletePost(@PathVariable Long id)
    {
        postService.deletePost(id);
        return ResponseEntity.ok("Post is deleted with id : " + id );
    }
}
