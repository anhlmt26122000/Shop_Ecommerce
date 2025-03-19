package com.final_project.mapper;


import com.final_project.dto.request.CategoryRequest;
import com.final_project.entity.Category;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);

    void updateCategory(@MappingTarget Category category, CategoryRequest request);
}
