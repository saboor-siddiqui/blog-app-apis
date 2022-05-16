package com.saboor.blog.services;

import com.saboor.blog.payloads.CommentDto;

public interface CommentService {
    public CommentDto createComment(CommentDto commentDto,Integer postID);
    public CommentDto updateComment(CommentDto commentDto,Integer commentID);
    public void deleteComment(Integer commentID);
}
