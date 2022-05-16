package com.saboor.blog.controllers;

import com.saboor.blog.entities.Comment;
import com.saboor.blog.payloads.ApiResponse;
import com.saboor.blog.payloads.CommentDto;
import com.saboor.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postID}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto , @PathVariable Integer postID){

        CommentDto newComment = this.commentService.createComment(commentDto,postID);
        return new ResponseEntity(newComment, HttpStatus.CREATED);

    }
    @PutMapping("/{commentID}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto , @PathVariable Integer commentID){

        CommentDto updatedComment = this.commentService.updateComment(commentDto,commentID);
        return ResponseEntity.ok(updatedComment);

    }

    @DeleteMapping("/comments/{commentID}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentID){

        this.commentService.deleteComment(commentID);
        return new ResponseEntity<>(new ApiResponse("Comment Deleted",true),HttpStatus.OK);

    }
}
