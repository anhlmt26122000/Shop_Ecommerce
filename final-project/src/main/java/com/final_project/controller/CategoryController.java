package com.final_project.controller;

import com.final_project.dto.request.CategoryRequest;
import com.final_project.dto.response.ApiResponse;
import com.final_project.entity.Category;
import com.final_project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    ApiResponse<Category> createCategory(@RequestBody @Valid CategoryRequest request){
        ApiResponse<Category> response = new ApiResponse<>();
        response.setResult(categoryService.createCategory(request));
        return response;
    }

    @GetMapping
    ApiResponse<List<Category>> getCategories() {
        ApiResponse<List<Category>> response = new ApiResponse<>();
        response.setResult(categoryService.getCategories());
        return response;
    }

    @GetMapping("/{name}")
    ApiResponse<Category> getCategory(@PathVariable("name") String name) {
        ApiResponse<Category> response = new ApiResponse<>();
        response.setResult(categoryService.getCategory(name));
        return response;
    }

    @PutMapping("/{categoryID}")
    ApiResponse<Category> updateCategory(@PathVariable("categoryID") String categoryID, @RequestBody @Valid CategoryRequest request){
        ApiResponse<Category> response = new ApiResponse<>();
        response.setResult(categoryService.updateCategory(categoryID, request));
        return response;
    }

    @DeleteMapping("/{categoryID}")
    ApiResponse<String> deleteCategory(@PathVariable("categoryID") String categoryID){
        categoryService.deleteCategory(categoryID);
        return new ApiResponse<>(200, "Category deleted", null);
    }
}
