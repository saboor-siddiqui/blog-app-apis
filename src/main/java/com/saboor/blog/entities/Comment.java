package com.saboor.blog.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String comment_content;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user_comment;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
