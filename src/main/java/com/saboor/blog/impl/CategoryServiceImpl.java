package com.saboor.blog.impl;

import com.saboor.blog.entities.Category;
import com.saboor.blog.exceptions.ResourceNotFoundException;
import com.saboor.blog.payloads.CategoryDto;
import com.saboor.blog.repositories.CategoryRepo;
import com.saboor.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category cat = this.modelMapper.map(categoryDto,Category.class);
        Category addedCategory = this.categoryRepo.save(cat);
        return this.modelMapper.map(addedCategory,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categoryList = this.categoryRepo.findAll();
       List<CategoryDto> categoryListDto = categoryList.stream().map((cat)->this.modelMapper.map(cat,CategoryDto.class)).collect(Collectors.toList());
        return categoryListDto;
    }

    @Override
    public CategoryDto getCategoryById(Integer id) {
        Category cat = this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","Category ID",id));
        return this.modelMapper.map(cat,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer id) {
        Category cat = this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","Category ID",id));
        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        cat.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = this.categoryRepo.save(cat);
        return this.modelMapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer id) {

        Category cat = this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","Category ID",id));
        this.categoryRepo.delete(cat);

    }
}
