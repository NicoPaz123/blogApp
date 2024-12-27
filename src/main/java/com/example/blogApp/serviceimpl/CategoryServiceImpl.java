package com.example.blogApp.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blogApp.dto.CategoryDto;
import com.example.blogApp.exception.ResourceNotFoundException;
import com.example.blogApp.domain.Category;
import com.example.blogApp.repo.CategoryRepo;
import com.example.blogApp.service.CategoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepo categoryRepo;

    private static ObjectMapper mapper = new ObjectMapper();

    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = mapper.convertValue(categoryDto,Category.class);
        Category savedCategory = categoryRepo.save(category);
        return mapper.convertValue(savedCategory,CategoryDto.class);
        
    }

    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category fetchedCategory = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        fetchedCategory.setTitle(categoryDto.getTitle());
        fetchedCategory.setDescription(categoryDto.getDescription());
        fetchedCategory = categoryRepo.save(fetchedCategory);
        return mapper.convertValue(fetchedCategory,CategoryDto.class);
    }

    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        return mapper.convertValue(categories, new TypeReference<List<CategoryDto>>() {});
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category fetchedCategory = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        return mapper.convertValue(fetchedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto deleteCategory(Integer categoryId) {
        Category fetchedCategory = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryRepo.deleteById(categoryId);
        return mapper.convertValue(fetchedCategory,CategoryDto.class);
    }

}
