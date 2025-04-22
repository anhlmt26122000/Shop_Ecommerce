package com.final_project.controller;

import com.final_project.dto.request.ProductRequest;
import com.final_project.dto.response.ApiResponse;
import com.final_project.dto.response.ProductResponse;
import com.final_project.entity.Product;
import com.final_project.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    ApiResponse<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request){
        ApiResponse<ProductResponse> productResponse = new ApiResponse<>();
        productResponse.setResult(productService.createProduct(request));
        return productResponse;
    }

    @GetMapping
    ApiResponse<List<ProductResponse>> getAllProduct(){
        ApiResponse<List<ProductResponse>> productResponse = new ApiResponse<>();
        productResponse.setResult(productService.getAllProduct());
        return productResponse;
    }

    @GetMapping("/{id}")
    ApiResponse<Product> getProductById(@PathVariable String id){
        ApiResponse<Product> response = new ApiResponse<>();
        response.setResult(productService.getProductById(id));
        return response;
    }

    @PutMapping("/{id}")
    ApiResponse<ProductResponse> updateProduct(@PathVariable String id, @RequestBody @Valid ProductRequest request){
        ApiResponse<ProductResponse> response = new ApiResponse<>();
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

    @GetMapping("page")
    ApiResponse<Page<ProductResponse>> getProductsWithPagination(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "5") int size) {
        // Chuyển giá trị page từ frontend (frontend bắt đầu từ 1, backend bắt đầu từ 0)
        page = page - 1;  // Điều chỉnh page khi frontend gửi page = 1 -> backend sẽ nhận page = 0
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductResponse> productResponse = productService.getProductsWithPagination(pageRequest);
        return new ApiResponse<>(200, "Get product successfully", productResponse);
    }

}
