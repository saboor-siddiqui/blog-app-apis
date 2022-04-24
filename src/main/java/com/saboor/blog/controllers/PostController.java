package com.saboor.blog.controllers;

import com.saboor.blog.entities.Post;
import com.saboor.blog.payloads.ApiResponse;
import com.saboor.blog.payloads.CategoryDto;
import com.saboor.blog.payloads.PostDto;
import com.saboor.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
        PostDto createPost = this.postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<>(createPost, HttpStatus.CREATED);
    }
    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
        PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
        return new ResponseEntity(updatedPostDto,HttpStatus.OK);
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Post has beem deleted",true),HttpStatus.OK);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto postDto = this.postService.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
        List<PostDto> postDtos= this.postService.getAllPostByUser(userId);
        return new ResponseEntity(postDtos,HttpStatus.OK);
    }
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory( @PathVariable Integer categoryId){
        List<PostDto> postDtoByCategory = this.postService.getPostByCategory(categoryId);
        return new ResponseEntity(postDtoByCategory,HttpStatus.OK);
    }
    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPosts(@RequestParam(value = "pageNumber",defaultValue = "1",required = false) Integer pageNumber
                                                    ,@RequestParam(value = "pageSize", defaultValue = "5",required = false) Integer pageSize) {

        List<PostDto> posts = this.postService.getAllPost(pageSize,pageNumber);
        return new ResponseEntity(posts,HttpStatus.OK);
    }

}
