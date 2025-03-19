package com.final_project.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ProductRequest {
    @Size(min=1, message = "PRODUCTNAME_NULL")
    String name;
    String description;
    String image;
    double price;
    int stock;
    
    @Min(value = 1,message = "CATEGORYID_NULL")
    Long categoryId;
}
