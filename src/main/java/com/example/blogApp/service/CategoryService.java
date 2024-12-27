package com.example.blogApp.service;

import java.util.List;

import com.example.blogApp.dto.CategoryDto;

public interface CategoryService {

    public CategoryDto createCategory(CategoryDto categoryDto);

    public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);

    public List<CategoryDto> getAllCategories();

    public CategoryDto getCategoryById(Integer categoryId);

    public CategoryDto deleteCategory(Integer categoryId);
}
