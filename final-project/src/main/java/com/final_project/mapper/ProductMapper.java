package com.final_project.mapper;


import com.final_project.dto.request.ProductRequest;
import com.final_project.entity.Product;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductRequest request);
    void updateProduct(@MappingTarget Product product, ProductRequest request);
}
