package com.example.blogApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogApp.dto.CategoryDto;
import com.example.blogApp.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/apis/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create-category")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto category){
        CategoryDto createdCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(createdCategory,HttpStatus.CREATED);
    }

    @PutMapping("/update-category/{category-id}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto category,@PathVariable(name = "category-id") Integer categoryId){
        CategoryDto updatedCategory = categoryService.updateCategory(category, categoryId);
        return new ResponseEntity<>(updatedCategory,HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-category/{category-id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(name = "category-id") Integer categoryId){
        CategoryDto category = categoryService.getCategoryById(categoryId);
         return new ResponseEntity<>(category,HttpStatus.OK);
    }

    @GetMapping("/get-categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }

    @PostMapping("/delete-category/{category-id}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable(name = "category-id") Integer categoryId){
        CategoryDto deletedCategory = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(deletedCategory,HttpStatus.OK);
    }
}
