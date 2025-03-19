package com.final_project.controller;

import com.final_project.dto.request.ProductRequest;
import com.final_project.dto.response.ApiResponse;
import com.final_project.entity.Product;
import com.final_project.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    ApiResponse<Product> createProduct(@RequestBody @Valid ProductRequest request){
        ApiResponse<Product> response = new ApiResponse<>();
        response.setResult(productService.createProduct(request));
        return response;
    }

    @GetMapping
    ApiResponse<List<Product>> getAllProduct(){
        ApiResponse<List<Product>> response = new ApiResponse<>();
        response.setResult(productService.getAllProduct());
        return response;
    }

    @GetMapping("/{id}")
    ApiResponse<Product> getProductById(@PathVariable String id){
        ApiResponse<Product> response = new ApiResponse<>();
        response.setResult(productService.getProductById(id));
        return response;
    }

    @PutMapping("/{id}")
    ApiResponse<Product> updateProduct(@PathVariable String id, @RequestBody @Valid ProductRequest request){
        ApiResponse<Product> response = new ApiResponse<>();
        response.setResult(productService.updateProduct(id, request));
        return response;
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteProduct(@PathVariable String id){
        ApiResponse<String> response = new ApiResponse<>();
        productService.deleteProduct(id);
        response.setResult("Delete product successfully");
        return response;
    }

}
