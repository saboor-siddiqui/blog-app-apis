package com.saboor.blog.controllers;

import com.saboor.blog.config.AppConstants;
import com.saboor.blog.services.FileService;
import com.saboor.blog.payloads.ApiResponse;
import com.saboor.blog.payloads.PostDto;
import com.saboor.blog.payloads.PostResponse;
import com.saboor.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

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
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber
                                                    , @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize
                                                    , @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy) {

        PostResponse allposts = this.postService.getAllPost(pageNumber,pageSize,sortBy);
        return new ResponseEntity<PostResponse>(allposts,HttpStatus.OK);
    }
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword){
        List<PostDto> result = this.postService.searchPosts(keyword);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("/post/image/upload/{postID}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image")MultipartFile image,@PathVariable Integer postID) throws IOException {
        PostDto postDto =  this.postService.getPostById(postID);
        String fileName = this.fileService.uploadImage(path,image);
        postDto.setImageName(fileName);
        PostDto updatedPost = this.postService.updatePost(postDto,postID);
        return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
    }

    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException{
        InputStream resource =  this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }



}
