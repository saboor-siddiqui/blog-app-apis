package com.saboor.blog.services;

import com.saboor.blog.entities.Post;
import com.saboor.blog.payloads.CategoryDto;
import com.saboor.blog.payloads.PostDto;
import com.saboor.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {
    public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
    public PostDto updatePost(PostDto postDto,Integer postId);
    public void deletePost(Integer postId);
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy);
    public PostDto getPostById(Integer postId);
    public List<PostDto> getPostByCategory(Integer categoryId);
    public List<PostDto> getAllPostByUser(Integer userId);
    public List<PostDto> searchPosts(String keyword);
}
