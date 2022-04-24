package com.saboor.blog.services;

import com.saboor.blog.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    public CategoryDto createCategory(CategoryDto categoryDto);
    public List<CategoryDto> getAllCategory();
    public CategoryDto getCategoryById(Integer id);
    public  CategoryDto updateCategory(CategoryDto categoryDto,Integer id);
    public void deleteCategory(Integer id);
}
