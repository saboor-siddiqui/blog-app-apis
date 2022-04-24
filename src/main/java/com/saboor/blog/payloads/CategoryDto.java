package com.saboor.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private Integer categoryId;
    @NotBlank
    @Size(min = 4,message = "Minimun size of category title must be 4")
    private String categoryTitle;
    @NotBlank
    @Size(min = 10, message = "Minimum size of category description must be 10")
    private String categoryDescription;
}

