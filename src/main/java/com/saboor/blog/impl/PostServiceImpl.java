package com.saboor.blog.impl;

import com.saboor.blog.entities.Category;
import com.saboor.blog.entities.Post;
import com.saboor.blog.entities.User;
import com.saboor.blog.exceptions.ResourceNotFoundException;
import com.saboor.blog.payloads.CategoryDto;
import com.saboor.blog.payloads.PostDto;
import com.saboor.blog.repositories.CategoryRepo;
import com.saboor.blog.repositories.PostRepo;
import com.saboor.blog.repositories.UserRepo;
import com.saboor.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {

        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User ID",userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category ID",categoryId));

        Post post  = this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post newPost  =  this.postRepo.save(post);
        return this.modelMapper.map(newPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post ID",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setAddedDate(postDto.getAddedDate());
        post.setImageName(postDto.getImageName());
//        post.setUser(this.modelMapper.map((postDto.getUser()),User.class));
//        post.setCategory(this.modelMapper.map((postDto.getCategory()),Category.class));
        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Post ID",postId));
        this.postRepo.delete(post);
    }

    @Override
    public List<PostDto> getAllPost() {
        List<Post> allPosts = this.postRepo.findAll();
        List<PostDto> allPostsDto = allPosts.stream().map((post) -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return allPostsDto;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post ID",postId));
        return this.modelMapper.map(post,PostDto.class);
    }

    @Override
    public List<PostDto> getPostByCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = this.modelMapper.map(categoryDto,Category.class);
        List<Post> postByCategory = this.postRepo.findByCategory(category);
        List<PostDto> postDtoByCategory = postByCategory.stream().map((post) ->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtoByCategory;
    }

    @Override
    public List<PostDto> getAllPostByUser(Integer userId) {
        return null;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        return null;
    }
}
