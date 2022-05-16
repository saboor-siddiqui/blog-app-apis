package com.saboor.blog.impl;

import com.saboor.blog.entities.Comment;
import com.saboor.blog.entities.Post;
import com.saboor.blog.exceptions.ResourceNotFoundException;
import com.saboor.blog.payloads.CommentDto;
import com.saboor.blog.repositories.CommentRepo;
import com.saboor.blog.repositories.PostRepo;
import com.saboor.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postID) {
        Post post = this.postRepo.findById(postID).orElseThrow(()-> new ResourceNotFoundException("Post","Post ID",postID));
        Comment comment =  this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment,CommentDto.class);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer commentID) {
        Comment comment = this.commentRepo.findById(commentID).orElseThrow(()-> new ResourceNotFoundException("Comment","Comment ID",commentID));
        comment.setComment_content(commentDto.getComment_content());
        comment.setId(commentDto.getId());
        Comment updatedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(updatedComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentID) {
        Comment comment = this.commentRepo.findById(commentID).orElseThrow(()-> new ResourceNotFoundException("Comment","Comment ID",commentID));
        this.commentRepo.delete(comment);
    }
}
