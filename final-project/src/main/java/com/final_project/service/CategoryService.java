package com.final_project.service;

import com.final_project.dto.request.CategoryRequest;
import com.final_project.entity.Category;
import com.final_project.exception.AppException;
import com.final_project.exception.ErrorCode;
import com.final_project.mapper.CategoryMapper;
import com.final_project.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = lombok.AccessLevel.PRIVATE)
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;
    public Category createCategory(CategoryRequest request){
        //Map request -> Category
        Category category=categoryMapper.toCategory(request);
        if(categoryRepository.findCategoriesByName(request.getName()).isPresent()){
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        return categoryRepository.save(category);
    }

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public Category getCategory(String name){
        return categoryRepository.findCategoriesByName(name).orElseThrow(()->
                new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
    }

    public Category updateCategory(String categoryID, CategoryRequest request){
        Category category = categoryRepository.findById(categoryID).orElseThrow(()->
                new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        //Map request -> Category
        categoryMapper.updateCategory(category,request);
        return categoryRepository.save(category);
    }

    public void deleteCategory(String categoryID){
        Category category = categoryRepository.findById(categoryID).orElseThrow(()->
                new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        categoryRepository.delete(category);

    }
}
