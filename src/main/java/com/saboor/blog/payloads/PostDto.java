package com.saboor.blog.payloads;


import com.saboor.blog.entities.Category;
import com.saboor.blog.entities.Comment;
import com.saboor.blog.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Integer postID;
    private String title;
    private String content;
    private String imageName;
    private Date addedDate;

    private CategoryDto category;

    private UserDto user;

    private Set<CommentDto> comments =  new HashSet<>();
}
