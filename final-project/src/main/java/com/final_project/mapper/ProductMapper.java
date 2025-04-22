package com.final_project.mapper;


import com.final_project.dto.request.ProductRequest;
import com.final_project.dto.response.ProductResponse;
import com.final_project.entity.Product;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductRequest request);
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductResponse toProductResponse(Product product);
    void updateProduct(@MappingTarget Product product, ProductRequest request);
}
