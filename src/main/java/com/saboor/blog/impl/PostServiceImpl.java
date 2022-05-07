package com.saboor.blog.impl;

import com.saboor.blog.entities.Category;
import com.saboor.blog.entities.Post;
import com.saboor.blog.entities.User;
import com.saboor.blog.exceptions.ResourceNotFoundException;
import com.saboor.blog.payloads.CategoryDto;
import com.saboor.blog.payloads.PostDto;
import com.saboor.blog.payloads.PostResponse;
import com.saboor.blog.repositories.CategoryRepo;
import com.saboor.blog.repositories.PostRepo;
import com.saboor.blog.repositories.UserRepo;
import com.saboor.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Post ID",postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy) {
        Pageable page =PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
        Page<Post> p = this.postRepo.findAll(page);
        List<Post> allPosts = p.getContent();
        List<PostDto> allPostsDto = allPosts.stream().map((post) -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(allPostsDto);
        postResponse.setPageNumber(p.getNumber());
        postResponse.setPageSize(p.getSize());
        postResponse.setTotalElements(p.getTotalElements());
        postResponse.setTotalPages(p.getTotalPages());
        postResponse.setLastPage(p.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post ID",postId));
        return this.modelMapper.map(post,PostDto.class);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
       Category category =  this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category ID",categoryId));
        List<Post> postList = this.postRepo.findByCategory(category);
        return postList.stream().map(post -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getAllPostByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","User ID",userId));
        List<Post> postList = this.postRepo.findByUser(user);
        return postList.stream().map(post -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> postswithTitle = this.postRepo.searchByTitle("%"+keyword+"%");
        List<PostDto> collect = postswithTitle.stream().map(post -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return collect;
    }
}
