package com.final_project.service;

import com.final_project.dto.request.ProductRequest;
import com.final_project.dto.response.ProductResponse;
import com.final_project.entity.Category;
import com.final_project.entity.Product;
import com.final_project.exception.AppException;
import com.final_project.exception.ErrorCode;

import com.final_project.mapper.ProductMapper;
import com.final_project.repository.CategoryRepository;
import com.final_project.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor
public class ProductService {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    ProductMapper productMapper;

    public ProductResponse createProduct(ProductRequest request) {
        // Get category by ID
        Category category = categoryRepository.findById(String.valueOf(request.getCategoryId()))
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

        // Get category code (assuming category has a field 'code')
        String categoryCode = String.valueOf(category.getId());

        // Find the latest product code in the category to get the sequence number
        String latestProductCode = productRepository.findLatestProductCodeByCategoryId(category.getId());

        // Process the sequence number
        int newNumber = 1; // Default if no products exist yet
        if (latestProductCode != null && latestProductCode.matches(".+_\\d{6}")) {
            // If the latest product code is valid (matches the format xxx_yyyyyy)
            String numberPart = latestProductCode.split("_")[1]; // Get the numeric part
            newNumber = Integer.parseInt(numberPart) + 1; // Increment the value
        }
        // Format the sequence number to 6 digits
        String productCode = String.format("%s_%06d", categoryCode, newNumber);

        // Map request -> Product
        Product product = productMapper.toProduct(request);
        product.setCode(productCode);
        product.setCategory(category);

        // Save the product
        productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    public List<ProductResponse> getAllProduct(){
        return productRepository.findAll().stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    public Product getProductById(String id){
        return productRepository.findById(id).orElseThrow(()->
                new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
    }

    public ProductResponse updateProduct(String id, ProductRequest request){
        Product product = productRepository.findById(id).orElseThrow(()->
                new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        Category category=categoryRepository.findById(String.valueOf(request.getCategoryId())).orElseThrow(()->
                new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

        //Map request -> Product
        productMapper.updateProduct(product,request);
        productRepository.save(product);
        return productMapper.toProductResponse(product);
    }
    public void deleteProduct(String id){
        Product product = productRepository.findById(id).orElseThrow(()->
                new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        productRepository.delete(product);
    }

    public Page<ProductResponse> getProductsWithPagination(PageRequest pageRequest) {
        Page<Product> productPage = productRepository.findAll(pageRequest);
        return productPage.map(productMapper::toProductResponse);
    }
}
